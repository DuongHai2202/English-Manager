package com.learningcenter.view.dialog;

import com.learningcenter.model.BaiViet;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BaiVietDialog extends JDialog {

    private JTextField txtTieuDe;
    private JComboBox<String> cbLoai, cbTrangThai;
    private JTextArea txtNoiDung;
    private JButton btnSave, btnCancel;

    private BaiViet post;
    private boolean confirmed = false;

    private final Color BRAND_COLOR = new Color(74, 175, 110);

    public BaiVietDialog(Frame parent, BaiViet post, boolean readOnly) {
        super(parent,
                post == null ? "Thêm bài viết mới" : (readOnly ? "Chi tiết bài viết" : "Chỉnh sửa bài viết"),
                true);
        this.post = post;

        initComponents();

        if (post != null) {
            loadPostData();
        } else {
            cbTrangThai.setSelectedItem("Đã đăng");
            cbLoai.setSelectedItem("Tin tức");
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

        txtTieuDe = new JTextField(26);
        cbLoai = new JComboBox<>(new String[] { "Tin tức", "Thông báo" });
        cbTrangThai = new JComboBox<>(new String[] { "Đã đăng", "Nháp" });

        addFormField(mainPanel, "Tiêu đề:", txtTieuDe, gbc, 0);
        addFormField(mainPanel, "Loại bài viết:", cbLoai, gbc, 1);
        addFormField(mainPanel, "Trạng thái:", cbTrangThai, gbc, 2);

        // Nội dung
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        mainPanel.add(new JLabel("Nội dung:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        txtNoiDung = new JTextArea(10, 28);
        txtNoiDung.setLineWrap(true);
        txtNoiDung.setWrapStyleWord(true);
        txtNoiDung.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JScrollPane sp = new JScrollPane(txtNoiDung);
        sp.setPreferredSize(new Dimension(420, 220));
        mainPanel.add(sp, gbc);

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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private void loadPostData() {
        txtTieuDe.setText(post.getTieuDe());
        cbLoai.setSelectedItem(post.getLoaiBaiViet() != null ? post.getLoaiBaiViet() : "Tin tức");
        cbTrangThai.setSelectedItem(post.getTrangThai() != null ? post.getTrangThai() : "Đã đăng");
        txtNoiDung.setText(post.getNoiDung() != null ? post.getNoiDung() : "");
    }

    private void setReadOnly() {
        txtTieuDe.setEditable(false);
        cbLoai.setEnabled(false);
        cbTrangThai.setEnabled(false);
        txtNoiDung.setEditable(false);

        btnSave.setVisible(false);
        btnCancel.setText("Đóng");
    }

    private void onSave() {
        String tieuDe = txtTieuDe.getText().trim();
        String noiDung = txtNoiDung.getText().trim();

        if (tieuDe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Tiêu đề!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (noiDung.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Nội dung!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (post == null) post = new BaiViet();

        post.setTieuDe(tieuDe);
        post.setNoiDung(noiDung);
        post.setLoaiBaiViet(cbLoai.getSelectedItem().toString());
        post.setTrangThai(cbTrangThai.getSelectedItem().toString());

        confirmed = true;
        dispose();
    }

    public BaiViet getPost() {
        return post;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
