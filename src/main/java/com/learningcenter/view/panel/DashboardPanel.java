package com.learningcenter.view.panel;

import com.learningcenter.controller.DashboardController;
import com.learningcenter.view.component.BarChartCustom;
import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DashboardPanel extends JPanel {
    private JPanel cardContainer;
    private JPanel chartContainer;

    private JLabel lblTotalStudents;
    private JLabel lblTotalTransactions;
    private JLabel lblTotalRevenue;

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        initComponents();
        new DashboardController(this);
    }

    private JTable tableRecent;
    private DefaultTableModel tableModelRecent;

    private void initComponents() {
        // --- Header ---
        JPanel pTitle = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        pTitle.setOpaque(false);
        JLabel lblTitle = new JLabel("Bảng Điều Khiển Hệ Thống");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        pTitle.add(lblTitle);
        add(pTitle, BorderLayout.NORTH);

        // --- Main Content (Center) ---
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(0, 20, 20, 20));

        // 1. Cards Area
        cardContainer = new JPanel(new GridLayout(1, 3, 20, 0));
        cardContainer.setOpaque(false);

        lblTotalStudents = createStatCard(cardContainer, "Tổng học viên", "0", new Color(74, 175, 110));
        lblTotalRevenue = createStatCard(cardContainer, "Doanh thu năm", "0 VNĐ", new Color(0, 188, 212));
        lblTotalTransactions = createStatCard(cardContainer, "Tổng giao dịch", "0", new Color(255, 152, 0));

        centerPanel.add(cardContainer, BorderLayout.NORTH);

        // 2. Charts Area
        chartContainer = new JPanel(new GridLayout(1, 2, 20, 0));
        chartContainer.setOpaque(false);

        centerPanel.add(chartContainer, BorderLayout.CENTER);

        // 3. Recent Transactions Area
        JPanel recentPanel = new JPanel(new BorderLayout());
        recentPanel.setBackground(Color.WHITE);
        recentPanel.setBorder(BorderFactory.createTitledBorder("Các giao dịch gần nhất"));

        String[] columns = { "Mã GD", "Học viên", "Loại", "Số tiền", "Ngày", "Trạng thái" };
        tableModelRecent = new DefaultTableModel(columns, 0);
        tableRecent = new JTable(tableModelRecent);
        tableRecent.setRowHeight(30);

        JScrollPane scrollRecent = new JScrollPane(tableRecent);
        scrollRecent.setPreferredSize(new Dimension(0, 200));
        recentPanel.add(scrollRecent, BorderLayout.CENTER);

        centerPanel.add(recentPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    public DefaultTableModel getTableModelRecent() {
        return tableModelRecent;
    }

    private JLabel createStatCard(JPanel container, String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout(0, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(20, 20, 20, 20)));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitle.setForeground(Color.GRAY);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblValue.setForeground(color);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);

        container.add(card);
        return lblValue;
    }

    public void updateStats(int totalStudents, int totalTransactions, double totalRevenue) {
        lblTotalStudents.setText(String.valueOf(totalStudents));
        lblTotalTransactions.setText(String.valueOf(totalTransactions));
        lblTotalRevenue.setText(String.format("%,.0f VNĐ", totalRevenue));
    }

    public void setCharts(Map<String, Double> revenueData, Map<String, Double> studentStatusData) {
        chartContainer.removeAll();

        BarChartCustom revenueChart = new BarChartCustom("Doanh thu theo tháng", revenueData);
        BarChartCustom statusChart = new BarChartCustom("Thống kê cơ sở vật chất", studentStatusData);

        chartContainer.add(revenueChart);
        chartContainer.add(statusChart);

        chartContainer.revalidate();
        chartContainer.repaint();
    }
}
