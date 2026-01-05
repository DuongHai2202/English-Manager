package com.learningcenter.view.panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.learningcenter.controller.ChungChiController;
import com.learningcenter.model.ChungChi;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ChungChiPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JComboBox<String> cbFilter;
    private JButton btnAdd, btnDelete, btnEdit, btnExport, btnSearch, btnRefresh;
    private ChungChiController controller;

    public ChungChiPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponent();

        // Khởi tạo controller và tải dữ liệu
        this.controller = new ChungChiController(this);
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
        funcPanel.setBorder(BorderFactory.createTitledBorder("Quản lý Chứng chỉ"));
        funcPanel.setBackground(Color.WHITE);

        btnAdd = createToolbarButton("Thêm");
        btnEdit = createToolbarButton("Sửa");
        btnDelete = createToolbarButton("Xóa");
        btnExport = createToolbarButton("Xuất Excel");

        funcPanel.add(btnAdd);
        funcPanel.add(btnEdit);
        funcPanel.add(btnDelete);
        funcPanel.add(btnExport);

        // RIGHT: Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Bộ lọc & Tìm kiếm"));
        searchPanel.setBackground(Color.WHITE);

        cbFilter = new JComboBox<>(new String[] { "Tất cả", "Theo loại", "Theo tên" });
        cbFilter.setPreferredSize(new Dimension(110, 36));

        txtSearch = new JTextField(18);
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập từ khóa...");
        txtSearch.setPreferredSize(new Dimension(180, 36));

        btnSearch = new JButton("Tìm");
        btnSearch.setPreferredSize(new Dimension(80, 36));
        btnSearch.setBackground(new Color(74, 175, 110));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 12));

        btnRefresh = new JButton("Làm mới");
        btnRefresh.setPreferredSize(new Dimension(100, 36));

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
        String[] columns = { "ID", "Loại Chứng Chỉ", "Tên Chứng Chỉ", "Mô Tả" };
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

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(5, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable(List<ChungChi> list) {
        tableModel.setRowCount(0);
        for (ChungChi cc : list) {
            tableModel.addRow(new Object[] {
                cc.getID_ChungChi(),
                cc.getLoaiChungChi(),
                cc.getTenChungChi(),
                cc.getMoTaNgan(),
                cc.getNgayTao()
            });
        }
    }

    public ChungChi getSelectedChungChi() {
        int row = table.getSelectedRow();
        if (row == -1) return null;

        ChungChi cc = new ChungChi();
        cc.setID_ChungChi((int) table.getValueAt(row, 0));
        cc.setLoaiChungChi(table.getValueAt(row, 1).toString());
        cc.setTenChungChi(table.getValueAt(row, 2).toString());
        cc.setMoTaNgan(table.getValueAt(row, 3).toString());
        return cc;
    }

    private JButton createToolbarButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(100, 45));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Getters
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnExport() { return btnExport; }
    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JComboBox<String> getCbFilter() { return cbFilter; }
}