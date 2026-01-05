package com.learningcenter.view.dialog;

import com.learningcenter.model.TaiLieu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TaiLieuDialog extends JDialog {

    private JTextField txtIdKhoaHoc, txtTieuDe, txtTenFile, txtDuongDan;
    private JTextArea txtMoTa;
    private JButton btnSave, btnCancel;

    private TaiLieu taiLieu;
    private boolean confirmed = false;

    private final Color BRAND_COLOR = new Color(74, 175, 110);

    public TaiLieuDialog(Frame parent, TaiLieu taiLieu, boolean readOnly) {
        super(parent,
                taiLieu == null ? "Thêm tài liệu"
                        : (readOnly ? "Chi tiết tài liệu" : "Chỉnh sửa tài liệu"),
                true);

        this.taiLieu = taiLieu;
        initUI();

        if (taiLieu != null) {
            loadData();
        }
        if (readOnly) {
            setReadOnly();
        }

        pack();
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        addField(mainPanel, gbc, row++, "ID Khóa học:", txtIdKhoaHoc = new JTextField(20));
        addField(mainPanel, gbc, row++, "Tiêu đề:", txtTieuDe = new JTextField(20));
        addField(mainPanel, gbc, row++, "Tên file:", txtTenFile = new JTextField(20));
        addField(mainPanel, gbc, row++, "Đường dẫn file:", txtDuongDan = new JTextField(20));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Mô tả:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        txtMoTa = new JTextArea(4, 20);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mainPanel.add(new JScrollPane(txtMoTa), gbc);

        add(mainPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnPanel.setBackground(new Color(245, 245, 245));

        btnSave = new JButton("Lưu");
        btnSave.setBackground(BRAND_COLOR);
        btnSave.setForeground(Color.WHITE);
        btnSave.setPreferredSize(new Dimension(100, 36));
        btnSave.addActionListener(e -> onSave());

        btnCancel = new JButton("Hủy");
        btnCancel.setPreferredSize(new Dimension(100, 36));
        btnCancel.addActionListener(e -> dispose());

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);

        add(btnPanel, BorderLayout.SOUTH);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row,
                          String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private void loadData() {
        txtIdKhoaHoc.setText(String.valueOf(taiLieu.getIdKhoaHoc()));
        txtTieuDe.setText(taiLieu.getTieuDe());
        txtMoTa.setText(taiLieu.getMoTa());
        txtTenFile.setText(taiLieu.getTenFile());
        txtDuongDan.setText(taiLieu.getDuongDanFile());
    }

    private void setReadOnly() {
        txtIdKhoaHoc.setEditable(false);
        txtTieuDe.setEditable(false);
        txtMoTa.setEditable(false);
        txtTenFile.setEditable(false);
        txtDuongDan.setEditable(false);
        btnSave.setVisible(false);
        btnCancel.setText("Đóng");
    }

    private void onSave() {
        if (txtTieuDe.getText().trim().isEmpty()
                || txtTenFile.getText().trim().isEmpty()
                || txtDuongDan.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ Tiêu đề, Tên file và Đường dẫn!",
                    "Thiếu dữ liệu",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            int idKhoaHoc = Integer.parseInt(txtIdKhoaHoc.getText().trim());

            if (taiLieu == null) {
                taiLieu = new TaiLieu();
            }

            taiLieu.setIdKhoaHoc(idKhoaHoc);
            taiLieu.setTieuDe(txtTieuDe.getText().trim());
            taiLieu.setMoTa(txtMoTa.getText().trim());
            taiLieu.setTenFile(txtTenFile.getText().trim());
            taiLieu.setDuongDanFile(txtDuongDan.getText().trim());

            confirmed = true;
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "ID Khóa học phải là số!",
                    "Lỗi dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public TaiLieu getTaiLieu() {
        return taiLieu;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
