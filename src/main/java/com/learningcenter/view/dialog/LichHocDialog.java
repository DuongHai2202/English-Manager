package com.learningcenter.view.dialog;

import com.learningcenter.model.LichHoc;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class LichHocDialog extends JDialog {

    private JTextField txtMaLop, txtTenLop, txtGioBatDau, txtGioKetThuc, txtPhongHoc;
    private JComboBox<String> cbThu;
    private JButton btnSave, btnCancel;

    private LichHoc schedule;
    private boolean confirmed = false;

    private final Color BRAND_COLOR = new Color(74, 175, 110);

    public LichHocDialog(Frame parent, LichHoc schedule, boolean readOnly) {
        super(parent,
                schedule == null ? "Thêm lịch học mới"
                        : (readOnly ? "Chi tiết lịch học" : "Chỉnh sửa lịch học"),
                true);
        this.schedule = schedule;
        initComponents();

        if (schedule != null) {
            loadData();
        }
        if (readOnly) {
            setReadOnly();
        }

        pack();
        setLocationRelativeTo(parent);
    }

    private void setReadOnly() {
        txtMaLop.setEditable(false);
        txtTenLop.setEditable(false);
        cbThu.setEnabled(false);
        txtGioBatDau.setEditable(false);
        txtGioKetThuc.setEditable(false);
        txtPhongHoc.setEditable(false);
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

        addFormField(mainPanel, "Mã lớp:", txtMaLop = new JTextField(20), gbc, 0);
        addFormField(mainPanel, "Tên lớp:", txtTenLop = new JTextField(20), gbc, 1);

        cbThu = new JComboBox<>(new String[]{
                "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"
        });
        addFormField(mainPanel, "Thứ trong tuần:", cbThu, gbc, 2);

        addFormField(mainPanel, "Giờ bắt đầu (HH:mm):", txtGioBatDau = new JTextField(20), gbc, 3);
        addFormField(mainPanel, "Giờ kết thúc (HH:mm):", txtGioKetThuc = new JTextField(20), gbc, 4);
        addFormField(mainPanel, "Phòng học:", txtPhongHoc = new JTextField(20), gbc, 5);

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

    private void addFormField(JPanel panel, String label, JComponent field,
                              GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private void loadData() {
        txtMaLop.setText(schedule.getMaLopHoc());
        txtTenLop.setText(schedule.getTenLopHoc());
        cbThu.setSelectedItem(schedule.getThuTrongTuan());

        if (schedule.getGioBatDau() != null) {
            txtGioBatDau.setText(new SimpleDateFormat("HH:mm")
                    .format(schedule.getGioBatDau()));
        }
        if (schedule.getGioKetThuc() != null) {
            txtGioKetThuc.setText(new SimpleDateFormat("HH:mm")
                    .format(schedule.getGioKetThuc()));
        }
        txtPhongHoc.setText(schedule.getPhongHoc());
    }

    private void onSave() {
        if (txtMaLop.getText().trim().isEmpty()
                || txtTenLop.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập Mã lớp và Tên lớp!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Time gioBD = Time.valueOf(txtGioBatDau.getText().trim() + ":00");
            Time gioKT = Time.valueOf(txtGioKetThuc.getText().trim() + ":00");

            if (schedule == null) {
                schedule = new LichHoc();
            }

            schedule.setMaLopHoc(txtMaLop.getText().trim());
            schedule.setTenLopHoc(txtTenLop.getText().trim());
            schedule.setThuTrongTuan(cbThu.getSelectedItem().toString());
            schedule.setGioBatDau(gioBD);
            schedule.setGioKetThuc(gioKT);
            schedule.setPhongHoc(txtPhongHoc.getText().trim());

            confirmed = true;
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Giờ học không đúng định dạng (HH:mm)!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public LichHoc getSchedule() {
        return schedule;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
