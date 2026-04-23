package com.learningcenter.controller;

import com.learningcenter.dao.DiemDanhDAO;
import com.learningcenter.model.DiemDanh;
import com.learningcenter.view.dialog.DiemDanhDialog;
import com.learningcenter.view.panel.DiemDanhPanel;
import java.awt.Frame;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Controller xử lý logic cho chức năng điểm danh
 * // THỊNH: Tạo mới controller cho điểm danh
 */
public class DiemDanhController {
    private DiemDanhPanel view;
    private DiemDanhDAO dao;
    private List<DiemDanh> currentList;

    public DiemDanhController(DiemDanhPanel view) {
        this.view = view;
        this.dao = new DiemDanhDAO();
        initEvent();
    }

    private void initEvent() {
        view.getBtnAdd().addActionListener(e -> onAdd());
        view.getBtnEdit().addActionListener(e -> onEdit());
        view.getBtnDelete().addActionListener(e -> onDelete());
        view.getBtnDetail().addActionListener(e -> onDetail());
        view.getBtnSearch().addActionListener(e -> onSearch());
        view.getBtnRefresh().addActionListener(e -> loadDataToTable());
        view.getBtnExport().addActionListener(e -> onExport());

        // Add Enter key support for search
        view.getTxtSearch().addActionListener(e -> onSearch());
    }

    public void loadDataToTable() {
        currentList = dao.getAll();
        view.updateTable(currentList);
    }

    private void onAdd() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        DiemDanhDialog dialog = new DiemDanhDialog(parent, null, false);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            DiemDanh dd = dialog.getDiemDanh();

            // Check if already exists for same student/class/date
            if (dao.checkExists(dd.getIdHocVien(), dd.getIdLopHoc(), dd.getNgayDiemDanh())) {
                JOptionPane.showMessageDialog(view, "Học viên này đã được điểm danh trong ngày hôm nay ở lớp này!");
                return;
            }

            if (dao.add(dd)) {
                JOptionPane.showMessageDialog(view, "Thêm điểm danh thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi khi thêm điểm danh!");
            }
        }
    }

    private void onEdit() {
        DiemDanh selected = view.getSelectedDiemDanh();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn bản ghi cần sửa!");
            return;
        }

        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        DiemDanhDialog dialog = new DiemDanhDialog(parent, selected, false);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            if (dao.update(dialog.getDiemDanh())) {
                JOptionPane.showMessageDialog(view, "Cập nhật điểm danh thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật điểm danh!");
            }
        }
    }

    private void onDelete() {
        DiemDanh selected = view.getSelectedDiemDanh();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn bản ghi cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa bản ghi này?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(selected.getIdDiemDanh())) {
                JOptionPane.showMessageDialog(view, "Xóa điểm danh thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Lỗi khi xóa điểm danh!");
            }
        }
    }

    private void onDetail() {
        DiemDanh selected = view.getSelectedDiemDanh();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn bản ghi cần xem chi tiết!");
            return;
        }

        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        DiemDanhDialog dialog = new DiemDanhDialog(parent, selected, true);
        dialog.setVisible(true);
    }

    private void onSearch() {
        String keyword = view.getTxtSearch().getText();
        String criteria = view.getCbFilter().getSelectedItem().toString();
        currentList = dao.search(keyword, criteria);
        view.updateTable(currentList);
    }

    private void onExport() {
        // Simple message for now as we might need a utility for CSV export
        // But for consistency with other parts, we might want to implement it
        JOptionPane.showMessageDialog(view, "Chức năng xuất Excel đang được cập nhật!");
    }

    public DiemDanh getDiemDanhFromList(int row) {
        if (currentList != null && row >= 0 && row < currentList.size()) {
            return currentList.get(row);
        }
        return null;
    }
}
