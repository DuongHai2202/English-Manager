package com.learningcenter.controller;

import com.learningcenter.dao.HoTroDAO;
import com.learningcenter.model.HoTro;
import com.learningcenter.view.dialog.HoTroDialog;
import com.learningcenter.view.panel.SupportManagementPanel;
import com.opencsv.CSVWriter;

import java.awt.Frame;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class HoTroController {
    private SupportManagementPanel view;
    private HoTroDAO dao;

    public HoTroController(SupportManagementPanel view) {
        this.view = view;
        this.dao = new HoTroDAO();
        initListeners();
    }

    private void initListeners() {
        view.getBtnAdd().addActionListener(e -> onAdd());
        view.getBtnEdit().addActionListener(e -> onEdit());
        view.getBtnDelete().addActionListener(e -> onDelete());
        view.getBtnSearch().addActionListener(e -> onSearch());
        view.getBtnRefresh().addActionListener(e -> loadDataToTable());
        view.getBtnExport().addActionListener(e -> onExport());
        view.getBtnDetail().addActionListener(e -> onDetail());
    }

    public void loadDataToTable() {
        List<HoTro> list = dao.getAll();
        view.updateTable(list);
    }

    private void onSearch() {
        String keyword = view.getTxtSearch().getText().trim();
        String criteria = view.getCbFilter().getSelectedItem().toString();
        List<HoTro> list = dao.search(keyword, criteria);
        view.updateTable(list);
    }

    private void onAdd() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        HoTroDialog dialog = new HoTroDialog(parent, null, false);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            if (dao.add(dialog.getSupport())) {
                JOptionPane.showMessageDialog(view, "Thêm yêu cầu hỗ trợ thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEdit() {
        HoTro selected = view.getSelectedSupport();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn yêu cầu cần sửa!");
            return;
        }

        HoTro full = fetchFullInfo(selected);
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        HoTroDialog dialog = new HoTroDialog(parent, full != null ? full : selected, false);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            if (dao.update(dialog.getSupport())) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDetail() {
        HoTro selected = view.getSelectedSupport();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn yêu cầu để xem chi tiết!");
            return;
        }

        HoTro full = fetchFullInfo(selected);
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        HoTroDialog dialog = new HoTroDialog(parent, full != null ? full : selected, true);
        dialog.setVisible(true);
    }

    private HoTro fetchFullInfo(HoTro selected) {
        for (HoTro ht : dao.getAll()) {
            if (ht.getIdHoTro() == selected.getIdHoTro()) {
                return ht;
            }
        }
        return null;
    }

    private void onDelete() {
        HoTro selected = view.getSelectedSupport();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn yêu cầu cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa yêu cầu này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(selected.getIdHoTro())) {
                JOptionPane.showMessageDialog(view, "Đã xóa yêu cầu!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu danh sách hỗ trợ");
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".csv")) path += ".csv";

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            try (FileOutputStream fos = new FileOutputStream(path);
                 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                 CSVWriter writer = new CSVWriter(osw)) {

                fos.write(0xef);
                fos.write(0xbb);
                fos.write(0xbf);

                String[] header = {"ID", "Người gửi", "Email", "Tiêu đề", "Chuyên mục", "Ưu tiên", "Trạng thái", "Ngày tạo"};
                writer.writeNext(header);

                List<HoTro> list = dao.getAll();
                for (HoTro ht : list) {
                    writer.writeNext(new String[]{
                            String.valueOf(ht.getIdHoTro()),
                            ht.getTenNguoiGui(),
                            ht.getEmailNguoiGui(),
                            ht.getTieuDe(),
                            ht.getChuyenMuc(),
                            ht.getDoUuTien(),
                            ht.getTrangThai(),
                            ht.getNgayTao() != null ? sdf.format(ht.getNgayTao()) : ""
                    });
                }

                JOptionPane.showMessageDialog(view, "Xuất file thành công: " + path);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, "Lỗi xuất file: " + e.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
