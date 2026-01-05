package com.learningcenter.controller;

import com.learningcenter.dao.ChungChiDAO;
import com.learningcenter.model.ChungChi;
import com.learningcenter.view.dialog.ChungChiDialog;
import com.learningcenter.view.panel.ChungChiPanel; // Đã đổi tên ở đây
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

public class ChungChiController {
    private ChungChiPanel view; // Đã đổi tên
    private ChungChiDAO dao;

    public ChungChiController(ChungChiPanel view) {
        this.view = view;
        this.dao = new ChungChiDAO();
        initListeners();
    }

    private void initListeners() {
        view.getBtnAdd().addActionListener(e -> onAdd());
        view.getBtnEdit().addActionListener(e -> onEdit());
        view.getBtnDelete().addActionListener(e -> onDelete());
        view.getBtnSearch().addActionListener(e -> onSearch());
        view.getBtnRefresh().addActionListener(e -> loadDataToTable());
        view.getBtnExport().addActionListener(e -> onExport());
    }

    public void loadDataToTable() {
        List<ChungChi> list = dao.getAll();
        view.updateTable(list);
    }

    private void onSearch() {
        String keyword = view.getTxtSearch().getText().trim();
        String criteria = view.getCbFilter().getSelectedItem().toString();
        List<ChungChi> list = dao.search(keyword, criteria);
        view.updateTable(list);
    }

    private void onAdd() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        ChungChiDialog dialog = new ChungChiDialog(parent, null, false);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            if (dao.add(dialog.getChungChi())) {
                JOptionPane.showMessageDialog(view, "Thêm chứng chỉ thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEdit() {
        ChungChi selected = view.getSelectedChungChi();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn chứng chỉ cần sửa!");
            return;
        }

        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        ChungChiDialog dialog = new ChungChiDialog(parent, selected, false);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            if (dao.update(dialog.getChungChi())) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDelete() {
        ChungChi selected = view.getSelectedChungChi();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn chứng chỉ cần xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, 
                "Bạn có chắc chắn muốn xóa chứng chỉ: " + selected.getTenChungChi() + "?", 
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(selected.getID_ChungChi())) {
                JOptionPane.showMessageDialog(view, "Đã xóa chứng chỉ!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu danh sách chứng chỉ");
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".csv")) path += ".csv";

            try (FileOutputStream fos = new FileOutputStream(path);
                 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                 CSVWriter writer = new CSVWriter(osw)) {

                // Ghi UTF-8 BOM để Excel hiển thị đúng tiếng Việt
                fos.write(0xef);
                fos.write(0xbb);
                fos.write(0xbf);

                String[] header = { "ID", "Loại Chứng Chỉ", "Tên Chứng Chỉ", "Mô Tả", "Ngày Tạo" };
                writer.writeNext(header);

                List<ChungChi> list = dao.getAll(); 
                for (ChungChi cc : list) {
                    writer.writeNext(new String[] {
                        String.valueOf(cc.getID_ChungChi()),
                        cc.getLoaiChungChi(),
                        cc.getTenChungChi(),
                        cc.getMoTaNgan(),
                        cc.getNgayTao() != null ? cc.getNgayTao().toString() : ""
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