package com.learningcenter.controller;

import com.learningcenter.dao.PhongHocDAO;
import com.learningcenter.model.PhongHoc;
import com.learningcenter.view.dialog.PhongHocDialog;
import com.learningcenter.view.panel.PhongHocPanel; // Đã đổi tên ở đây
import java.awt.Frame;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class PhongHocController {
    private PhongHocPanel view; // Sử dụng PhongHocPanel
    private PhongHocDAO dao;

    public PhongHocController(PhongHocPanel view) {
        this.view = view;
        this.dao = new PhongHocDAO();
        initListeners();
    }

    private void initListeners() {
        view.getBtnAdd().addActionListener(e -> onAdd());
        view.getBtnEdit().addActionListener(e -> onEdit());
        view.getBtnDelete().addActionListener(e -> onDelete());
        view.getBtnSearch().addActionListener(e -> onSearch());
        view.getBtnRefresh().addActionListener(e -> loadDataToTable());
        view.getBtnDetail().addActionListener(e -> onDetail());
    }

    public void loadDataToTable() {
        List<PhongHoc> list = dao.getAll();
        view.updateTable(list);
    }

    private void onSearch() {
        String keyword = view.getTxtSearch().getText().trim();
        String criteria = view.getCbFilter().getSelectedItem().toString();
        List<PhongHoc> list = dao.search(keyword, criteria);
        view.updateTable(list);
    }

    private void onAdd() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        PhongHocDialog dialog = new PhongHocDialog(parent, null, false);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            if (dao.add(dialog.getPhongHoc())) {
                JOptionPane.showMessageDialog(view, "Thêm phòng học thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEdit() {
        PhongHoc selected = view.getSelectedPhongHoc();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn phòng học!");
            return;
        }

        PhongHoc fullInfo = fetchFullInfo(selected);
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        PhongHocDialog dialog = new PhongHocDialog(parent, fullInfo != null ? fullInfo : selected, false);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            if (dao.update(dialog.getPhongHoc())) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDetail() {
        PhongHoc selected = view.getSelectedPhongHoc();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn phòng học để xem chi tiết!");
            return;
        }

        PhongHoc fullInfo = fetchFullInfo(selected);
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        PhongHocDialog dialog = new PhongHocDialog(parent, fullInfo != null ? fullInfo : selected, true);
        dialog.setVisible(true);
    }

    private PhongHoc fetchFullInfo(PhongHoc selected) {
        for (PhongHoc ph : dao.getAll()) {
            if (ph.getId() == selected.getId()) {
                return ph;
            }
        }
        return null;
    }

    private void onDelete() {
        PhongHoc selected = view.getSelectedPhongHoc();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn phòng học cần xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, 
                "Bạn có chắc chắn muốn xóa phòng: " + selected.getTenPhong() + "?", 
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(selected.getId())) {
                JOptionPane.showMessageDialog(view, "Đã xóa phòng học!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}