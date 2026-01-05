package com.learningcenter.view.panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.learningcenter.controller.CoSoVatChatController;
import com.learningcenter.model.CoSoVatChat;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CoSoVatChatPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JComboBox<String> cbFilter;
    private JButton btnAdd, btnDelete, btnEdit, btnDetail, btnRefresh, btnSearch;
    private CoSoVatChatController controller;

    public CoSoVatChatPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponent();

        // Initialize controller and let it handle data loading
        this.controller = new CoSoVatChatController(this);
        this.controller.loadDataToTable();
    }

    private void initComponent() {
        // --- Toolbar ---
        JPanel toolbarWrapper = new JPanel(new GridBagLayout());
        toolbarWrapper.setOpaque(false);
        toolbarWrapper.setBorder(new EmptyBorder(15, 15, 10, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // LEFT: Functions
        JPanel funcPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        funcPanel.setBorder(BorderFactory.createTitledBorder("Chức năng Hệ thống"));
        funcPanel.setBackground(Color.WHITE);

        btnAdd = createToolbarButton("➕", "Thêm");
        btnEdit = createToolbarButton("✏️", "Sữa");
        btnDelete = createToolbarButton("🗑️", "Xóa");
        btnDetail = createToolbarButton("👁️", "Chi tiết");

        funcPanel.add(btnAdd);
        funcPanel.add(btnEdit);
        funcPanel.add(btnDelete);
        funcPanel.add(btnDetail);

        // RIGHT: Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Bộ lọc & Tìm kiếm"));
        searchPanel.setBackground(Color.WHITE);

        cbFilter = new JComboBox<>(new String[] { "Tất cả", "Theo tên", "Theo loại" });
        cbFilter.setPreferredSize(new Dimension(100, 36));

        txtSearch = new JTextField(18);
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập từ khóa...");
        txtSearch.setPreferredSize(new Dimension(180, 36));

        btnSearch = new JButton("Tìm");
        btnSearch.setPreferredSize(new Dimension(110, 36));
        btnSearch.setBackground(new Color(74, 175, 110));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 12));

        btnRefresh = new JButton("🔄 Làm mới");
        btnRefresh.setPreferredSize(new Dimension(110, 36));

        searchPanel.add(cbFilter);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        gbc.gridx = 0;
        gbc.weightx = 0.5;
        toolbarWrapper.add(funcPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(0, 10, 0, 0);
        toolbarWrapper.add(searchPanel, gbc);

        add(toolbarWrapper, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = { "ID", "Tên cơ sở vật chất", "Loại", "Số lượng", "Đơn vị", "Tình trạng", "Ngày mua" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(32);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        // Hide ID Column but keep in model
        table.removeColumn(table.getColumnModel().getColumn(0));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(5, 15, 15, 15));
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JButton createToolbarButton(String icon, String text) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setPreferredSize(new Dimension(80, 55));

        JLabel lblIcon = new JLabel(icon, SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 20));

        JLabel lblText = new JLabel(text, SwingConstants.CENTER);
        lblText.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        btn.add(lblIcon, BorderLayout.CENTER);
        btn.add(lblText, BorderLayout.SOUTH);

        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public void updateTable(List<CoSoVatChat> list) {
        tableModel.setRowCount(0);
        for (CoSoVatChat ts : list) {
            tableModel.addRow(new Object[] {
                    ts.getIdCoSoVatChat(),
                    ts.getTenCoSoVatChat(),
                    ts.getLoaiCoSoVatChat(),
                    ts.getSoLuong(),
                    ts.getDonViTinh(),
                    ts.getTinhTrang(),
                    ts.getNgayMua()
            });
        }
    }

    public int getSelectedId() {
        int row = table.getSelectedRow();
        if (row == -1)
            return -1;
        return (int) table.getModel().getValueAt(table.convertRowIndexToModel(row), 0);
    }

    // Getters for Controller
    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnDetail() {
        return btnDetail;
    }

    public JButton getBtnRefresh() {
        return btnRefresh;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }

    public JComboBox<String> getCbFilter() {
        return cbFilter;
    }
}
