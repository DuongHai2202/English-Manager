package com.learningcenter.controller;

import com.learningcenter.dao.GiaoDichDao;
import com.learningcenter.model.GiaoDich;
import com.learningcenter.view.dialog.GiaoDichDialog;
import com.learningcenter.view.panel.GiaoDichPanel;
import java.awt.Frame;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GiaoDichController {
    private GiaoDichPanel view;
    private GiaoDichDao dao;

    public GiaoDichController(GiaoDichPanel view) {
        this.view = view;
        this.dao = new GiaoDichDao();
        initListeners();
    }

    private void initListeners() {
        view.getBtnSearch().addActionListener(e -> onSearch());
        view.getBtnRefresh().addActionListener(e -> loadDataToTable());
        view.getBtnAdd().addActionListener(e -> onAdd());
        view.getBtnEdit().addActionListener(e -> onEdit());
        view.getBtnDelete().addActionListener(e -> onDelete());
        view.getBtnDetail().addActionListener(e -> onDetail());
    }

    public void loadDataToTable() {
        List<GiaoDich> list = dao.getAll();
        view.updateTable(list);
    }

    private void onSearch() {
        String keyword = view.getTxtSearch().getText().trim();
        String criteria = view.getCbFilter().getSelectedItem().toString();
        List<GiaoDich> list = dao.search(keyword, criteria);
        view.updateTable(list);
    }

    private void onAdd() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        GiaoDichDialog dialog = new GiaoDichDialog(parent, null, false);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            if (dao.add(dialog.getGiaoDich())) {
                JOptionPane.showMessageDialog(view, "Thêm giao dịch thành công!");
                loadDataToTable();
                DashboardController.refreshData();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEdit() {
        GiaoDich selected = getSelectedGiaoDich();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một dòng để sửa!");
            return;
        }

        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        GiaoDichDialog dialog = new GiaoDichDialog(parent, selected, false);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            if (dao.update(dialog.getGiaoDich())) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadDataToTable();
                DashboardController.refreshData();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDetail() {
        GiaoDich selected = getSelectedGiaoDich();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một dòng để xem chi tiết!");
            return;
        }

        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        GiaoDichDialog dialog = new GiaoDichDialog(parent, selected, true);
        dialog.setVisible(true);
    }

    private void onDelete() {
        GiaoDich selected = getSelectedGiaoDich();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một dòng để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa giao dịch này?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(selected.getIdGiaoDich())) {
                JOptionPane.showMessageDialog(view, "Đã xóa giao dịch thành công!");
                loadDataToTable();
                DashboardController.refreshData();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private GiaoDich getSelectedGiaoDich() {
        int id = view.getSelectedGiaoDichId();
        if (id == -1)
            return null;

        // Tìm lại đối tượng từ DB để đảm bảo dữ liệu mới nhất
        for (GiaoDich gd : dao.getAll()) {
            if (gd.getIdGiaoDich() == id) {
                return gd;
            }
        }
        return null;
    }
}
