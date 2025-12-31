package com.learningcenter.view.dialog;

import com.learningcenter.model.HocVien;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HocVienDialog extends JDialog {
    private JTextField txtMa, txtTen, txtNgaySinh, txtSDT, txtEmail, txtTrinhDo;
    private JComboBox<String> cbGioiTinh, cbTrangThai;
    private JTextArea txtDiaChi, txtGhiChu;
    private JButton btnSave, btnCancel;
    private HocVien student;
    private boolean confirmed = false;

    private final Color BRAND_COLOR = new Color(74, 175, 110);

    public HocVienDialog(Frame parent, HocVien student, boolean readOnly) {
        super(parent, student == null ? "Thêm học viên mới"
                : (readOnly ? "Chi tiết học viên" : "Chỉnh sửa thông tin học viên"), true);
        this.student = student;
        initComponents();
        if (student != null) {
            loadStudentData();
        }
        if (readOnly) {
            setReadOnly();
        }
        pack();
        setLocationRelativeTo(parent);
    }

    private void setReadOnly() {
        txtMa.setEditable(false);
        txtTen.setEditable(false);
        txtNgaySinh.setEditable(false);
        txtSDT.setEditable(false);
        txtEmail.setEditable(false);
        txtTrinhDo.setEditable(false);
        cbGioiTinh.setEnabled(false);
        cbTrangThai.setEnabled(false);
        txtDiaChi.setEditable(false);
        txtGhiChu.setEditable(false);
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
        gbc.insets = new Insets(5, 5, 5, 5);

        // Form Fields
        addFormField(mainPanel, "Mã học viên:", txtMa = new JTextField(20), gbc, 0);
        addFormField(mainPanel, "Họ và tên:", txtTen = new JTextField(20), gbc, 1);
        addFormField(mainPanel, "Ngày sinh (dd/MM/yyyy):", txtNgaySinh = new JTextField(20), gbc, 2);

        cbGioiTinh = new JComboBox<>(new String[] { "Nam", "Nữ", "Khác" });
        addFormField(mainPanel, "Giới tính:", cbGioiTinh, gbc, 3);

        addFormField(mainPanel, "Số điện thoại:", txtSDT = new JTextField(20), gbc, 4);
        addFormField(mainPanel, "Email:", txtEmail = new JTextField(20), gbc, 5);
        addFormField(mainPanel, "Trình độ:", txtTrinhDo = new JTextField(20), gbc, 6);

        cbTrangThai = new JComboBox<>(new String[] { "Đang học", "Đã tốt nghiệp", "Bảo lưu", "Nghỉ học" });
        addFormField(mainPanel, "Trạng thái:", cbTrangThai, gbc, 7);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtDiaChi = new JTextArea(3, 20);
        txtDiaChi.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mainPanel.add(new JScrollPane(txtDiaChi), gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Ghi chú:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtGhiChu = new JTextArea(3, 20);
        txtGhiChu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        mainPanel.add(new JScrollPane(txtGhiChu), gbc);

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
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private void loadStudentData() {
        txtMa.setText(student.getMaHocVien());
        txtTen.setText(student.getHoTen());
        if (student.getNgaySinh() != null) {
            txtNgaySinh.setText(new SimpleDateFormat("dd/MM/yyyy").format(student.getNgaySinh()));
        }
        cbGioiTinh.setSelectedItem(student.getGioiTinh());
        txtSDT.setText(student.getSoDienThoai());
        txtEmail.setText(student.getEmail());
        txtTrinhDo.setText(student.getTrinhDo());
        cbTrangThai.setSelectedItem(student.getTrangThai());
        txtDiaChi.setText(student.getDiaChi());
        txtGhiChu.setText(student.getGhiChu());
    }

    private void onSave() {
        if (txtMa.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã và Họ tên học viên!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (student == null)
            student = new HocVien();
        student.setMaHocVien(txtMa.getText().trim());
        student.setHoTen(txtTen.getText().trim());
        try {
            if (!txtNgaySinh.getText().trim().isEmpty()) {
                student.setNgaySinh(new SimpleDateFormat("dd/MM/yyyy").parse(txtNgaySinh.getText().trim()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng (dd/MM/yyyy)!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        student.setGioiTinh(cbGioiTinh.getSelectedItem().toString());
        student.setSoDienThoai(txtSDT.getText().trim());
        student.setEmail(txtEmail.getText().trim());
        student.setTrinhDo(txtTrinhDo.getText().trim());
        student.setTrangThai(cbTrangThai.getSelectedItem().toString());
        student.setDiaChi(txtDiaChi.getText().trim());
        student.setGhiChu(txtGhiChu.getText().trim());

        confirmed = true;
        dispose();
    }

    public HocVien getStudent() {
        return student;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
