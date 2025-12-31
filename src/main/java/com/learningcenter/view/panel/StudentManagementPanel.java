package com.learningcenter.view.panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.learningcenter.controller.HocVienController;
import com.learningcenter.model.HocVien;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class StudentManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JComboBox<String> cbFilter;
    private JButton btnAdd, btnDelete, btnEdit, btnDetail, btnExport, btnSearch, btnRefresh;
    private HocVienController controller;

    public StudentManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponent();

        // Initialize controller and let it handle data loading
        this.controller = new HocVienController(this);
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
        btnDelete = createToolbarButton("🗑️", "Xóa");
        btnEdit = createToolbarButton("✏️", "Sửa");
        btnDetail = createToolbarButton("👁️", "Chi tiết");
        btnExport = createToolbarButton("📑", "Xuất Excel");

        funcPanel.add(btnAdd);
        funcPanel.add(btnEdit);
        funcPanel.add(btnDelete);
        funcPanel.add(btnDetail);
        funcPanel.add(btnExport);

        // RIGHT: Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Bộ lọc & Tìm kiếm"));
        searchPanel.setBackground(Color.WHITE);

        cbFilter = new JComboBox<>(new String[] { "Tất cả", "Theo tên", "Theo mã" });
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
        gbc.weightx = 0.6;
        toolbarWrapper.add(funcPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.4;
        gbc.insets = new Insets(0, 10, 0, 0);
        toolbarWrapper.add(searchPanel, gbc);

        add(toolbarWrapper, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = { "ID", "Mã HV", "Họ Tên", "Ngày Sinh", "Giới Tính", "SĐT", "Trình Độ", "Trạng Thái" };
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

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(180);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(5, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable(List<HocVien> list) {
        tableModel.setRowCount(0);
        for (HocVien hv : list) {
            tableModel.addRow(new Object[] {
                    hv.getIdHocVien(),
                    hv.getMaHocVien(),
                    hv.getHoTen(),
                    hv.getNgaySinh(),
                    hv.getGioiTinh(),
                    hv.getSoDienThoai(),
                    hv.getTrinhDo(),
                    hv.getTrangThai()
            });
        }
    }

    public HocVien getSelectedStudent() {
        int row = table.getSelectedRow();
        if (row == -1)
            return null;

        HocVien hv = new HocVien();
        hv.setIdHocVien((int) table.getValueAt(row, 0));
        hv.setMaHocVien(table.getValueAt(row, 1).toString());
        hv.setHoTen(table.getValueAt(row, 2).toString());
        // Simple mapping, controller can re-fetch full object from list if needed
        return hv;
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

    // Getters for controller to access View components
    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDetail() {
        return btnDetail;
    }

    public JButton getBtnExport() {
        return btnExport;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JButton getBtnRefresh() {
        return btnRefresh;
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }

    public JComboBox<String> getCbFilter() {
        return cbFilter;
    }
}
