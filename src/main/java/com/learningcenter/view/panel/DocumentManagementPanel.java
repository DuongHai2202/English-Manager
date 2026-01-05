package com.learningcenter.view.panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.learningcenter.controller.TaiLieuController;
import com.learningcenter.model.TaiLieu;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DocumentManagementPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtSearch;
    private JComboBox<String> cbFilter;

    private JButton btnAdd, btnDelete, btnEdit, btnDetail, btnExport, btnSearch, btnRefresh;

    private TaiLieuController controller;

    public DocumentManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponent();

        controller = new TaiLieuController(this);
        controller.loadDataToTable();
    }

    private void initComponent() {
        // --- Toolbar Wrapper ---
        JPanel toolbarWrapper = new JPanel(new GridBagLayout());
        toolbarWrapper.setOpaque(false);
        // Tăng padding bottom lên 15 để tạo khoảng cách an toàn với bảng
        toolbarWrapper.setBorder(new EmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // LEFT: Functions (Giữ nguyên cấu trúc Chức năng hệ thống)
        JPanel funcPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        funcPanel.setBorder(BorderFactory.createTitledBorder("Quản lý Tài liệu"));
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

        // --- PHẦN SỬA ĐỔI: RIGHT: Search (Giao diện 2 hàng, bo góc) ---
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Bộ lọc & Tìm kiếm"));
        searchPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbcSearch = new GridBagConstraints();
        gbcSearch.insets = new Insets(5, 5, 5, 5);
        gbcSearch.fill = GridBagConstraints.HORIZONTAL;

        // Hàng 1: ComboBox và TextField (Bo góc 20)
        cbFilter = new JComboBox<>(new String[]{"Tất cả", "Theo tiêu đề", "Theo khóa học", "Theo tên file"});
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

        // Hàng 2: Nút Tìm và Làm mới (Bo góc 15)
        btnSearch = new JButton("Tìm");
        btnSearch.setPreferredSize(new Dimension(100, 38));
        btnSearch.setBackground(new Color(110, 175, 74)); // Màu xanh lá
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
        String[] columns = {"ID", "ID Khóa học", "Tiêu đề", "Mô tả", "Tên file", "Đường dẫn"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35); // Tăng chiều cao hàng một chút cho dễ nhìn
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        JScrollPane scrollPane = new JScrollPane(table);
        // Tăng khoảng cách phía trên bảng để không bị dính vào toolbar
        scrollPane.setBorder(new EmptyBorder(10, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable(List<TaiLieu> list) {
        tableModel.setRowCount(0);
        for (TaiLieu tl : list) {
            tableModel.addRow(new Object[]{
                    tl.getIdTaiLieu(),
                    tl.getIdKhoaHoc(),
                    tl.getTieuDe(),
                    tl.getMoTa(),
                    tl.getTenFile(),
                    tl.getDuongDanFile()
            });
        }
    }

    public TaiLieu getSelectedDocument() {
        int row = table.getSelectedRow();
        if (row == -1) return null;

        int modelRow = table.convertRowIndexToModel(row);

        TaiLieu tl = new TaiLieu();
        Object idObj = tableModel.getValueAt(modelRow, 0);
        if (idObj == null) return null;

        tl.setIdTaiLieu(Integer.parseInt(idObj.toString()));

        Object idKhoaHocObj = tableModel.getValueAt(modelRow, 1);
        tl.setIdKhoaHoc(idKhoaHocObj != null ? Integer.parseInt(idKhoaHocObj.toString()) : 0);

        Object tieuDeObj = tableModel.getValueAt(modelRow, 2);
        tl.setTieuDe(tieuDeObj != null ? tieuDeObj.toString() : "");

        Object moTaObj = tableModel.getValueAt(modelRow, 3);
        tl.setMoTa(moTaObj != null ? moTaObj.toString() : "");

        Object tenFileObj = tableModel.getValueAt(modelRow, 4);
        tl.setTenFile(tenFileObj != null ? tenFileObj.toString() : "");

        Object duongDanObj = tableModel.getValueAt(modelRow, 5);
        tl.setDuongDanFile(duongDanObj != null ? duongDanObj.toString() : "");

        return tl;
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

    // ===== GETTERS cho Controller =====
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