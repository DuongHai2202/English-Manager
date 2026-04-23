package com.learningcenter.view.dialog;

import com.learningcenter.model.NgayNghi;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NgayNghiDialog extends JDialog {
    private JTextField txtTen, txtNgayBatDau, txtNgayKetThuc;
    private JTextArea txtGhiChu;
    private JButton btnSave, btnCancel;
    private NgayNghi holiday;
    private boolean confirmed = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public NgayNghiDialog(Frame parent, NgayNghi holiday, boolean readOnly) {
        super(parent, holiday == null ? "Thêm ngày nghỉ" : (readOnly ? "Chi tiết" : "Sửa ngày nghỉ"), true);
        this.holiday = holiday;
        initComponents();
        if (holiday != null) loadData();
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
        gbc.insets = new Insets(5, 5, 5, 5);

        // Fields
        addFormField(mainPanel, "Tên ngày nghỉ:", txtTen = new JTextField(25), gbc, 0);
        addFormField(mainPanel, "Ngày bắt đầu (yyyy-MM-dd):", txtNgayBatDau = new JTextField(25), gbc, 1);
        addFormField(mainPanel, "Ngày kết thúc (yyyy-MM-dd):", txtNgayKetThuc = new JTextField(25), gbc, 2);

        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Ghi chú:"), gbc);
        gbc.gridx = 1;
        txtGhiChu = new JTextArea(4, 25);
        txtGhiChu.setLineWrap(true);
        mainPanel.add(new JScrollPane(txtGhiChu), gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Buttons
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
        txtTen.setText(holiday.getTenNgayNghi());
        txtNgayBatDau.setText(sdf.format(holiday.getNgayBatDau()));
        txtNgayKetThuc.setText(sdf.format(holiday.getNgayKetThuc()));
        txtGhiChu.setText(holiday.getGhiChu());
    }

    private void setReadOnly() {
        txtTen.setEditable(false);
        txtNgayBatDau.setEditable(false);
        txtNgayKetThuc.setEditable(false);
        txtGhiChu.setEditable(false);
        btnSave.setVisible(false);
        btnCancel.setText("Đóng");
    }

    private void onSave() {
        try {
            if (holiday == null) holiday = new NgayNghi();
            holiday.setTenNgayNghi(txtTen.getText().trim());
            holiday.setNgayBatDau(sdf.parse(txtNgayBatDau.getText().trim()));
            holiday.setNgayKetThuc(sdf.parse(txtNgayKetThuc.getText().trim()));
            holiday.setGhiChu(txtGhiChu.getText().trim());
            
            confirmed = true;
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày tháng phải đúng định dạng yyyy-MM-dd");
        }
    }

    public NgayNghi getHoliday() { return holiday; }
    public boolean isConfirmed() { return confirmed; }
}