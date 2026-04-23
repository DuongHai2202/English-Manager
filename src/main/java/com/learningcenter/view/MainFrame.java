package com.learningcenter.view;

import com.learningcenter.controller.GiangVienController;
import com.learningcenter.controller.KhoaHocController;
import com.learningcenter.controller.LopHocController;
import com.learningcenter.controller.NhanVienController;
import com.learningcenter.controller.PhanLopController;
import com.learningcenter.model.TaiKhoan;
import com.learningcenter.view.panel.GiaoDichPanel;
import com.learningcenter.view.panel.HocVienPanel;
import com.learningcenter.view.panel.DashboardPanel;
import com.learningcenter.view.panel.CoSoVatChatPanel;
import com.learningcenter.view.panel.GiangVienPanel;
import com.learningcenter.view.panel.KhoaHocPanel;
import com.learningcenter.view.panel.LopHocPanel;
import com.learningcenter.view.panel.StaffManagementPanel;
import com.learningcenter.view.panel.ScheduleManagementPanel;
import com.learningcenter.view.panel.DocumentManagementPanel;
import com.learningcenter.view.panel.PostManagementPanel;
import com.learningcenter.view.panel.SupportManagementPanel;
import com.learningcenter.controller.LichHocController;
import com.learningcenter.controller.TaiLieuController;
import com.learningcenter.controller.BaiVietController;
import com.learningcenter.controller.HoTroController;
import com.learningcenter.controller.ChungChiController;
import com.learningcenter.controller.NgayNghiController;
import com.learningcenter.controller.PhongHocController;
import com.learningcenter.view.panel.ChungChiPanel;
import com.learningcenter.view.panel.NgayNghiPanel;
import com.learningcenter.view.panel.PhongHocPanel;
import com.learningcenter.view.panel.DiemDanhPanel; // THỊNH
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
    private JPanel cardPanel;

    // Aesthetic colors from reference image
    private final Color SIDEBAR_COLOR = new Color(74, 175, 110);
    private final Color SIDEBAR_HOVER = new Color(60, 150, 100);
    private final Color SIDEBAR_SELECTED = new Color(0, 188, 212);
    private final Color CONTENT_BG = new Color(245, 245, 245);

    public MainFrame(TaiKhoan tk) {
        this.currentAccount = tk;
        setTitle("HỆ THỐNG QUẢT TRỊ TRUNG TÂM ANH NGỮ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1420, 780);
        setMinimumSize(new Dimension(1024, 600));
        setLocationRelativeTo(null);

        // Logo
        try {
            ImageIcon icon = new ImageIcon(
                    getClass().getResource("/com/learningcenter/util/icons/logo-trung-tam-tieng-anh.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Không thể tải logo ứng dụng: " + e.getMessage());
        }

        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        // --- SIDEBAR ---
        sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(new Dimension(250, 0));

        // Sidebar Top
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

        addMenuItem(menuPanel, "Thống kê", "DASHBOARD");
        addMenuItem(menuPanel, "Học viên", "STUDENTS");
        addMenuItem(menuPanel, "Nhân viên", "STAFF");
        addMenuItem(menuPanel, "Quản lý Giảng viên", "MNTEACHER");
        addMenuItem(menuPanel, "Khóa học", "COURSES");
        addClassMenu(menuPanel);
        addMenuItem(menuPanel, "Lịch học", "SCHEDULES");
        addMenuItem(menuPanel, "Tài chính", "FINANCE");
        addMenuItem(menuPanel, "Bài viết", "POSTS");
        addMenuItem(menuPanel, "Tài liệu", "DOCUMENTS");
        addMenuItem(menuPanel, "Cơ sở vật chất", "FACILITIES");
        addMenuItem(menuPanel, "Hỗ trợ", "SUPPORT");
        addMenuItem(menuPanel, "Quản lý Chứng chỉ", "CERTIFICATES");
        addMenuItem(menuPanel, "Ngày nghỉ", "HOLIDAYS");
        addMenuItem(menuPanel, "Phòng học", "ROOMS");

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
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        // --- Modules Registration ---

        // Hải
        cardPanel.add(new DashboardPanel(), "DASHBOARD");
        cardPanel.add(new HocVienPanel(), "STUDENTS");
        cardPanel.add(new GiaoDichPanel(), "FINANCE");
        cardPanel.add(new CoSoVatChatPanel(), "FACILITIES");

        // Hưng
        StaffManagementPanel staffPanel = new StaffManagementPanel();
        new NhanVienController(staffPanel);
        cardPanel.add(staffPanel, "STAFF");
        GiangVienPanel gvPanel = new GiangVienPanel();
        new GiangVienController(gvPanel);
        cardPanel.add(gvPanel, "MNTEACHER");
        KhoaHocPanel khPanel = new KhoaHocPanel();
        new KhoaHocController(khPanel);
        cardPanel.add(khPanel, "COURSES");
        LopHocPanel lhPanel = new LopHocPanel();
        new LopHocController(lhPanel);
        cardPanel.add(lhPanel, "CLASS_MANAGEMENT");
        PhanLop pl = new PhanLop();
        new PhanLopController(pl);
        cardPanel.add(pl, "CLASS_ASSIGN");

        // Huy
        ScheduleManagementPanel schedulePanel = new ScheduleManagementPanel();
        new LichHocController(schedulePanel);
        cardPanel.add(schedulePanel, "SCHEDULES");

        DocumentManagementPanel docPanel = new DocumentManagementPanel();
        new TaiLieuController(docPanel);
        cardPanel.add(docPanel, "DOCUMENTS");

        PostManagementPanel postPanel = new PostManagementPanel();
        new BaiVietController(postPanel);
        cardPanel.add(postPanel, "POSTS");

        SupportManagementPanel supportPanel = new SupportManagementPanel();
        new HoTroController(supportPanel);
        cardPanel.add(supportPanel, "SUPPORT");

        // THỊNH
        ChungChiPanel ccPanel = new ChungChiPanel();
        ChungChiController ccController = new ChungChiController(ccPanel);
        ccController.loadDataToTable();
        cardPanel.add(ccPanel, "CERTIFICATES");

        NgayNghiPanel nnPanel = new NgayNghiPanel();
        NgayNghiController nnController = new NgayNghiController(nnPanel);
        nnController.loadDataToTable();
        cardPanel.add(nnPanel, "HOLIDAYS");

        PhongHocPanel phPanel = new PhongHocPanel();
        PhongHocController phController = new PhongHocController(phPanel);
        phController.loadDataToTable();
        cardPanel.add(phPanel, "ROOMS");
        
        DiemDanhPanel ddPanel = new DiemDanhPanel();
        cardPanel.add(ddPanel, "ATTENDANCE");

        contentArea.add(cardPanel, BorderLayout.CENTER);
        add(contentArea, BorderLayout.CENTER);

        // Show default
        cardLayout.show(cardPanel, "DASHBOARD");
        highlightDashboardMenu();
    }

    private void addClassMenu(JPanel parent) {
        JPanel classMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 12));
        classMenu.setOpaque(false);
        classMenu.setMaximumSize(new Dimension(250, 45));
        classMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblTitle = new JLabel("Lớp học ▶");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        classMenu.add(lblTitle);

        JPanel subMenu = new JPanel();
        subMenu.setLayout(new BoxLayout(subMenu, BoxLayout.Y_AXIS));
        subMenu.setOpaque(false);
        subMenu.setVisible(false);

        subMenu.add(createSubMenuItem("Quản lý lớp học", "CLASS_MANAGEMENT"));
        subMenu.add(createSubMenuItem("Phân lớp", "CLASS_ASSIGN"));
        subMenu.add(createSubMenuItem("Điểm danh học viên", "ATTENDANCE")); // THỊNH

        classMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean show = !subMenu.isVisible();
                subMenu.setVisible(show);
                lblTitle.setText(show ? "Lớp học ▼" : "Lớp học ▶");
                parent.revalidate();
            }
        });

        parent.add(classMenu);
        parent.add(subMenu);
    }

    private JPanel createSubMenuItem(String title, String cardName) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 45, 10));
        item.setOpaque(false);
        item.setMaximumSize(new Dimension(250, 40));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel(title);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        item.add(label);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, cardName);
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

        return item;
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
                cardLayout.show(cardPanel, cardName);
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

        parent.add(item);
    }

    private void addBottomItem(JPanel parent, String title, java.awt.event.ActionListener action) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        item.setOpaque(false);
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
                item.setOpaque(true);
                item.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                item.setOpaque(false);
                item.repaint();
            }
        });
        parent.add(item);
    }

    private void resetMenuSelection() {
        Component menuViewport = ((JViewport) ((JScrollPane) sidebar.getComponent(1)).getComponent(0)).getComponent(0);
        resetSelectionRecursive((Container) menuViewport);
    }

    private void resetSelectionRecursive(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JPanel) {
                JPanel p = (JPanel) c;
                p.setOpaque(false);
                p.setBackground(null);
                resetSelectionRecursive(p);
            }
        }
    }

    private void highlightDashboardMenu() {
        Component menuViewport = ((JViewport) ((JScrollPane) sidebar.getComponent(1)).getComponent(0)).getComponent(0);
        JPanel menuPanel = (JPanel) menuViewport;
        for (Component c : menuPanel.getComponents()) {
            if (c instanceof JPanel) {
                JPanel p = (JPanel) c;
                for (Component sub : p.getComponents()) {
                    if (sub instanceof JLabel && ((JLabel) sub).getText().equals("Thống kê")) {
                        p.setOpaque(true);
                        p.setBackground(SIDEBAR_SELECTED);
                        p.repaint();
                        return;
                    }
                }
            }
        }
    }

}
