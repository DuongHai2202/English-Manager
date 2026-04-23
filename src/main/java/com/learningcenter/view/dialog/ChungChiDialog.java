package com.learningcenter.view.dialog;

import com.learningcenter.model.ChungChi;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ChungChiDialog extends JDialog {
    private JTextField txtLoai, txtTen;
    private JTextArea txtMoTa;
    private JButton btnSave, btnCancel;
    private ChungChi chungChi;
    private boolean confirmed = false;

    private final Color BRAND_COLOR = new Color(74, 175, 110);

    public ChungChiDialog(Frame parent, ChungChi chungChi, boolean readOnly) {
        super(parent, chungChi == null ? "Thêm chứng chỉ mới" : "Cập nhật chứng chỉ", true);
        this.chungChi = chungChi;
        initComponents();
        if (chungChi != null) {
            loadData();
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
        gbc.insets = new Insets(8, 8, 8, 8);

        // Fields
        addFormField(mainPanel, "Loại chứng chỉ:", txtLoai = new JTextField(25), gbc, 0);
        addFormField(mainPanel, "Tên chứng chỉ:", txtTen = new JTextField(25), gbc, 1);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Mô tả ngắn:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtMoTa = new JTextArea(4, 25);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mainPanel.add(new JScrollPane(txtMoTa), gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Buttons
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
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private void loadData() {
        txtLoai.setText(chungChi.getLoaiChungChi());
        txtTen.setText(chungChi.getTenChungChi());
        txtMoTa.setText(chungChi.getMoTaNgan());
    }

    private void onSave() {
        if (txtLoai.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Loại và Tên chứng chỉ!");
            return;
        }

        if (chungChi == null) chungChi = new ChungChi();
        chungChi.setLoaiChungChi(txtLoai.getText().trim());
        chungChi.setTenChungChi(txtTen.getText().trim());
        chungChi.setMoTaNgan(txtMoTa.getText().trim());

        confirmed = true;
        dispose();
    }

    public ChungChi getChungChi() { return chungChi; }
    public boolean isConfirmed() { return confirmed; }
}