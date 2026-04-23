package com.learningcenter.view.panel;

import com.learningcenter.controller.PhongHocController;
import com.learningcenter.model.PhongHoc;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PhongHocPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnDelete, btnEdit, btnDetail, btnRefresh, btnSearch;
    private JTextField txtSearch;
    private JComboBox<String> cbFilter;
    private PhongHocController controller;

    public PhongHocPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponent();

        // Khởi tạo controller
        this.controller = new PhongHocController(this);
        this.controller.loadDataToTable();
    }

    private void initComponent() {
        // --- Toolbar ---
        JPanel toolbarWrapper = new JPanel(new BorderLayout());
        toolbarWrapper.setOpaque(false);
        toolbarWrapper.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Bên trái: Các nút chức năng
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actionPanel.setOpaque(false);
        btnAdd = new JButton("Thêm mới");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnDetail = new JButton("Chi tiết");
        btnRefresh = new JButton("Làm mới");

        actionPanel.add(btnAdd);
        actionPanel.add(btnEdit);
        actionPanel.add(btnDelete);
        actionPanel.add(btnDetail);
        actionPanel.add(btnRefresh);

        // Bên phải: Tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setOpaque(false);
        txtSearch = new JTextField(15);
        cbFilter = new JComboBox<>(new String[]{"Tất cả", "Theo tên", "Theo trạng thái"});
        btnSearch = new JButton("Tìm kiếm");

        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(txtSearch);
        searchPanel.add(cbFilter);
        searchPanel.add(btnSearch);

        toolbarWrapper.add(actionPanel, BorderLayout.WEST);
        toolbarWrapper.add(searchPanel, BorderLayout.EAST);

        add(toolbarWrapper, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = { "ID", "Tên Phòng", "Sức Chứa", "Tầng", "Trạng Thái" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(0, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTable(List<PhongHoc> list) {
        tableModel.setRowCount(0);
        for (PhongHoc ph : list) {
            tableModel.addRow(new Object[] {
                ph.getId(),
                ph.getTenPhong(),
                ph.getSucChua(),
                ph.getTang(),
                ph.getTrangThai()
            });
        }
    }

    public PhongHoc getSelectedPhongHoc() {
        int row = table.getSelectedRow();
        if (row == -1) return null;

        PhongHoc ph = new PhongHoc();
        ph.setId((int) table.getValueAt(row, 0));
        ph.setTenPhong(table.getValueAt(row, 1).toString());
        ph.setSucChua((int) table.getValueAt(row, 2));
        ph.setTang((int) table.getValueAt(row, 3));
        ph.setTrangThai(table.getValueAt(row, 4).toString());
        return ph;
    }

    // Getters cho Controller
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDetail() { return btnDetail; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JButton getBtnSearch() { return btnSearch; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JComboBox<String> getCbFilter() { return cbFilter; }
}