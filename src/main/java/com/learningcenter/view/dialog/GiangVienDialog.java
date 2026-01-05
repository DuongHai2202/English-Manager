/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.view.dialog;

import javax.swing.*;
import java.awt.*;


/**
 *
 * @author Admin
 */
public class GiangVienDialog extends JDialog {
     public JTextField txtMaNV, txtHoTen, txtNgaySinh, txtNgayVaoLam,txtSDT, txtEmail, txtChuyenMon, txtMucLuongGio, txtTrinhDo;
    public JComboBox<String> cbGioiTinh,cbTrangThai;
    public JButton btnSave, btnCancel;

    public GiangVienDialog(Frame parent) {
        super(parent, "Giảng viên", true);
        setLayout(new GridLayout(0, 2, 5, 5));

        txtMaNV = new JTextField();
        txtHoTen = new JTextField();
        txtNgaySinh = new JTextField();
        txtNgayVaoLam = new JTextField();
        txtSDT = new JTextField();
        txtEmail = new JTextField();
        txtChuyenMon = new JTextField();
        txtTrinhDo = new JTextField();
        txtMucLuongGio = new JTextField();

        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cbTrangThai = new JComboBox<>(new String[]{"Đang làm", "Nghỉ việc"});

        add(new JLabel("Mã NV")); add(txtMaNV);
        add(new JLabel("Họ tên")); add(txtHoTen);
        add(new JLabel("Ngày sinh")); add(txtNgaySinh);
        add(new JLabel("Giới tính")); add(cbGioiTinh);
        add(new JLabel("SĐT")); add(txtSDT);
        add(new JLabel("Email")); add(txtEmail);
        add(new JLabel("Chuyên môn")); add(txtChuyenMon);
        add(new JLabel("Trình độ học vấn")); add(txtTrinhDo);
        add(new JLabel("Ngày vào làm")); add(txtNgayVaoLam);
        add(new JLabel("Lương")); add(txtMucLuongGio);
        add(new JLabel("Trạng thái")); add(cbTrangThai);

        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Đóng");
        add(btnSave); add(btnCancel);

        pack();
        setLocationRelativeTo(parent);
    }
}
