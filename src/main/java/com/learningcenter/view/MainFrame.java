package com.learningcenter.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.learningcenter.model.TaiKhoan;
import com.learningcenter.view.panel.StudentManagementPanel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    private TaiKhoan currentAccount;
    private JPanel sidebar;
    private JPanel contentArea;
    private CardLayout cardLayout;

    // Aesthetic colors from reference image
    private final Color SIDEBAR_COLOR = new Color(74, 175, 110); // Green
    private final Color SIDEBAR_HOVER = new Color(60, 150, 100);
    private final Color SIDEBAR_SELECTED = new Color(0, 188, 212); // Cyan highlight
    private final Color CONTENT_BG = new Color(245, 245, 245);

    public MainFrame(TaiKhoan tk) {
        this.currentAccount = tk;
        setTitle("HỆ THỐNG QUẢN TRỊ TRUNG TÂM ANH NGỮ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1420, 780);
        setMinimumSize(new Dimension(1024, 600));
        setLocationRelativeTo(null);
        // setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        // --- SIDEBAR ---
        sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(new Dimension(250, 0)); // Standard sidebar width

        // Sidebar Top (User Info)
        JPanel sbTop = new JPanel(new GridBagLayout());
        sbTop.setOpaque(false);
        sbTop.setBorder(new EmptyBorder(30, 10, 30, 10));
        GridBagConstraints gbcTop = new GridBagConstraints();
        gbcTop.gridx = 0;

        JLabel lblHi = new JLabel("HI !");
        lblHi.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHi.setForeground(Color.WHITE);
        gbcTop.gridy = 0;
        sbTop.add(lblHi, gbcTop);

        JLabel lblName = new JLabel(currentAccount.getHoTen());
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblName.setForeground(Color.WHITE);
        gbcTop.gridy = 1;
        gbcTop.insets = new Insets(10, 0, 0, 0);
        sbTop.add(lblName, gbcTop);

        sidebar.add(sbTop, BorderLayout.NORTH);

        // Sidebar Menu
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);

        addMenuItem(menuPanel, "Học viên", "STUDENTS");
        addMenuItem(menuPanel, "Nhân viên", "STAFF");
        addMenuItem(menuPanel, "Khóa học", "COURSES");
        addMenuItem(menuPanel, "Lớp học", "CLASSES");
        addMenuItem(menuPanel, "Lịch học", "SCHEDULES");
        addMenuItem(menuPanel, "Tài chính", "FINANCE");
        addMenuItem(menuPanel, "Bài viết", "POSTS");
        addMenuItem(menuPanel, "Thống kê", "DASHBOARD");
        addMenuItem(menuPanel, "Báo cáo", "REPORTS");

        JScrollPane scrollMenu = new JScrollPane(menuPanel);
        scrollMenu.setOpaque(false);
        scrollMenu.getViewport().setOpaque(false);
        scrollMenu.setBorder(null);
        sidebar.add(scrollMenu, BorderLayout.CENTER);

        // Sidebar Bottom
        JPanel sbBottom = new JPanel();
        sbBottom.setLayout(new BoxLayout(sbBottom, BoxLayout.Y_AXIS));
        sbBottom.setOpaque(false);
        sbBottom.setBorder(new EmptyBorder(0, 0, 20, 0));

        addBottomItem(sbBottom, "Đổi thông tin",
                e -> JOptionPane.showMessageDialog(this, "Chức năng đang phát triển"));
        addBottomItem(sbBottom, "Đăng xuất", e -> {
            dispose();
            new LoginView().setVisible(true);
        });

        sidebar.add(sbBottom, BorderLayout.SOUTH);

        add(sidebar, BorderLayout.WEST);

        // --- MAIN CONTENT AREA ---
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(CONTENT_BG);

        cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        // Các modules
        cardPanel.add(new StudentManagementPanel(), "STUDENTS");
        // test mockup
        cardPanel.add(createModulePanel("Nhân viên"), "STAFF");
        cardPanel.add(createModulePanel("Khóa học"), "COURSES");
        cardPanel.add(createModulePanel("Lớp học"), "CLASSES");
        cardPanel.add(createModulePanel("Lịch học"), "SCHEDULES");
        cardPanel.add(createModulePanel("Tài chính"), "FINANCE");
        cardPanel.add(createModulePanel("Bài viết"), "POSTS");
        cardPanel.add(createModulePanel("Dashboard"), "DASHBOARD");

        contentArea.add(cardPanel, BorderLayout.CENTER);
        add(contentArea, BorderLayout.CENTER);

        // Show default
        cardLayout.show(cardPanel, "STUDENTS");
    }

    private void addMenuItem(JPanel parent, String title, String cardName) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 12));
        item.setOpaque(false);
        item.setMaximumSize(new Dimension(250, 45));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel(title);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        item.add(label);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentArea.getComponent(0).getParent(), cardName); // contentArea structure
                resetMenuSelection();
                item.setOpaque(true);
                item.setBackground(SIDEBAR_SELECTED);
                item.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!item.isOpaque()) {
                    item.setOpaque(true);
                    item.setBackground(SIDEBAR_HOVER);
                    item.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (item.getBackground() != SIDEBAR_SELECTED) {
                    item.setOpaque(false);
                    item.repaint();
                }
            }
        });

        // Mặc định chọn học viên
        if (cardName.equals("STUDENTS")) {
            item.setOpaque(true);
            item.setBackground(SIDEBAR_SELECTED);
        }

        parent.add(item);
    }

    private void addBottomItem(JPanel parent, String title, java.awt.event.ActionListener action) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        item.setOpaque(false);
        // Sử dụng một màu xanh lá sáng hơn màu nền sidebar (không dùng Alpha)
        item.setBackground(new Color(95, 185, 125)); 
        item.setMaximumSize(new Dimension(220, 40));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel label = new JLabel(title);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        item.add(label);
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(null);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                item.setOpaque(true); // Hiện màu nền đặc
                item.repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                item.setOpaque(false); // Ẩn màu nền
                item.repaint();
            }
        });
        parent.add(item);
    }

    private void resetMenuSelection() {
        // Find the panel inside scroll pane
        Component menuPanel = ((JViewport) ((JScrollPane) sidebar.getComponent(1)).getComponent(0)).getComponent(0);
        for (Component c : ((JPanel) menuPanel).getComponents()) {
            if (c instanceof JComponent) {
                ((JComponent) c).setOpaque(false);
                c.setBackground(null);
            }
        }
    }

    private JPanel createModulePanel(String title) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        // Mocking the top toolbar from the image
        JPanel toolbarWrapper = new JPanel(new GridLayout(1, 2, 20, 0));
        toolbarWrapper.setOpaque(false);
        toolbarWrapper.setBorder(new EmptyBorder(20, 20, 10, 20));

        // LEFT: Functions
        JPanel funcPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        funcPanel.setBorder(BorderFactory.createTitledBorder("Chức năng Hệ thống"));
        funcPanel.setBackground(Color.WHITE);
        funcPanel.add(createIconButton("➕", "Thêm"));
        funcPanel.add(createIconButton("🗑️", "Xóa"));
        funcPanel.add(createIconButton("✏️", "Sửa"));
        funcPanel.add(createIconButton("👁️", "Chi tiết"));
        funcPanel.add(createIconButton("📑", "Xuất Excel"));

        // RIGHT: Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm & Lọc"));
        searchPanel.setBackground(Color.WHITE);

        JComboBox<String> cbFilter = new JComboBox<>(new String[] { "Tất cả", "Theo tên", "Theo mã" });
        cbFilter.setPreferredSize(new Dimension(100, 32));
        JTextField txtSearch = new JTextField(20);
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập từ khóa...");
        txtSearch.setPreferredSize(new Dimension(200, 32));

        JButton btnSearch = new JButton("Tìm");
        btnSearch.setPreferredSize(new Dimension(90, 32));
        btnSearch.setBackground(new Color(74, 175, 110));
        btnSearch.setForeground(Color.WHITE);

        JButton btnRefresh = new JButton("🔄 Làm mới");
        btnRefresh.setPreferredSize(new Dimension(100, 32));

        searchPanel.add(cbFilter);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        toolbarWrapper.add(funcPanel);
        toolbarWrapper.add(searchPanel);

        p.add(toolbarWrapper, BorderLayout.NORTH);

        // TABLE Placeholder
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        JLabel lblMock = new JLabel("Bảng danh sách " + title, SwingConstants.CENTER);
        lblMock.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        tablePanel.add(lblMock, BorderLayout.CENTER);

        p.add(tablePanel, BorderLayout.CENTER);

        return p;
    }

    private JPanel createIconButton(String icon, String text) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblIcon = new JLabel(icon, SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24));

        JLabel lblText = new JLabel(text, SwingConstants.CENTER);
        lblText.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        p.add(lblIcon, BorderLayout.CENTER);
        p.add(lblText, BorderLayout.SOUTH);

        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p.setOpaque(true);
                p.setBackground(new Color(240, 240, 240));
                p.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p.setOpaque(false);
                p.repaint();
            }
        });

        return p;
    }
}
