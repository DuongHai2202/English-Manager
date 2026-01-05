package com.learningcenter.view.panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.learningcenter.controller.BaiVietController;
import com.learningcenter.model.BaiViet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PostManagementPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnAdd, btnEdit, btnDelete, btnDetail, btnExport, btnRefresh;
    private JButton btnSearch;
    private JTextField txtSearch;
    private JComboBox<String> cbFilter;

    private BaiVietController controller;

    public PostManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponent();

        this.controller = new BaiVietController(this);
        this.controller.loadDataToTable();
    }

    private void initComponent() {
        // --- TOOLBAR WRAPPER ---
        JPanel toolbarWrapper = new JPanel(new GridBagLayout());
        toolbarWrapper.setOpaque(false);
        toolbarWrapper.setBorder(new EmptyBorder(15, 15, 10, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // LEFT: Functions (Giữ nguyên y hệt code cũ)
        JPanel funcPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        funcPanel.setBorder(BorderFactory.createTitledBorder("Quản lý Bài viết"));
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

        // RIGHT: Search (Đã sửa lại giao diện theo hình mẫu: 2 hàng, bo góc)
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Bộ lọc & Tìm kiếm"));
        searchPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbcSearch = new GridBagConstraints();
        gbcSearch.insets = new Insets(5, 5, 5, 5);
        gbcSearch.fill = GridBagConstraints.HORIZONTAL;

        // Hàng 1: ComboBox và TextField (Bo tròn)
        cbFilter = new JComboBox<>(new String[]{"Tất cả", "Theo tiêu đề", "Theo loại"});
        cbFilter.setPreferredSize(new Dimension(100, 36));
        cbFilter.putClientProperty(FlatClientProperties.STYLE, "arc:20"); // Bo tròn góc

        txtSearch = new JTextField(18);
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập từ khóa...");
        txtSearch.setPreferredSize(new Dimension(180, 36));
        txtSearch.putClientProperty(FlatClientProperties.STYLE, "arc:20"); // Bo tròn góc

        gbcSearch.gridx = 0; gbcSearch.gridy = 0;
        searchPanel.add(cbFilter, gbcSearch);
        gbcSearch.gridx = 1;
        searchPanel.add(txtSearch, gbcSearch);

        // Hàng 2: Các nút bấm (Tìm & Làm mới)
        btnSearch = new JButton("Tìm");
        btnSearch.setPreferredSize(new Dimension(100, 38));
        btnSearch.setBackground(new Color(110, 175, 74)); // Màu xanh lá giống hình
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSearch.putClientProperty(FlatClientProperties.STYLE, "arc:15"); // Bo góc nút

        btnRefresh = new JButton("Làm mới");
        btnRefresh.setPreferredSize(new Dimension(100, 38));
        btnRefresh.putClientProperty(FlatClientProperties.STYLE, "arc:15"); // Bo góc nút

        gbcSearch.gridy = 1; 
        gbcSearch.gridx = 0;
        searchPanel.add(btnSearch, gbcSearch);
        gbcSearch.gridx = 1;
        searchPanel.add(btnRefresh, gbcSearch);

        // Đưa 2 cụm Panel vào Wrapper
        gbc.gridx = 0;
        gbc.weightx = 0.6;
        toolbarWrapper.add(funcPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.4;
        gbc.insets = new Insets(0, 10, 0, 0);
        toolbarWrapper.add(searchPanel, gbc);

        add(toolbarWrapper, BorderLayout.NORTH);

        // --- TABLE SECTION (Giữ nguyên) ---
        String[] columns = {"ID", "Tiêu đề", "Loại", "Trạng thái", "Nội dung"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        JScrollPane scrollPane = new JScrollPane(table);
        // Tăng padding top lên 20 để bảng không bị chạm vào nút khi cửa sổ nhỏ
        scrollPane.setBorder(new EmptyBorder(20, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable(List<BaiViet> list) {
        tableModel.setRowCount(0);
        for (BaiViet bv : list) {
            String noiDung = bv.getNoiDung();
            if (noiDung != null && noiDung.length() > 60) {
                noiDung = noiDung.substring(0, 60) + "...";
            }
            tableModel.addRow(new Object[]{
                    bv.getIdBaiViet(),
                    bv.getTieuDe(),
                    bv.getLoaiBaiViet(),
                    bv.getTrangThai(),
                    noiDung
            });
        }
    }

    public BaiViet getSelectedPost() {
        int row = table.getSelectedRow();
        if (row == -1) return null;
        int modelRow = table.convertRowIndexToModel(row);
        BaiViet bv = new BaiViet();
        Object idObj = tableModel.getValueAt(modelRow, 0);
        if (idObj != null) {
            bv.setIdBaiViet(Integer.parseInt(idObj.toString()));
        } else {
            return null;
        }
        bv.setTieuDe(tableModel.getValueAt(modelRow, 1).toString());
        return bv;
    }

    private JButton createToolbarButton(String icon, String text) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setPreferredSize(new Dimension(90, 55));
        JLabel lblIcon = new JLabel(icon, SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));
        JLabel lblText = new JLabel(text, SwingConstants.CENTER);
        lblText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btn.add(lblIcon, BorderLayout.CENTER);
        btn.add(lblText, BorderLayout.SOUTH);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // GETTERS cho Controller
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnDetail() { return btnDetail; }
    public JButton getBtnExport() { return btnExport; }
    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JComboBox<String> getCbFilter() { return cbFilter; }
}