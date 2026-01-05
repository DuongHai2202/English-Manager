package com.learningcenter.view.dialog;

import com.learningcenter.model.DiemDanh;
import com.learningcenter.model.HocVien;
import com.learningcenter.model.LopHoc;
import com.learningcenter.dao.HocVienDAO;
import com.learningcenter.dao.LopHocDAO;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.Date;
import javax.swing.*;

public class DiemDanhDialog extends JDialog {
    private JComboBox<HocVien> cbHocVien;
    private JComboBox<LopHoc> cbLopHoc;
    private JDateChooser dateChooser;
    private JComboBox<String> cbTrangThai;
    private JTextArea txtGhiChu;
    private JButton btnConfirm, btnCancel;

    private boolean confirmed = false;
    private DiemDanh diemDanh;
    private boolean isDetailView;

    public DiemDanhDialog(Frame parent, DiemDanh dd, boolean isDetail) {
        super(parent, "Thông tin điểm danh", true);
        this.diemDanh = dd;
        this.isDetailView = isDetail;

        initComponents();
        loadDataToComboBox();

        if (diemDanh != null) {
            setData();
        }

        if (isDetailView) {
            disableEditing();
        }

        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Học viên
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Học viên:"), gbc);
        cbHocVien = new JComboBox<>();
        gbc.gridx = 1;
        mainPanel.add(cbHocVien, gbc);

        // Lớp học
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Lớp học:"), gbc);
        cbLopHoc = new JComboBox<>();
        gbc.gridx = 1;
        mainPanel.add(cbLopHoc, gbc);

        // Ngày điểm danh
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Ngày:"), gbc);
        dateChooser = new JDateChooser(new Date());
        dateChooser.setDateFormatString("dd/MM/yyyy");
        gbc.gridx = 1;
        mainPanel.add(dateChooser, gbc);

        // Trạng thái
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Trạng thái:"), gbc);
        cbTrangThai = new JComboBox<>(new String[] { "Có mặt", "Vắng", "Muộn" });
        gbc.gridx = 1;
        mainPanel.add(cbTrangThai, gbc);

        // Ghi chú
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Ghi chú:"), gbc);
        txtGhiChu = new JTextArea(3, 20);
        gbc.gridx = 1;
        mainPanel.add(new JScrollPane(txtGhiChu), gbc);

        // Nút bấm
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnConfirm = new JButton(isDetailView ? "Đóng" : "Xác nhận");
        btnCancel = new JButton("Hủy");

        btnConfirm.addActionListener(e -> {
            if (!isDetailView) {
                if (validateForm()) {
                    captureData();
                    confirmed = true;
                    dispose();
                }
            } else {
                dispose();
            }
        });

        btnCancel.addActionListener(e -> dispose());

        btnPanel.add(btnConfirm);
        if (!isDetailView)
            btnPanel.add(btnCancel);

        add(mainPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadDataToComboBox() {
        new HocVienDAO().getAll().forEach(cbHocVien::addItem);
        new LopHocDAO().getAllList().forEach(cbLopHoc::addItem);
    }

    private void setData() {
        // 1. Chọn đúng học viên trong ComboBox
        for (int i = 0; i < cbHocVien.getItemCount(); i++) {
            HocVien hv = cbHocVien.getItemAt(i);
            if (hv.getIdHocVien() == diemDanh.getIdHocVien()) {
                cbHocVien.setSelectedIndex(i);
                break;
            }
        }

        // 2. Chọn đúng lớp học trong ComboBox
        for (int i = 0; i < cbLopHoc.getItemCount(); i++) {
            LopHoc lh = cbLopHoc.getItemAt(i);
            if (lh.getID_LopHoc() == diemDanh.getIdLopHoc()) {
                cbLopHoc.setSelectedIndex(i);
                break;
            }
        }

        // 3. Set các thông tin khác
        dateChooser.setDate(diemDanh.getNgayDiemDanh());
        cbTrangThai.setSelectedItem(diemDanh.getTrangThai());
        txtGhiChu.setText(diemDanh.getGhiChu());
    }

    private void captureData() {
        if (diemDanh == null)
            diemDanh = new DiemDanh();
        HocVien hv = (HocVien) cbHocVien.getSelectedItem();
        LopHoc lh = (LopHoc) cbLopHoc.getSelectedItem();

        diemDanh.setIdHocVien(hv.getIdHocVien());
        diemDanh.setIdLopHoc(lh.getID_LopHoc());
        diemDanh.setNgayDiemDanh(dateChooser.getDate());
        diemDanh.setTrangThai(cbTrangThai.getSelectedItem().toString());
        diemDanh.setGhiChu(txtGhiChu.getText());
    }

    private boolean validateForm() {
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày!");
            return false;
        }
        return true;
    }

    private void disableEditing() {
        cbHocVien.setEnabled(false);
        cbLopHoc.setEnabled(false);
        dateChooser.setEnabled(false);
        cbTrangThai.setEnabled(false);
        txtGhiChu.setEditable(false);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public DiemDanh getDiemDanh() {
        return diemDanh;
    }
}