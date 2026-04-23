package com.learningcenter.controller;

import com.learningcenter.dao.CoSoVatChatDAO;
import com.learningcenter.model.CoSoVatChat;
import com.learningcenter.view.dialog.CoSoVatChatDialog;
import com.learningcenter.view.panel.CoSoVatChatPanel;
import java.awt.Frame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class CoSoVatChatController {
    private CoSoVatChatPanel view;
    private CoSoVatChatDAO dao;

    public CoSoVatChatController(CoSoVatChatPanel view) {
        this.view = view;
        this.dao = new CoSoVatChatDAO();
        initListeners();
    }

    private void initListeners() {
        view.getBtnAdd().addActionListener(e -> onAdd());
        view.getBtnEdit().addActionListener(e -> onEdit());
        view.getBtnDelete().addActionListener(e -> onDelete());
        view.getBtnDetail().addActionListener(e -> onDetail());
        view.getBtnRefresh().addActionListener(e -> loadDataToTable());
        view.getBtnSearch().addActionListener(e -> onSearch());
        view.getTxtSearch().addActionListener(e -> onSearch());
    }

    public void loadDataToTable() {
        view.updateTable(dao.getAll());
    }

    private void onSearch() {
        String keyword = view.getTxtSearch().getText().trim();
        String criteria = view.getCbFilter().getSelectedItem().toString();
        view.updateTable(dao.search(keyword, criteria));
    }

    private void onAdd() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        CoSoVatChatDialog dialog = new CoSoVatChatDialog(parent, null, false);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            if (dao.add(dialog.getCoSoVatChat())) {
                JOptionPane.showMessageDialog(view, "Thêm cơ sở vật chất thành công!");
                loadDataToTable();
            }
        }
    }

    private void onEdit() {
        int id = view.getSelectedId();
        if (id == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một dòng để sửa!");
            return;
        }

        CoSoVatChat selected = dao.getAll().stream()
                .filter(i -> i.getIdCoSoVatChat() == id)
                .findFirst().orElse(null);

        if (selected != null) {
            Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
            CoSoVatChatDialog dialog = new CoSoVatChatDialog(parent, selected, false);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                if (dao.update(dialog.getCoSoVatChat())) {
                    JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                    loadDataToTable();
                }
            }
        }
    }

    private void onDetail() {
        int id = view.getSelectedId();
        if (id == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một dòng để xem chi tiết!");
            return;
        }

        CoSoVatChat selected = dao.getAll().stream()
                .filter(i -> i.getIdCoSoVatChat() == id)
                .findFirst().orElse(null);

        if (selected != null) {
            Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
            CoSoVatChatDialog dialog = new CoSoVatChatDialog(parent, selected, true);
            dialog.setVisible(true);
        }
    }

    private void onDelete() {
        int id = view.getSelectedId();
        if (id == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một dòng để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa cơ sở vật chất này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(id)) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                loadDataToTable();
            }
        }
    }
}
