package com.learningcenter.view.panel;

import com.learningcenter.controller.NgayNghiController;
import com.learningcenter.model.NgayNghi;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class NgayNghiPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnDelete, btnEdit, btnDetail, btnRefresh;
    private NgayNghiController controller;

    public NgayNghiPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponent();

        // Khởi tạo controller
        this.controller = new NgayNghiController(this);
        this.controller.loadDataToTable();
    }

    private void initComponent() {
        // --- Toolbar ---
        JPanel toolbarWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        toolbarWrapper.setOpaque(false);
        toolbarWrapper.setBorder(new EmptyBorder(10, 10, 10, 10));

        btnAdd = new JButton("Thêm mới");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnDetail = new JButton("Chi tiết");
        btnRefresh = new JButton("Làm mới");

        // Thiết kế kích thước đồng nhất cho các nút
        Dimension btnSize = new Dimension(100, 35);
        for (JButton b : new JButton[]{btnAdd, btnEdit, btnDelete, btnDetail, btnRefresh}) {
            b.setPreferredSize(btnSize);
            toolbarWrapper.add(b);
        }

        add(toolbarWrapper, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = { "ID", "Tên Ngày Nghỉ", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Ghi Chú" };
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

    public void updateTable(List<NgayNghi> list) {
        tableModel.setRowCount(0);
        for (NgayNghi nn : list) {
            tableModel.addRow(new Object[] {
                nn.getID_NgayNghi(),
                nn.getTenNgayNghi(),
                nn.getNgayBatDau(),
                nn.getNgayKetThuc(),
                nn.getGhiChu()
            });
        }
    }

    public NgayNghi getSelectedHoliday() {
        int row = table.getSelectedRow();
        if (row == -1) return null;

        NgayNghi nn = new NgayNghi();
        nn.setID_NgayNghi((int) table.getValueAt(row, 0));
        nn.setTenNgayNghi(table.getValueAt(row, 1).toString());
        nn.setNgayBatDau((java.util.Date) table.getValueAt(row, 2));
        nn.setNgayKetThuc((java.util.Date) table.getValueAt(row, 3));
        nn.setGhiChu(table.getValueAt(row, 4) != null ? table.getValueAt(row, 4).toString() : "");
        return nn;
    }

    // Getters cho Controller
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDetail() { return btnDetail; }
    public JButton getBtnRefresh() { return btnRefresh; }
}