package com.learningcenter.controller;

import com.learningcenter.dao.HocVienDAO;
import com.learningcenter.model.HocVien;
import com.learningcenter.view.dialog.HocVienDialog;
import com.learningcenter.view.panel.HocVienPanel;
import com.opencsv.CSVWriter;
import java.awt.Frame;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class HocVienController {
    private HocVienPanel view;
    private HocVienDAO dao;

    public HocVienController(HocVienPanel view) {
        this.view = view;
        this.dao = new HocVienDAO();
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
        List<HocVien> list = dao.getAll();
        view.updateTable(list);
    }

    private void onSearch() {
        String keyword = view.getTxtSearch().getText().trim();
        String criteria = view.getCbFilter().getSelectedItem().toString();
        List<HocVien> list = dao.search(keyword, criteria);
        view.updateTable(list);
    }

    private void onAdd() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        HocVienDialog dialog = new HocVienDialog(parent, null, false);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            if (dao.add(dialog.getStudent())) {
                JOptionPane.showMessageDialog(view, "Thêm học viên thành công!");
                loadDataToTable();
                DashboardController.refreshData();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEdit() {
        HocVien selected = view.getSelectedStudent();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn học viên!");
            return;
        }

        HocVien fullInfo = fetchFullInfo(selected);
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        HocVienDialog dialog = new HocVienDialog(parent, fullInfo != null ? fullInfo : selected, false);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            if (dao.update(dialog.getStudent())) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadDataToTable();
                DashboardController.refreshData();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDetail() {
        HocVien selected = view.getSelectedStudent();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn học viên để xem chi tiết!");
            return;
        }

        HocVien fullInfo = fetchFullInfo(selected);
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        HocVienDialog dialog = new HocVienDialog(parent, fullInfo != null ? fullInfo : selected, true);
        dialog.setVisible(true);
    }

    private HocVien fetchFullInfo(HocVien selected) {
        for (HocVien h : dao.getAll()) {
            if (h.getIdHocVien() == selected.getIdHocVien()) {
                return h;
            }
        }
        return null;
    }

    private void onDelete() {
        HocVien selected = view.getSelectedStudent();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn học viên cần xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa học viên này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(selected.getIdHocVien())) {
                JOptionPane.showMessageDialog(view, "Đã xóa học viên!");
                loadDataToTable();
                DashboardController.refreshData();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu danh sách học viên");
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".csv"))
                path += ".csv";

            try (FileOutputStream fos = new FileOutputStream(path);
                    OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                    CSVWriter writer = new CSVWriter(osw)) {

                fos.write(0xef);
                fos.write(0xbb);
                fos.write(0xbf);

                String[] header = { "ID", "Mã Học Viên", "Họ Tên", "Ngày Sinh", "Giới Tính", "SĐT", "Trình Độ",
                        "Trạng Thái" };
                writer.writeNext(header);

                List<HocVien> list = dao.getAll(); // Export all
                for (HocVien hv : list) {
                    writer.writeNext(new String[] {
                            String.valueOf(hv.getIdHocVien()),
                            hv.getMaHocVien(),
                            hv.getHoTen(),
                            hv.getNgaySinh() != null ? hv.getNgaySinh().toString() : "",
                            hv.getGioiTinh(),
                            hv.getSoDienThoai(),
                            hv.getTrinhDo(),
                            hv.getTrangThai()
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
