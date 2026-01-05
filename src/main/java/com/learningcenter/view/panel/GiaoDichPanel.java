package com.learningcenter.view.panel;

import com.formdev.flatlaf.FlatClientProperties;
import com.learningcenter.controller.GiaoDichController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.learningcenter.model.GiaoDich;

public class GiaoDichPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JComboBox<String> cbFilter;
    private JButton btnAdd, btnDelete, btnEdit, btnDetail, btnExport, btnSearch, btnRefresh;
    private GiaoDichController controller;

    public GiaoDichPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponent();

        // Set controller để chạy
        this.controller = new GiaoDichController(this);
        this.controller.loadDataToTable();
    }

    public void initComponent() {
        // Toolbar
        JPanel toolbarWrapper = new JPanel(new GridBagLayout());
        toolbarWrapper.setOpaque(false);
        toolbarWrapper.setBorder(new EmptyBorder(15, 15, 10, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // Chức năng
        // Left: btn
        JPanel funcPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        funcPanel.setBorder(BorderFactory.createTitledBorder("Chức năng Hệ thống"));
        funcPanel.setBackground(Color.WHITE);

        btnAdd = createToolbarButton("➕", "Thêm");
        btnDelete = createToolbarButton("🗑️", "Xóa");
        btnEdit = createToolbarButton("✏️", "Sửa");
        btnDetail = createToolbarButton("👁️", "Chi tiết");
        btnExport = createToolbarButton("📑", "Xuất Excel");

        funcPanel.add(btnAdd);
        funcPanel.add(btnDelete);
        funcPanel.add(btnEdit);
        funcPanel.add(btnDetail);
        funcPanel.add(btnExport);

        // RIGHT: Ô search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Bộ lọc & Tìm kiếm"));
        searchPanel.setBackground(Color.WHITE);

        // Filter bộ lọc
        cbFilter = new JComboBox<>(new String[] { "Tất cả", "Theo số tiền", "Theo tháng", "Theo loại giao dịch" });
        cbFilter.setPreferredSize(new Dimension(100, 36));

        // Ô search
        txtSearch = new JTextField(18);
        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập từ khóa...");
        txtSearch.setPreferredSize(new Dimension(180, 36));

        // Nút tìm
        btnSearch = new JButton("Tìm");
        btnSearch.setPreferredSize(new Dimension(110, 36));
        btnSearch.setBackground(new Color(74, 175, 110));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Làm mới
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

        // Table
        String[] columns = { "ID", "Học Viên", "Loại GD", "Số Tiền", "Ngày GD", "Phương Thức", "Trạng Thái",
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

        // Căn chỉnh độ rộng cột
        table.getColumnModel().getColumn(1).setPreferredWidth(180); // Tên học viên
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);

        // Ẩn cột ID_GiaoDich (Index 0) nhưng vẫn giữ trong Model
        table.removeColumn(table.getColumnModel().getColumn(0));

        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(5, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);
    }

    public int getSelectedGiaoDichId() {
        int row = table.getSelectedRow();
        if (row == -1)
            return -1;
        // Trả về giá trị ID từ Model (cột 0) thay vì Table (vì ID đã bị ẩn khỏi Table)
        return (int) table.getModel().getValueAt(table.convertRowIndexToModel(row), 0);
    }

    public void updateTable(List<GiaoDich> list) {
        tableModel.setRowCount(0);
        for (GiaoDich gd : list) {
            tableModel.addRow(new Object[] {
                    gd.getIdGiaoDich(),
                    gd.getTenHocVien() != null ? gd.getTenHocVien() : "ID: " + gd.getIdHocVien(),
                    gd.getLoaiGiaoDich(),
                    String.format("%,.0f VNĐ", gd.getSoTien()),
                    gd.getNgayGiaoDich(),
                    gd.getPhuongThucThanhToan(),
                    gd.getTrangThai(),
                    gd.getNoiDung()
            });
        }
    }

    // Tạo nút các chức năng thêm sửa
    public JButton createToolbarButton(String icon, String text) {
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
