package com.learningcenter.view.panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.learningcenter.controller.LichHocController;
import com.learningcenter.model.LichHoc;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ScheduleManagementPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JComboBox<String> cbFilter;

    private JButton btnAdd, btnDelete, btnEdit, btnDetail, btnExport, btnSearch, btnRefresh;

    private LichHocController controller;

    public ScheduleManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Giữ nền xám nhạt để nổi bật khung trắng
        initComponent();

        this.controller = new LichHocController(this);
        this.controller.loadDataToTable();
    }

    private void initComponent() {
        // --- Toolbar Wrapper ---
        JPanel toolbarWrapper = new JPanel(new GridBagLayout());
        toolbarWrapper.setOpaque(false);
        toolbarWrapper.setBorder(new EmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // LEFT: Functions - NÚT BẤM MÀU MẶC ĐỊNH
        JPanel funcPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        funcPanel.setBorder(BorderFactory.createTitledBorder("Quản lý Lịch học"));
        funcPanel.setBackground(Color.WHITE);

        btnAdd = createToolbarButton("➕", "Thêm");
        btnEdit = createToolbarButton("📝", "Sửa");
        btnDelete = createToolbarButton("🗑️", "Xóa");
        btnDetail = createToolbarButton("👁️", "Chi tiết");
        btnExport = createToolbarButton("📄", "Xuất CSV");

        funcPanel.add(btnAdd);
        funcPanel.add(btnEdit);
        funcPanel.add(btnDelete);
        funcPanel.add(btnDetail);
        funcPanel.add(btnExport);

        // RIGHT: Search - Giao diện 2 hàng, bo góc
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Bộ lọc & Tìm kiếm"));
        searchPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbcSearch = new GridBagConstraints();
        gbcSearch.insets = new Insets(5, 5, 5, 5);
        gbcSearch.fill = GridBagConstraints.HORIZONTAL;

        // Hàng 1: Filter và Search box
        cbFilter = new JComboBox<>(new String[] { "Tất cả", "Theo lớp", "Theo thứ" });
        cbFilter.setPreferredSize(new Dimension(110, 38));
        cbFilter.putClientProperty(FlatClientProperties.STYLE, "arc:20");

        txtSearch = new JTextField(18);
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã lớp hoặc thứ...");
        txtSearch.setPreferredSize(new Dimension(180, 38));
        txtSearch.putClientProperty(FlatClientProperties.STYLE, "arc:20");

        gbcSearch.gridx = 0; gbcSearch.gridy = 0;
        searchPanel.add(cbFilter, gbcSearch);
        gbcSearch.gridx = 1;
        searchPanel.add(txtSearch, gbcSearch);

        // Hàng 2: Nút Tìm và Làm mới
        btnSearch = new JButton("Tìm");
        btnSearch.setPreferredSize(new Dimension(110, 38));
        btnSearch.setBackground(new Color(110, 175, 74)); // Chỉ giữ màu xanh cho nút Tìm
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSearch.putClientProperty(FlatClientProperties.STYLE, "arc:15");

        btnRefresh = new JButton("Làm mới");
        btnRefresh.setPreferredSize(new Dimension(110, 38));
        btnRefresh.putClientProperty(FlatClientProperties.STYLE, "arc:15");

        gbcSearch.gridy = 1; 
        gbcSearch.gridx = 0;
        searchPanel.add(btnSearch, gbcSearch);
        gbcSearch.gridx = 1;
        searchPanel.add(btnRefresh, gbcSearch);

        // Ghép các Panel vào ToolbarWrapper
        gbc.gridx = 0;
        gbc.weightx = 0.6;
        toolbarWrapper.add(funcPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.4;
        gbc.insets = new Insets(0, 10, 0, 0);
        toolbarWrapper.add(searchPanel, gbc);

        add(toolbarWrapper, BorderLayout.NORTH);

        // --- Table Section ---
        String[] columns = { "ID", "Mã lớp", "Tên lớp", "Thứ", "Giờ bắt đầu", "Giờ kết thúc", "Phòng học" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 15, 15, 15));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable(List<LichHoc> list) {
        tableModel.setRowCount(0);
        for (LichHoc lh : list) {
            tableModel.addRow(new Object[] {
                lh.getIdLichHoc(),
                lh.getMaLopHoc(),
                lh.getTenLopHoc(),
                lh.getThuTrongTuan(),
                lh.getGioBatDau(),
                lh.getGioKetThuc(),
                lh.getPhongHoc()
            });
        }
    }

    public LichHoc getSelectedSchedule() {
        int row = table.getSelectedRow();
        if (row == -1) return null;

        int modelRow = table.convertRowIndexToModel(row);
        LichHoc lh = new LichHoc();
        Object idObj = tableModel.getValueAt(modelRow, 0);

        if (idObj != null) {
            lh.setIdLichHoc(Integer.parseInt(idObj.toString()));
        } else {
            return null;
        }

        lh.setMaLopHoc(tableModel.getValueAt(modelRow, 1) != null ? tableModel.getValueAt(modelRow, 1).toString() : "");
        lh.setTenLopHoc(tableModel.getValueAt(modelRow, 2) != null ? tableModel.getValueAt(modelRow, 2).toString() : "");
        lh.setThuTrongTuan(tableModel.getValueAt(modelRow, 3) != null ? tableModel.getValueAt(modelRow, 3).toString() : "");

        Object gioBD = tableModel.getValueAt(modelRow, 4);
        Object gioKT = tableModel.getValueAt(modelRow, 5);

        if (gioBD instanceof java.sql.Time) lh.setGioBatDau((java.sql.Time) gioBD);
        if (gioKT instanceof java.sql.Time) lh.setGioKetThuc((java.sql.Time) gioKT);

        Object phongObj = tableModel.getValueAt(modelRow, 6);
        lh.setPhongHoc(phongObj != null ? phongObj.toString() : "");

        return lh;
    }

    private JButton createToolbarButton(String icon, String text) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setPreferredSize(new Dimension(90, 55));
        btn.putClientProperty(FlatClientProperties.STYLE, "arc:10"); // Giữ bo góc nhẹ

        JLabel lblIcon = new JLabel(icon, SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));

        JLabel lblText = new JLabel(text, SwingConstants.CENTER);
        lblText.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        btn.add(lblIcon, BorderLayout.CENTER);
        btn.add(lblText, BorderLayout.SOUTH);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    // Getters cho Controller
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDetail() { return btnDetail; }
    public JButton getBtnExport() { return btnExport; }
    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JComboBox<String> getCbFilter() { return cbFilter; }
}