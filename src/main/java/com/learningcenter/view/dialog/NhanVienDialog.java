package com.learningcenter.view.dialog;

import javax.swing.*;
import java.awt.*;

public class NhanVienDialog extends JDialog {

    public JTextField txtMaNV, txtHoTen, txtNgaySinh, txtNgayVaoLam,txtSDT, txtEmail, txtDiaChi, txtLuong;
    public JComboBox<String> cbGioiTinh, cbChucVu, cbTrangThai;
    public JButton btnSave, btnCancel;

    public NhanVienDialog(Frame parent) {
        super(parent, "Nhân viên", true);
        setLayout(new GridLayout(0, 2, 5, 5));

        txtMaNV = new JTextField();
        txtHoTen = new JTextField();
        txtNgaySinh = new JTextField();
        txtNgayVaoLam = new JTextField();
        txtSDT = new JTextField();
        txtEmail = new JTextField();
        txtDiaChi = new JTextField();
        txtLuong = new JTextField();

        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cbChucVu = new JComboBox<>(new String[]{"Kế Toán", "Quản lý", "Tư vấn", "Kỹ Thuật"});
        cbTrangThai = new JComboBox<>(new String[]{"Đang làm", "Nghỉ việc"});

        add(new JLabel("Mã NV")); add(txtMaNV);
        add(new JLabel("Họ tên")); add(txtHoTen);
        add(new JLabel("Ngày sinh")); add(txtNgaySinh);
        add(new JLabel("Giới tính")); add(cbGioiTinh);
        add(new JLabel("SĐT")); add(txtSDT);
        add(new JLabel("Email")); add(txtEmail);
        add(new JLabel("Địa chỉ")); add(txtDiaChi);
        add(new JLabel("Chức vụ")); add(cbChucVu);
        add(new JLabel("Ngày vào làm")); add(txtNgayVaoLam);
        add(new JLabel("Lương")); add(txtLuong);
        add(new JLabel("Trạng thái")); add(cbTrangThai);

        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Đóng");
        add(btnSave); add(btnCancel);

        pack();
        setLocationRelativeTo(parent);
    }
}
