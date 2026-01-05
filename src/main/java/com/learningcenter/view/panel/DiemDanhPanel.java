package com.learningcenter.view.panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.learningcenter.controller.DiemDanhController;
import com.learningcenter.model.DiemDanh;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Panel quản lý điểm danh học viên
 * // THỊNH: Đổi tên từ AttendanceCheckPanel sang DiemDanhPanel
 */
public class DiemDanhPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JComboBox<String> cbFilter;
    private JButton btnAdd, btnDelete, btnEdit, btnDetail, btnExport, btnSearch, btnRefresh;
    private DiemDanhController controller;

    public DiemDanhPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponent();

        // Initialize controller and let it handle data loading
        this.controller = new DiemDanhController(this);
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
        btnEdit = createToolbarButton("📝", "Sửa");
        btnDelete = createToolbarButton("❌", "Xóa");
        btnDetail = createToolbarButton("👁️", "Chi tiết");
        btnExport = createToolbarButton("📥", "Xuất Excel");

        funcPanel.add(btnAdd);
        funcPanel.add(btnEdit);
        funcPanel.add(btnDelete);
        funcPanel.add(btnDetail);
        funcPanel.add(btnExport);

        // RIGHT: Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Bộ lọc & Tìm kiếm"));
        searchPanel.setBackground(Color.WHITE);

        cbFilter = new JComboBox<>(new String[] { "Tất cả", "Theo tên học viên", "Theo lớp học", "Theo trạng thái" });
        cbFilter.setPreferredSize(new Dimension(150, 36));

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
        String[] columns = { "ID", "Mã HV", "Tên Học Viên", "Mã Lớp", "Tên Lớp", "Ngày Điểm Danh", "Trạng Thái",
                "Ghi Chú" };
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
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(120);
        table.getColumnModel().getColumn(7).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(5, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable(List<DiemDanh> list) {
        tableModel.setRowCount(0);
        for (DiemDanh dd : list) {
            tableModel.addRow(new Object[] {
                    dd.getIdDiemDanh(),
                    dd.getMaHocVien(),
                    dd.getTenHocVien(),
                    dd.getMaLopHoc(),
                    dd.getTenLopHoc(),
                    dd.getNgayDiemDanh(),
                    dd.getTrangThai(),
                    dd.getGhiChu()
            });
        }
    }

    public DiemDanh getSelectedDiemDanh() {
        int row = table.getSelectedRow();
        if (row == -1)
            return null;

        return controller.getDiemDanhFromList(row);
    }

    private JButton createToolbarButton(String icon, String text) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setPreferredSize(new Dimension(75, 52));
        btn.setBackground(Color.WHITE);

        JLabel lblIcon = new JLabel(icon, SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));

        JLabel lblText = new JLabel(text, SwingConstants.CENTER);
        lblText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblText.setBorder(new EmptyBorder(0, 0, 2, 0));

        btn.add(lblIcon, BorderLayout.CENTER);
        btn.add(lblText, BorderLayout.SOUTH);

        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Getters for controller
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
