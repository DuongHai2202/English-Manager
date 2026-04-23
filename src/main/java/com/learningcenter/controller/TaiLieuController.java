package com.learningcenter.controller;

import com.learningcenter.dao.TaiLieuDAO;
import com.learningcenter.model.TaiLieu;
import com.learningcenter.view.dialog.TaiLieuDialog;
import com.learningcenter.view.panel.DocumentManagementPanel;
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

public class TaiLieuController {

    private DocumentManagementPanel view;
    private TaiLieuDAO dao;

    public TaiLieuController(DocumentManagementPanel view) {
        this.view = view;
        this.dao = new TaiLieuDAO();
        initListeners();
    }

    private void initListeners() {
        view.getBtnAdd().addActionListener(e -> onAdd());
        view.getBtnEdit().addActionListener(e -> onEdit());
        view.getBtnDelete().addActionListener(e -> onDelete());
        view.getBtnDetail().addActionListener(e -> onDetail());
        view.getBtnSearch().addActionListener(e -> onSearch());
        view.getBtnRefresh().addActionListener(e -> loadDataToTable());
        view.getBtnExport().addActionListener(e -> onExport());
    }

    public void loadDataToTable() {
        List<TaiLieu> list = dao.getAll();
        view.updateTable(list);
    }

    /* ================= SEARCH ================= */
    private void onSearch() {
        String keyword = view.getTxtSearch().getText().trim();
        String criteria = view.getCbFilter().getSelectedItem().toString();
        List<TaiLieu> list = dao.search(keyword, criteria);
        view.updateTable(list);
    }

    /* ================= ADD ================= */
    private void onAdd() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        TaiLieuDialog dialog = new TaiLieuDialog(parent, null, false);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            if (dao.add(dialog.getTaiLieu())) {
                JOptionPane.showMessageDialog(view, "Thêm tài liệu thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /* ================= EDIT ================= */
    private void onEdit() {
        TaiLieu selected = view.getSelectedDocument();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn tài liệu!");
            return;
        }

        TaiLieu full = dao.findById(selected.getIdTaiLieu());
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        TaiLieuDialog dialog = new TaiLieuDialog(parent, full != null ? full : selected, false);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            if (dao.update(dialog.getTaiLieu())) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /* ================= DETAIL ================= */
    private void onDetail() {
        TaiLieu selected = view.getSelectedDocument();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn tài liệu!");
            return;
        }

        TaiLieu full = dao.findById(selected.getIdTaiLieu());
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        TaiLieuDialog dialog = new TaiLieuDialog(parent, full != null ? full : selected, true);
        dialog.setVisible(true);
    }

    /* ================= DELETE ================= */
    private void onDelete() {
        TaiLieu selected = view.getSelectedDocument();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn tài liệu cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Bạn có chắc chắn muốn xóa tài liệu này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(selected.getIdTaiLieu())) {
                JOptionPane.showMessageDialog(view, "Đã xóa tài liệu!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /* ================= EXPORT ================= */
    private void onExport() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Xuất danh sách tài liệu");

        if (chooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".csv")) path += ".csv";

            try (FileOutputStream fos = new FileOutputStream(path);
                 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                 CSVWriter writer = new CSVWriter(osw)) {

                fos.write(0xef);
                fos.write(0xbb);
                fos.write(0xbf);

                String[] header = {
                        "ID", "ID Khóa học", "Tiêu đề",
                        "Mô tả", "Tên file", "Đường dẫn"
                };
                writer.writeNext(header);

                for (TaiLieu tl : dao.getAll()) {
                    writer.writeNext(new String[]{
                            String.valueOf(tl.getIdTaiLieu()),
                            String.valueOf(tl.getIdKhoaHoc()),
                            tl.getTieuDe(),
                            tl.getMoTa(),
                            tl.getTenFile(),
                            tl.getDuongDanFile()
                    });
                }

                JOptionPane.showMessageDialog(view, "Xuất file thành công!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, "Lỗi xuất file: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
