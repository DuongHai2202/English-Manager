package com.learningcenter.view.dialog;

import com.learningcenter.model.PhongHoc;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PhongHocDialog extends JDialog {
    private JTextField txtTen, txtSucChua, txtTang;
    private JComboBox<String> cbTrangThai;
    private JButton btnSave, btnCancel;
    private PhongHoc phongHoc;
    private boolean confirmed = false;

    public PhongHocDialog(Frame parent, PhongHoc phongHoc, boolean readOnly) {
        super(parent, phongHoc == null ? "Thêm phòng học" : (readOnly ? "Chi tiết phòng học" : "Sửa phòng học"), true);
        this.phongHoc = phongHoc;
        initComponents();
        if (phongHoc != null) loadData();
        if (readOnly) setReadOnly();
        
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Các trường nhập liệu
        addFormField(mainPanel, "Tên phòng học:", txtTen = new JTextField(20), gbc, 0);
        addFormField(mainPanel, "Sức chứa:", txtSucChua = new JTextField(20), gbc, 1);
        addFormField(mainPanel, "Tầng:", txtTang = new JTextField(20), gbc, 2);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        mainPanel.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        cbTrangThai = new JComboBox<>(new String[]{"Trống", "Đang sử dụng", "Bảo trì", "Đang sửa chữa"});
        mainPanel.add(cbTrangThai, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Nút bấm
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        
        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());
        
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel panel, String label, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private void loadData() {
        txtTen.setText(phongHoc.getTenPhong());
        txtSucChua.setText(String.valueOf(phongHoc.getSucChua()));
        txtTang.setText(String.valueOf(phongHoc.getTang()));
        cbTrangThai.setSelectedItem(phongHoc.getTrangThai());
    }

    private void setReadOnly() {
        txtTen.setEditable(false);
        txtSucChua.setEditable(false);
        txtTang.setEditable(false);
        cbTrangThai.setEnabled(false);
        btnSave.setVisible(false);
        btnCancel.setText("Đóng");
    }

    private void onSave() {
        try {
            String ten = txtTen.getText().trim();
            int sucChua = Integer.parseInt(txtSucChua.getText().trim());
            int tang = Integer.parseInt(txtTang.getText().trim());
            String trangThai = cbTrangThai.getSelectedItem().toString();

            if (ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên phòng không được để trống!");
                return;
            }

            if (phongHoc == null) phongHoc = new PhongHoc();
            phongHoc.setTenPhong(ten);
            phongHoc.setSucChua(sucChua);
            phongHoc.setTang(tang);
            phongHoc.setTrangThai(trangThai);
            
            confirmed = true;
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Sức chứa và Tầng phải là số nguyên!");
        }
    }

    public PhongHoc getPhongHoc() { return phongHoc; }
    public boolean isConfirmed() { return confirmed; }
}