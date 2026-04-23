/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.view.panel;

import java.awt.BorderLayout;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class GiangVienPanel extends JPanel {

    /* ===== TABLE GIẢNG VIÊN ===== */
    public JTable table;
    public DefaultTableModel model;

    /* ===== TABLE LỚP DẠY ===== */
    public JTable tblLopDay;
    public DefaultTableModel lopDayModel;

    public JTextField txtSearch;
    public JComboBox<String> cbFilter;

    public JButton btnAdd, btnEdit, btnDelete, btnDetail, btnExport, btnSearch, btnRefresh;

    public GiangVienPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponent();
    }

    private void initComponent() {

        /* ================= TOOLBAR ================= */
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

        cbFilter = new JComboBox<>(new String[]{"Tất cả", "Theo tên", "Theo mã"});
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

        /* ================= CENTER ================= */
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        /* ===== TABLE GIẢNG VIÊN ===== */
        String[] gvColumns = {
            "ID", "Mã GV", "Họ tên", "Ngày sinh",
            "Giới tính", "SĐT", "Email", "Chuyên môn",
            "Trình độ học vấn", "Ngày vào làm", "Mức lương giờ", "Trạng thái"
        };

        model = new DefaultTableModel(gvColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);

        JScrollPane gvScroll = new JScrollPane(table);
        gvScroll.setBorder(new EmptyBorder(10, 10, 5, 10));

        /* ===== TABLE LỚP DẠY ===== */
        String[] lopColumns = {
            "ID Lớp", "Mã lớp", "Tên lớp",
            "Ngày bắt đầu", "Ngày kết thúc",
            "Sĩ số", "Trạng thái"
        };

        lopDayModel = new DefaultTableModel(lopColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLopDay = new JTable(lopDayModel);
        tblLopDay.setRowHeight(26);

        JScrollPane lopScroll = new JScrollPane(tblLopDay);
        lopScroll.setBorder(
            BorderFactory.createTitledBorder("Các lớp giảng viên đang dạy")
        );

        centerPanel.add(gvScroll, BorderLayout.CENTER);
        centerPanel.add(lopScroll, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }
}
