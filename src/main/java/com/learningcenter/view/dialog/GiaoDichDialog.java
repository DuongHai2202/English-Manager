package com.learningcenter.view.dialog;

import com.learningcenter.model.GiaoDich;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GiaoDichDialog extends JDialog {
    private JTextField txtIDGiaoDich, txtIDHocVien, txtSoTien, txtNgayGD;
    private JComboBox<String> cbLoaiGD, cbPhuongThuc, cbTrangThai;
    private JTextArea txtNoiDung;
    private JButton btnSave, btnCancel;
    private GiaoDich giaoDich;
    private boolean confirmed = false;

    private final Color BRAND_COLOR = new Color(74, 175, 110);

    public GiaoDichDialog(Frame parent, GiaoDich gd, boolean readOnly) {
        super(parent, gd == null ? "Thêm giao dịch mới"
                : (readOnly ? "Chi tiết giao dịch" : "Chỉnh sửa giao dịch"), true);
        this.giaoDich = gd;
        initComponents();
        if (gd != null) {
            loadGiaoDichData();
        } else {
            txtIDGiaoDich.setText("Tự động");
            // Mặc định ngày hiện tại cho giao dịch mới
            txtNgayGD.setText(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
        }
        if (readOnly) {
            setReadOnly();
        }
        pack();
        setLocationRelativeTo(parent);
    }

    private void setReadOnly() {
        txtIDGiaoDich.setEditable(false);
        txtIDHocVien.setEditable(false);
        txtSoTien.setEditable(false);
        txtNgayGD.setEditable(false);
        cbLoaiGD.setEnabled(false);
        cbPhuongThuc.setEnabled(false);
        cbTrangThai.setEnabled(false);
        txtNoiDung.setEditable(false);
        btnSave.setVisible(false);
        btnCancel.setText("Đóng");
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Form Fields
        addFormField(mainPanel, "Mã Giao dịch:", txtIDGiaoDich = new JTextField(20), gbc, 0);
        txtIDGiaoDich.setEditable(false); // ID luôn luôn không được sửa trực tiếp

        addFormField(mainPanel, "ID Học viên:", txtIDHocVien = new JTextField(20), gbc, 1);

        cbLoaiGD = new JComboBox<>(new String[] { "Thu học phí", "Hoàn phí", "Mua tài liệu", "Khác" });
        addFormField(mainPanel, "Loại giao dịch:", cbLoaiGD, gbc, 2);

        addFormField(mainPanel, "Số tiền:", txtSoTien = new JTextField(20), gbc, 3);

        cbPhuongThuc = new JComboBox<>(new String[] { "Tiền mặt", "Chuyển khoản", "Quẹt thẻ" });
        addFormField(mainPanel, "Phương thức:", cbPhuongThuc, gbc, 4);

        addFormField(mainPanel, "Ngày GD:", txtNgayGD = new JTextField(20), gbc, 5);

        cbTrangThai = new JComboBox<>(new String[] { "Hoàn thành", "Chờ xử lý", "Đã hủy" });
        addFormField(mainPanel, "Trạng thái:", cbTrangThai, gbc, 6);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Nội dung/Ghi chú:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        txtNoiDung = new JTextArea(4, 20);
        txtNoiDung.setLineWrap(true);
        txtNoiDung.setWrapStyleWord(true);
        txtNoiDung.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mainPanel.add(new JScrollPane(txtNoiDung), gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnSave = new JButton("Lưu giao dịch");
        btnSave.setBackground(BRAND_COLOR);
        btnSave.setForeground(Color.WHITE);
        btnSave.setPreferredSize(new Dimension(130, 35));
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
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private void loadGiaoDichData() {
        txtIDGiaoDich.setText(String.valueOf(giaoDich.getIdGiaoDich()));
        txtIDHocVien.setText(String.valueOf(giaoDich.getIdHocVien()));
        cbLoaiGD.setSelectedItem(giaoDich.getLoaiGiaoDich());
        txtSoTien.setText(String.valueOf(giaoDich.getSoTien()));
        cbPhuongThuc.setSelectedItem(giaoDich.getPhuongThucThanhToan());
        if (giaoDich.getNgayGiaoDich() != null) {
            txtNgayGD.setText(new SimpleDateFormat("dd/MM/yyyy").format(giaoDich.getNgayGiaoDich()));
        }
        cbTrangThai.setSelectedItem(giaoDich.getTrangThai());
        txtNoiDung.setText(giaoDich.getNoiDung());
    }

    private void onSave() {
        try {
            if (txtIDHocVien.getText().trim().isEmpty() || txtSoTien.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ ID Học viên và Số tiền!", "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (giaoDich == null)
                giaoDich = new GiaoDich();

            giaoDich.setIdHocVien(Integer.parseInt(txtIDHocVien.getText().trim()));
            giaoDich.setSoTien(Double.parseDouble(txtSoTien.getText().trim()));
            giaoDich.setLoaiGiaoDich(cbLoaiGD.getSelectedItem().toString());
            giaoDich.setPhuongThucThanhToan(cbPhuongThuc.getSelectedItem().toString());
            giaoDich.setTrangThai(cbTrangThai.getSelectedItem().toString());
            giaoDich.setNoiDung(txtNoiDung.getText().trim());

            String dateStr = txtNgayGD.getText().trim();
            if (!dateStr.isEmpty()) {
                java.util.Date parsedDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
                giaoDich.setNgayGiaoDich(new Date(parsedDate.getTime()));
            }

            confirmed = true;
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số tiền hoặc ID Học viên phải là số!", "Lỗi nhập liệu",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày tháng (dd/MM/yyyy)!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public GiaoDich getGiaoDich() {
        return giaoDich;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
