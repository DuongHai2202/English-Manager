package com.learningcenter.view.panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.learningcenter.controller.HoTroController;
import com.learningcenter.model.HoTro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class SupportManagementPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtSearch;
    private JComboBox<String> cbFilter;

    private JButton btnAdd, btnDelete, btnEdit, btnDetail, btnExport, btnSearch, btnRefresh;

    private HoTroController controller;

    public SupportManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Nền xám nhạt đồng bộ
        initComponent();

        controller = new HoTroController(this);
        controller.loadDataToTable();
    }

    private void initComponent() {
        // --- Toolbar Wrapper ---
        JPanel toolbarWrapper = new JPanel(new GridBagLayout());
        toolbarWrapper.setOpaque(false);
        toolbarWrapper.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // LEFT: Functions (Nút mặc định, không màu)
        JPanel funcPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        funcPanel.setBorder(BorderFactory.createTitledBorder("Quản lý Hỗ trợ"));
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

        // --- RIGHT: Search (Giao diện 2 hàng, bo góc) ---
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Bộ lọc & Tìm kiếm"));
        searchPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbcSearch = new GridBagConstraints();
        gbcSearch.insets = new Insets(5, 5, 5, 5);
        gbcSearch.fill = GridBagConstraints.HORIZONTAL;

        // Hàng 1: ComboBox và TextField
        cbFilter = new JComboBox<>(new String[]{"Tất cả", "Theo tiêu đề", "Theo người gửi", "Theo trạng thái"});
        cbFilter.setPreferredSize(new Dimension(130, 38));
        cbFilter.putClientProperty(FlatClientProperties.STYLE, "arc:20");

        txtSearch = new JTextField(18);
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập từ khóa...");
        txtSearch.setPreferredSize(new Dimension(200, 38));
        txtSearch.putClientProperty(FlatClientProperties.STYLE, "arc:20");

        gbcSearch.gridx = 0; gbcSearch.gridy = 0;
        searchPanel.add(cbFilter, gbcSearch);
        gbcSearch.gridx = 1;
        searchPanel.add(txtSearch, gbcSearch);

        // Hàng 2: Nút Tìm và Làm mới
        btnSearch = new JButton("Tìm");
        btnSearch.setPreferredSize(new Dimension(100, 38));
        btnSearch.setBackground(new Color(110, 175, 74)); // Giữ màu xanh cho nút Tìm chính
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSearch.putClientProperty(FlatClientProperties.STYLE, "arc:15");

        btnRefresh = new JButton("Làm mới");
        btnRefresh.setPreferredSize(new Dimension(100, 38));
        btnRefresh.putClientProperty(FlatClientProperties.STYLE, "arc:15");

        gbcSearch.gridy = 1; 
        gbcSearch.gridx = 0;
        searchPanel.add(btnSearch, gbcSearch);
        gbcSearch.gridx = 1;
        searchPanel.add(btnRefresh, gbcSearch);

        // Đưa vào toolbarWrapper
        gbc.gridx = 0;
        gbc.weightx = 0.6;
        toolbarWrapper.add(funcPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.4;
        gbc.insets = new Insets(0, 10, 0, 0);
        toolbarWrapper.add(searchPanel, gbc);

        add(toolbarWrapper, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = {"ID", "Người gửi", "Email", "Tiêu đề", "Chuyên mục", "Ưu tiên", "Trạng thái", "Ngày tạo"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(235, 245, 235));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 15, 15, 15));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable(List<HoTro> list) {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (HoTro ht : list) {
            String ngay = (ht.getNgayTao() != null) ? sdf.format(ht.getNgayTao()) : "";
            tableModel.addRow(new Object[]{
                    ht.getIdHoTro(),
                    ht.getTenNguoiGui(),
                    ht.getEmailNguoiGui(),
                    ht.getTieuDe(),
                    ht.getChuyenMuc(),
                    ht.getDoUuTien(),
                    ht.getTrangThai(),
                    ngay
            });
        }
    }

    public HoTro getSelectedSupport() {
        int row = table.getSelectedRow();
        if (row == -1) return null;
        int modelRow = table.convertRowIndexToModel(row);
        HoTro ht = new HoTro();
        Object idObj = tableModel.getValueAt(modelRow, 0);
        if (idObj == null) return null;
        ht.setIdHoTro(Integer.parseInt(idObj.toString()));
        ht.setTenNguoiGui(tableModel.getValueAt(modelRow, 1) != null ? tableModel.getValueAt(modelRow, 1).toString() : "");
        ht.setEmailNguoiGui(tableModel.getValueAt(modelRow, 2) != null ? tableModel.getValueAt(modelRow, 2).toString() : "");
        ht.setTieuDe(tableModel.getValueAt(modelRow, 3) != null ? tableModel.getValueAt(modelRow, 3).toString() : "");
        ht.setChuyenMuc(tableModel.getValueAt(modelRow, 4) != null ? tableModel.getValueAt(modelRow, 4).toString() : "");
        ht.setDoUuTien(tableModel.getValueAt(modelRow, 5) != null ? tableModel.getValueAt(modelRow, 5).toString() : "");
        ht.setTrangThai(tableModel.getValueAt(modelRow, 6) != null ? tableModel.getValueAt(modelRow, 6).toString() : "");
        return ht;
    }

    private JButton createToolbarButton(String icon, String text) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setPreferredSize(new Dimension(90, 55));
        btn.putClientProperty(FlatClientProperties.STYLE, "arc:10");

        JLabel lblIcon = new JLabel(icon, SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
        JLabel lblText = new JLabel(text, SwingConstants.CENTER);
        lblText.setFont(new Font("Segoe UI", Font.BOLD, 11));

        btn.add(lblIcon, BorderLayout.CENTER);
        btn.add(lblText, BorderLayout.SOUTH);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ===== GETTERS =====
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