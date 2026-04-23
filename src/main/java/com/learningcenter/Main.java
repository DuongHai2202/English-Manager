package com.learningcenter;

import com.formdev.flatlaf.FlatLightLaf;
import com.learningcenter.view.LoginView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        // Cấu hình giao diện hiện đại
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        // Chạy màn hình đăng nhập
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}
