package com.learningcenter.controller;

import com.learningcenter.dao.BaiVietDAO;
import com.learningcenter.model.BaiViet;
import com.learningcenter.view.dialog.BaiVietDialog;
import com.learningcenter.view.panel.PostManagementPanel;
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

public class BaiVietController {

    private PostManagementPanel view;
    private BaiVietDAO dao;

    public BaiVietController(PostManagementPanel view) {
        this.view = view;
        this.dao = new BaiVietDAO();
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
        List<BaiViet> list = dao.getAll();
        view.updateTable(list);
    }

    private void onSearch() {
        String keyword = view.getTxtSearch().getText().trim();
        String criteria = view.getCbFilter().getSelectedItem().toString();
        List<BaiViet> list = dao.search(keyword, criteria);
        view.updateTable(list);
    }

    private void onAdd() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        BaiVietDialog dialog = new BaiVietDialog(parent, null, false);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            if (dao.add(dialog.getPost())) {
                JOptionPane.showMessageDialog(view, "Thêm bài viết thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEdit() {
        BaiViet selected = view.getSelectedPost();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn bài viết!");
            return;
        }

        BaiViet fullInfo = fetchFullInfo(selected);

        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        BaiVietDialog dialog = new BaiVietDialog(parent, fullInfo != null ? fullInfo : selected, false);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            BaiViet edited = dialog.getPost();
            // đảm bảo ID không bị mất khi dialog trả về object mới
            edited.setIdBaiViet(selected.getIdBaiViet());

            if (dao.update(edited)) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDetail() {
        BaiViet selected = view.getSelectedPost();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn bài viết để xem chi tiết!");
            return;
        }

        BaiViet fullInfo = fetchFullInfo(selected);

        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        BaiVietDialog dialog = new BaiVietDialog(parent, fullInfo != null ? fullInfo : selected, true);
        dialog.setVisible(true);
    }

    private BaiViet fetchFullInfo(BaiViet selected) {
        for (BaiViet b : dao.getAll()) {
            if (b.getIdBaiViet() == selected.getIdBaiViet()) {
                return b;
            }
        }
        return null;
    }

    private void onDelete() {
        BaiViet selected = view.getSelectedPost();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn bài viết cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Bạn có chắc chắn muốn xóa bài viết này?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(selected.getIdBaiViet())) {
                JOptionPane.showMessageDialog(view, "Đã xóa bài viết!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu danh sách bài viết");

        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".csv")) path += ".csv";

            try (FileOutputStream fos = new FileOutputStream(path);
                 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                 CSVWriter writer = new CSVWriter(osw)) {

                fos.write(0xef);
                fos.write(0xbb);
                fos.write(0xbf);

                String[] header = { "ID", "Tiêu đề", "Loại bài viết", "Trạng thái", "Ngày đăng" };
                writer.writeNext(header);

                List<BaiViet> list = dao.getAll();
                for (BaiViet bv : list) {
                    writer.writeNext(new String[] {
                            String.valueOf(bv.getIdBaiViet()),
                            bv.getTieuDe(),
                            bv.getLoaiBaiViet(),
                            bv.getTrangThai(),
                            bv.getNgayDang() != null ? bv.getNgayDang().toString() : ""
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
