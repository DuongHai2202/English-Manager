/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.view.panel;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Admin
 */
public class LopHocPanel extends JPanel {
        public JTable table;
    public DefaultTableModel tableModel;

    public JTextField txtSearch;
    public JComboBox<String> cbFilter;

    public JButton btnAdd, btnEdit, btnDelete, btnDetail, btnExport, btnSearch, btnRefresh;

    public LopHocPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponent();
    }

    private void initComponent() {

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBorder(new EmptyBorder(10, 10, 10, 10));
        toolbar.setBackground(Color.WHITE);

        JPanel funcPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        funcPanel.setBackground(Color.WHITE);
        funcPanel.setBorder(BorderFactory.createTitledBorder("Chức năng"));

        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnDetail = new JButton("Chi tiết");
        btnExport = new JButton("Xuất Excel");

        funcPanel.add(btnAdd);
        funcPanel.add(btnEdit);
        funcPanel.add(btnDelete);
        funcPanel.add(btnDetail);
        funcPanel.add(btnExport);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));

        cbFilter = new JComboBox<>(new String[]{"Tất cả", "Theo tên", "Theo mã", "Theo tình trạng"});
        txtSearch = new JTextField(15);

        btnSearch = new JButton("Tìm");
        btnRefresh = new JButton("Làm mới");

        searchPanel.add(cbFilter);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        toolbar.add(funcPanel, BorderLayout.WEST);
        toolbar.add(searchPanel, BorderLayout.EAST);

        add(toolbar, BorderLayout.NORTH);

        String[] columns = {
                "ID", "Mã lớp học", "Tên lớp học", "Tên khoá học",
                "Sĩ số tối đa", "Ngày bắt đầu","Ngày kết thúc","Phòng học","Trạng Thái"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(scrollPane, BorderLayout.CENTER);
    }
}
