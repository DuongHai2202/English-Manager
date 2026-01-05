/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class KhoaHocPanel extends JPanel {
    public JTable table;
    public DefaultTableModel model;

    public JTextField txtSearch;
    public JComboBox<String> cbFilter;

    public JButton btnAdd, btnEdit, btnDelete, btnDetail, btnExport, btnSearch, btnRefresh;
    public KhoaHocPanel(){
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


        toolbar.add(funcPanel, BorderLayout.WEST);

        add(toolbar, BorderLayout.NORTH);

        String[] columns = {
                "ID", "Mã khoá học", "Tên khoá học", "Mô tả",
                "Trình độ", "Thời lượng","Học phí","Trạng thái"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(scrollPane, BorderLayout.CENTER);
    }
}
