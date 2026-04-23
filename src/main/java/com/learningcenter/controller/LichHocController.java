package com.learningcenter.controller;

import com.learningcenter.dao.LichHocDAO;
import com.learningcenter.model.LichHoc;
import com.learningcenter.view.dialog.LichHocDialog;
import com.learningcenter.view.panel.ScheduleManagementPanel;
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

public class LichHocController {

    private ScheduleManagementPanel view;
    private LichHocDAO dao;

    public LichHocController(ScheduleManagementPanel view) {
        this.view = view;
        this.dao = new LichHocDAO();
        initListeners();
        loadDataToTable();
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
        List<LichHoc> list = dao.getAll();
        view.updateTable(list);
    }

    private void onSearch() {
        String keyword = view.getTxtSearch().getText().trim();
        String criteria = view.getCbFilter().getSelectedItem().toString();
        List<LichHoc> list = dao.search(keyword, criteria);
        view.updateTable(list);
    }

   private void onAdd() {
    Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
    LichHocDialog dialog = new LichHocDialog(parent, null, false);
    dialog.setVisible(true);

    if (dialog.isConfirmed()) {
        LichHoc lh = dialog.getSchedule();

        Integer idLopHoc = dao.getIdLopHocByMa(lh.getMaLopHoc());
        if (idLopHoc == null) {
            JOptionPane.showMessageDialog(view, "Mã lớp không tồn tại trong bảng LOP_HOC!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        lh.setIdLopHoc(idLopHoc);

        if (dao.add(lh)) {
            JOptionPane.showMessageDialog(view, "Thêm lịch học thành công!");
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    private void onEdit() {
    LichHoc selected = view.getSelectedSchedule();
    if (selected == null) {
        JOptionPane.showMessageDialog(view, "Vui lòng chọn lịch học!");
        return;
    }

    Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
    LichHocDialog dialog = new LichHocDialog(parent, selected, false);
    dialog.setVisible(true);

    if (dialog.isConfirmed()) {
        LichHoc lh = dialog.getSchedule();

        // 🔴 DÒNG QUAN TRỌNG NHẤT
        lh.setIdLichHoc(selected.getIdLichHoc());

        if (dao.update(lh)) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

    private void onDetail() {
        LichHoc selected = view.getSelectedSchedule();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn lịch học để xem chi tiết!");
            return;
        }

        Frame parent = (Frame) SwingUtilities.getWindowAncestor(view);
        LichHocDialog dialog = new LichHocDialog(parent, selected, true);
        dialog.setVisible(true);
    }

    private void onDelete() {
        LichHoc selected = view.getSelectedSchedule();
        if (selected == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn lịch học cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa lịch học này?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(selected.getIdLichHoc())) {
                JOptionPane.showMessageDialog(view, "Đã xóa lịch học!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu danh sách lịch học");

        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".csv")) path += ".csv";

            try (FileOutputStream fos = new FileOutputStream(path);
                 OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                 CSVWriter writer = new CSVWriter(osw)) {

                fos.write(0xef); fos.write(0xbb); fos.write(0xbf);

                writer.writeNext(new String[]{"ID", "Mã lớp", "Tên lớp", "Thứ", "Giờ bắt đầu", "Giờ kết thúc", "Phòng"});

                List<LichHoc> list = dao.getAll();
                for (LichHoc lh : list) {
                    writer.writeNext(new String[]{
                            String.valueOf(lh.getIdLichHoc()),
                            lh.getMaLopHoc(),
                            lh.getTenLopHoc(),
                            lh.getThuTrongTuan(),
                            lh.getGioBatDau() != null ? lh.getGioBatDau().toString() : "",
                            lh.getGioKetThuc() != null ? lh.getGioKetThuc().toString() : "",
                            lh.getPhongHoc()
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
