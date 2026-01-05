package com.learningcenter.view.dialog;

import com.learningcenter.model.CoSoVatChat;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CoSoVatChatDialog extends JDialog {
    private JTextField txtTen, txtLoai, txtSoLuong, txtDonVi, txtNgayMua;
    private JComboBox<String> cbTinhTrang;
    private JTextArea txtGhiChu;
    private JButton btnSave, btnCancel;
    private CoSoVatChat csvc;
    private boolean confirmed = false;

    private final Color BRAND_COLOR = new Color(74, 175, 110);

    public CoSoVatChatDialog(Frame owner, CoSoVatChat item, boolean readOnly) {
        super(owner, item == null ? "Thêm cơ sở vật chất mới"
                : (readOnly ? "Chi tiết cơ sở vật chất" : "Chỉnh sửa cơ sở vật chất"), true);
        this.csvc = item;
        initComponents();
        if (item != null) {
            loadData();
        } else {
            txtNgayMua.setText(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
        }

        if (readOnly) {
            setReadOnly();
        }

        pack();
        setLocationRelativeTo(owner);
    }

    private void setReadOnly() {
        txtTen.setEditable(false);
        txtLoai.setEditable(false);
        txtSoLuong.setEditable(false);
        txtDonVi.setEditable(false);
        cbTinhTrang.setEnabled(false);
        txtNgayMua.setEditable(false);
        txtGhiChu.setEditable(false);
        btnSave.setVisible(false);
        btnCancel.setText("Đóng");
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        addFormField(mainPanel, "Tên vật chất:", txtTen = new JTextField(25), gbc, 0);
        addFormField(mainPanel, "Loại:", txtLoai = new JTextField(25), gbc, 1);
        addFormField(mainPanel, "Số lượng:", txtSoLuong = new JTextField(25), gbc, 2);
        addFormField(mainPanel, "Đơn vị tính:", txtDonVi = new JTextField(25), gbc, 3);

        cbTinhTrang = new JComboBox<>(new String[] { "Tốt", "Hỏng", "Cần bảo trì", "Đã thanh lý" });
        addFormField(mainPanel, "Tình trạng:", cbTinhTrang, gbc, 4);

        addFormField(mainPanel, "Ngày mua (dd/MM/yyyy):", txtNgayMua = new JTextField(25), gbc, 5);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Ghi chú:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtGhiChu = new JTextArea(4, 25);
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mainPanel.add(new JScrollPane(txtGhiChu), gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnPanel.setBackground(new Color(245, 245, 245));

        btnSave = new JButton("Lưu dữ liệu");
        btnSave.setPreferredSize(new Dimension(120, 35));
        btnSave.setBackground(BRAND_COLOR);
        btnSave.setForeground(Color.WHITE);

        btnCancel = new JButton("Hủy bỏ");
        btnCancel.setPreferredSize(new Dimension(100, 35));

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());
    }

    private void addFormField(JPanel panel, String label, JComponent comp, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(comp, gbc);
    }

    private void loadData() {
        txtTen.setText(csvc.getTenCoSoVatChat());
        txtLoai.setText(csvc.getLoaiCoSoVatChat());
        txtSoLuong.setText(String.valueOf(csvc.getSoLuong()));
        txtDonVi.setText(csvc.getDonViTinh());
        cbTinhTrang.setSelectedItem(csvc.getTinhTrang());
        txtGhiChu.setText(csvc.getGhiChu());
        if (csvc.getNgayMua() != null) {
            txtNgayMua.setText(new SimpleDateFormat("dd/MM/yyyy").format(csvc.getNgayMua()));
        }
    }

    private void onSave() {
        if (txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên cơ sở vật chất!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (csvc == null)
                csvc = new CoSoVatChat();
            csvc.setTenCoSoVatChat(txtTen.getText().trim());
            csvc.setLoaiCoSoVatChat(txtLoai.getText().trim());
            csvc.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));
            csvc.setDonViTinh(txtDonVi.getText().trim());
            csvc.setTinhTrang(cbTinhTrang.getSelectedItem().toString());
            csvc.setGhiChu(txtGhiChu.getText().trim());
            if (!txtNgayMua.getText().trim().isEmpty()) {
                csvc.setNgayMua(new SimpleDateFormat("dd/MM/yyyy").parse(txtNgayMua.getText().trim()));
            }
            confirmed = true;
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là con số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public CoSoVatChat getCoSoVatChat() {
        return csvc;
    }
}
