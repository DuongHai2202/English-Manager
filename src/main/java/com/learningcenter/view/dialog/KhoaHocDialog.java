/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.view.dialog;

import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class KhoaHocDialog extends JDialog{
     public JTextField txtMaKH, txtTenKH, txtMoTa, txtThoiLuong,txtHocPhi;
    public JComboBox<String>  cbTrinhDo,cbTrangThai;
    public JButton btnSave, btnCancel;

    public KhoaHocDialog(Frame parent) {
        super(parent, "Khoá học", true);
        setLayout(new GridLayout(0, 2, 5, 5));

        txtMaKH = new JTextField();
        txtTenKH = new JTextField();
        txtMoTa = new JTextField();
        txtThoiLuong = new JTextField();
        txtHocPhi = new JTextField();


        cbTrinhDo = new JComboBox<>(new String[]{"Cơ bản", "Trung bình","Nâng cao"});
        cbTrangThai = new JComboBox<>(new String[]{"Đang mở", "Đã đóng"});

        add(new JLabel("Mã KH")); add(txtMaKH);
        add(new JLabel("Tên khoá học")); add(txtTenKH);
        add(new JLabel("Mô tả")); add(txtMoTa);
        add(new JLabel("Trình độ")); add(cbTrinhDo);
        add(new JLabel("Thời lượng")); add(txtThoiLuong);
        add(new JLabel("Học Phí")); add(txtHocPhi);
        add(new JLabel("Trạng thái")); add(cbTrangThai);

        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Đóng");
        add(btnSave); add(btnCancel);

        pack();
        setLocationRelativeTo(parent);
    }
}
