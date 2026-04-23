/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.view.dialog;

import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author Admin
 */
public class LopHocDialog extends JDialog {
    public JTextField txtMaLH, txtTenLH,txtSiSoToiDa, txtNgayBatDau,txtNgayKetThuc, txtPhongHoc;
    public JComboBox<Object> cbKhoaHoc;
    public JComboBox<String> cbTrangThai;
    public JButton btnSave, btnCancel;

    public LopHocDialog(Frame parent) {
        super(parent, "Lớp học", true);
        setLayout(new GridLayout(0, 2, 5, 5));

        txtMaLH = new JTextField();
        txtTenLH = new JTextField();
        txtSiSoToiDa = new JTextField();
        txtNgayBatDau = new JTextField();
        txtNgayKetThuc = new JTextField();
        txtPhongHoc = new JTextField();

        cbKhoaHoc = new JComboBox();
        cbTrangThai = new JComboBox<>(new String[]{"Đang học", "Đã kết thúc", "Sắp mở"});

        add(new JLabel("Mã lớp học")); add(txtMaLH);
        add(new JLabel("Tên lớp học")); add(txtTenLH);
        add(new JLabel("Tên khoá học")); add(cbKhoaHoc);
        add(new JLabel("Sĩ số tối đa")); add(txtSiSoToiDa);
        add(new JLabel("Ngày bắt đầu")); add(txtNgayBatDau);
        add(new JLabel("Ngày kết thúc")); add(txtNgayKetThuc);
        add(new JLabel("Phòng học")); add(txtPhongHoc);
        add(new JLabel("Trạng thái")); add(cbTrangThai);

        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Đóng");
        add(btnSave); add(btnCancel);

        pack();
        setLocationRelativeTo(parent);
    }
}
