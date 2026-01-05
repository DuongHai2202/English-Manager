package com.learningcenter.controller;

import com.learningcenter.dao.NgayNghiDAO;
import com.learningcenter.model.NgayNghi;
import com.learningcenter.view.dialog.NgayNghiDialog;
import com.learningcenter.view.panel.NgayNghiPanel; // Đã đổi tên ở đây
import java.awt.Frame;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class NgayNghiController {
    private NgayNghiPanel view; // Đã đổi tên ở đây
    private NgayNghiDAO dao;

    public NgayNghiController(NgayNghiPanel view) { // Đã đổi tên ở đây
        this.view = view;
        this.dao = new NgayNghiDAO();
        initListeners();
    }

    private void initListeners() {
        view.getBtnAdd().addActionListener(e -> onAdd());
        view.getBtnEdit().addActionListener(e -> onEdit());
        view.getBtnDelete().addActionListener(e -> onDelete());
        view.getBtnRefresh().addActionListener(e -> loadDataToTable());
        view.getBtnDetail().addActionListener(e -> onDetail());
    }

    public void loadDataToTable() {
        List<NgayNghi> list = dao.getAll();
        view.updateTable(list);
    }

    private void onAdd() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        NgayNghiDialog dialog = new NgayNghiDialog(parent, null, false);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            if (dao.add(dialog.getHoliday())) {
                JOptionPane.showMessageDialog(view, "Thêm ngày nghỉ thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEdit() {
        NgayNghi selected = view.getSelectedHoliday();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn ngày nghỉ cần sửa!");
            return;
        }

        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        NgayNghiDialog dialog = new NgayNghiDialog(parent, selected, false);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            if (dao.update(dialog.getHoliday())) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDetail() {
        NgayNghi selected = view.getSelectedHoliday();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn ngày nghỉ để xem chi tiết!");
            return;
        }

        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        NgayNghiDialog dialog = new NgayNghiDialog(parent, selected, true);
        dialog.setVisible(true);
    }

    private void onDelete() {
        NgayNghi selected = view.getSelectedHoliday();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn ngày nghỉ cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, 
                "Bạn có chắc chắn muốn xóa ngày nghỉ: " + selected.getTenNgayNghi() + "?", 
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(selected.getID_NgayNghi())) {
                JOptionPane.showMessageDialog(view, "Đã xóa ngày nghỉ thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}