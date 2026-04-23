package com.learningcenter.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.learningcenter.dao.TaiKhoanDAO;
import com.learningcenter.model.TaiKhoan;
import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblStatus;

    // Aesthetic colors
    private final Color BRAND_COLOR = new Color(74, 175, 110); // Green from image

    public LoginView() {
        setTitle("Đăng nhập - Hệ thống Quản trị");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600); // Increased size
        setLocationRelativeTo(null);
        setResizable(false);

        initComponent();
    }

    private void initComponent() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- LEFT PANEL (Branding) ---
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(BRAND_COLOR);
        GridBagConstraints gbcL = new GridBagConstraints();
        gbcL.gridx = 0;

        // Logo/Icon
        try {
            String iconPath = "/com/learningcenter/util/icons/icon_educationpng.png";
            URL imgUrl = getClass().getResource(iconPath);
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                JLabel lblLogoIcon = new JLabel(new ImageIcon(img));
                gbcL.gridy = 0;
                leftPanel.add(lblLogoIcon, gbcL);
            } else {
                // Fallback to emoji if file missing
                JLabel lblLogoIcon = new JLabel("");
                lblLogoIcon.setFont(new Font("Segoe UI", Font.PLAIN, 120));
                lblLogoIcon.setForeground(Color.WHITE);
                gbcL.gridy = 0;
                leftPanel.add(lblLogoIcon, gbcL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblCenterName = new JLabel("LEARNING CENTER");
        lblCenterName.setFont(new Font("Segoe UI", Font.BOLD, 36)); // Bigger font
        lblCenterName.setForeground(Color.WHITE);
        gbcL.gridy = 1;
        gbcL.insets = new Insets(30, 0, 0, 0);
        leftPanel.add(lblCenterName, gbcL);

        JLabel lblDesc = new JLabel("Hệ thống quản lý trung tâm anh ngữ"); // More professional text
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblDesc.setForeground(new Color(230, 255, 230));
        gbcL.gridy = 2;
        gbcL.insets = new Insets(15, 0, 0, 0);
        leftPanel.add(lblDesc, gbcL);

        // --- RIGHT PANEL (Form) ---
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(50, 80, 50, 80)); // More padding
        GridBagConstraints gbcR = new GridBagConstraints();
        gbcR.fill = GridBagConstraints.HORIZONTAL;
        gbcR.weightx = 1.0;
        gbcR.gridx = 0;

        JLabel lblLogin = new JLabel("ĐĂNG NHẬP");
        lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Bigger font
        lblLogin.setForeground(new Color(50, 50, 50));
        gbcR.gridy = 0;
        gbcR.insets = new Insets(0, 0, 50, 0);
        rightPanel.add(lblLogin, gbcR);

        // Username
        JLabel lblUser = new JLabel("Tên đăng nhập:");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbcR.gridy = 1;
        gbcR.insets = new Insets(0, 0, 5, 0);
        rightPanel.add(lblUser, gbcR);

        txtUsername = new JTextField();
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "admin");
        txtUsername.setPreferredSize(new Dimension(300, 40));
        gbcR.gridy = 2;
        gbcR.insets = new Insets(0, 0, 20, 0);
        rightPanel.add(txtUsername, gbcR);

        // Password
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbcR.gridy = 3;
        gbcR.insets = new Insets(0, 0, 5, 0);
        rightPanel.add(lblPass, gbcR);

        txtPassword = new JPasswordField();
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "••••••");
        txtPassword.setPreferredSize(new Dimension(300, 40));
        gbcR.gridy = 4;
        gbcR.insets = new Insets(0, 0, 30, 0);
        rightPanel.add(txtPassword, gbcR);

        // Button
        btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setPreferredSize(new Dimension(300, 45));
        btnLogin.setBackground(BRAND_COLOR);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setFocusPainted(false);
        gbcR.gridy = 5;
        gbcR.insets = new Insets(0, 0, 15, 0);
        rightPanel.add(btnLogin, gbcR);

        lblStatus = new JLabel(" ", SwingConstants.CENTER);
        lblStatus.setForeground(Color.RED);
        gbcR.gridy = 6;
        rightPanel.add(lblStatus, gbcR);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        // Action
        btnLogin.addActionListener(e -> handleLogin());
        txtPassword.addActionListener(e -> handleLogin());

        // Hover effect for button
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(60, 150, 100));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(BRAND_COLOR);
            }
        });
    }

    private void handleLogin() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            lblStatus.setText("Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        TaiKhoanDAO dao = new TaiKhoanDAO();
        TaiKhoan tk = dao.dangNhap(user, pass);

        if (tk != null) {
            this.dispose();
            // Open MainFrame
            SwingUtilities.invokeLater(() -> {
                new MainFrame(tk).setVisible(true);
            });
        } else {
            lblStatus.setText("Sai tài khoản hoặc mật khẩu!");
        }
    }
}
