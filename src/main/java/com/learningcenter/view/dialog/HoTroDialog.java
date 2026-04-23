package com.learningcenter.view.dialog;

import com.learningcenter.model.HoTro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HoTroDialog extends JDialog {

    private JTextField txtNguoiGui, txtEmail, txtTieuDe, txtChuyenMuc;
    private JComboBox<String> cbUuTien, cbTrangThai;
    private JTextArea txtNoiDung;

    private JButton btnSave, btnCancel;

    private HoTro support;
    private boolean confirmed = false;

    private final Color BRAND_COLOR = new Color(74, 175, 110);

    public HoTroDialog(Frame parent, HoTro support, boolean readOnly) {
        super(parent,
                support == null ? "Thêm yêu cầu hỗ trợ"
                        : (readOnly ? "Chi tiết yêu cầu hỗ trợ" : "Chỉnh sửa yêu cầu hỗ trợ"),
                true);

        this.support = support;

        initComponents();

        if (support != null) {
            loadData();
        }

        if (readOnly) {
            setReadOnly();
        }

        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        addFormField(mainPanel, "Người gửi:", txtNguoiGui = new JTextField(22), gbc, 0);
        addFormField(mainPanel, "Email:", txtEmail = new JTextField(22), gbc, 1);
        addFormField(mainPanel, "Tiêu đề:", txtTieuDe = new JTextField(22), gbc, 2);
        addFormField(mainPanel, "Chuyên mục:", txtChuyenMuc = new JTextField(22), gbc, 3);

        cbUuTien = new JComboBox<>(new String[]{"Thấp", "Trung bình", "Cao"});
        addFormField(mainPanel, "Độ ưu tiên:", cbUuTien, gbc, 4);

        cbTrangThai = new JComboBox<>(new String[]{"Mở", "Đang xử lý", "Đã xử lý", "Đóng"});
        addFormField(mainPanel, "Trạng thái:", cbTrangThai, gbc, 5);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        mainPanel.add(new JLabel("Nội dung:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        txtNoiDung = new JTextArea(6, 22);
        txtNoiDung.setLineWrap(true);
        txtNoiDung.setWrapStyleWord(true);
        txtNoiDung.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JScrollPane sp = new JScrollPane(txtNoiDung);
        sp.setPreferredSize(new Dimension(350, 130));
        mainPanel.add(sp, gbc);

        add(mainPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnPanel.setBackground(new Color(245, 245, 245));

        btnSave = new JButton("Lưu dữ liệu");
        btnSave.setBackground(BRAND_COLOR);
        btnSave.setForeground(Color.WHITE);
        btnSave.setPreferredSize(new Dimension(120, 35));
        btnSave.addActionListener(e -> onSave());

        btnCancel = new JButton("Hủy bỏ");
        btnCancel.setPreferredSize(new Dimension(100, 35));
        btnCancel.addActionListener(e -> dispose());

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        add(btnPanel, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel panel, String label, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private void loadData() {
        txtNguoiGui.setText(support.getTenNguoiGui());
        txtEmail.setText(support.getEmailNguoiGui());
        txtTieuDe.setText(support.getTieuDe());
        txtChuyenMuc.setText(support.getChuyenMuc());

        if (support.getDoUuTien() != null) cbUuTien.setSelectedItem(support.getDoUuTien());
        if (support.getTrangThai() != null) cbTrangThai.setSelectedItem(support.getTrangThai());

        txtNoiDung.setText(support.getNoiDung());
    }

    private void setReadOnly() {
        txtNguoiGui.setEditable(false);
        txtEmail.setEditable(false);
        txtTieuDe.setEditable(false);
        txtChuyenMuc.setEditable(false);
        cbUuTien.setEnabled(false);
        cbTrangThai.setEnabled(false);
        txtNoiDung.setEditable(false);

        btnSave.setVisible(false);
        btnCancel.setText("Đóng");
    }

    private void onSave() {
        String ten = txtNguoiGui.getText().trim();
        String tieuDe = txtTieuDe.getText().trim();
        String noiDung = txtNoiDung.getText().trim();

        if (ten.isEmpty() || tieuDe.isEmpty() || noiDung.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập: Người gửi, Tiêu đề, Nội dung!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (support == null) support = new HoTro();

        support.setTenNguoiGui(ten);
        support.setEmailNguoiGui(txtEmail.getText().trim());
        support.setTieuDe(tieuDe);
        support.setNoiDung(noiDung);
        support.setChuyenMuc(txtChuyenMuc.getText().trim());
        support.setDoUuTien(cbUuTien.getSelectedItem().toString());
        support.setTrangThai(cbTrangThai.getSelectedItem().toString());

        confirmed = true;
        dispose();
    }

    public HoTro getSupport() {
        return support;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
