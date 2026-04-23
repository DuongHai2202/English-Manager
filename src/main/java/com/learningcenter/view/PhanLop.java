/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.view;

import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */

public class PhanLop extends JPanel {

    /* ===== COMPONENTS ===== */
    public JComboBox<Object> cbKhoaHoc;

    public JTable tblLopChuaCoGV;
    public JTable tblGiangVien;

    public DefaultTableModel lopModel;
    public DefaultTableModel gvModel;

    public JButton btnPhanCong;
    public JButton btnLamMoi;

    public PhanLop() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initComponent();
    }

    private void initComponent() {

        /* ===== TOP: TOOLBAR ===== */
        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBorder(new EmptyBorder(10, 10, 10, 10));
        toolbar.setBackground(Color.WHITE);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createTitledBorder("Chọn khóa học"));

        cbKhoaHoc = new JComboBox<>();
        cbKhoaHoc.setPreferredSize(new Dimension(250, 25));

        filterPanel.add(new JLabel("Khóa học:"));
        filterPanel.add(cbKhoaHoc);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setBorder(BorderFactory.createTitledBorder("Thao tác"));

        btnPhanCong = new JButton("Phân công");
        btnLamMoi = new JButton("Làm mới");

        btnPhanCong.setEnabled(false);

        actionPanel.add(btnPhanCong);
        actionPanel.add(btnLamMoi);

        toolbar.add(filterPanel, BorderLayout.WEST);
        toolbar.add(actionPanel, BorderLayout.EAST);

        add(toolbar, BorderLayout.NORTH);

        /* ===== CENTER: 2 TABLES ===== */
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        centerPanel.setBackground(Color.WHITE);

        /* ===== TABLE LỚP CHƯA CÓ GIẢNG VIÊN ===== */
        String[] lopColumns = {
                "ID", "Mã lớp", "Tên lớp",
                "Ngày bắt đầu", "Ngày kết thúc",
                "Sĩ số"
        };

        lopModel = new DefaultTableModel(lopColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLopChuaCoGV = new JTable(lopModel);
        tblLopChuaCoGV.setRowHeight(28);

        JScrollPane lopScroll = new JScrollPane(tblLopChuaCoGV);
        lopScroll.setBorder(BorderFactory.createTitledBorder("Lớp chưa có giảng viên"));

        /* ===== TABLE GIẢNG VIÊN ===== */
        String[] gvColumns = {
                "ID", "Mã GV", "Họ và tên",
                "Giới tính", "SĐT", "Email"
        };

        gvModel = new DefaultTableModel(gvColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblGiangVien = new JTable(gvModel);
        tblGiangVien.setRowHeight(28);

        JScrollPane gvScroll = new JScrollPane(tblGiangVien);
        gvScroll.setBorder(BorderFactory.createTitledBorder("Giảng viên"));

        centerPanel.add(lopScroll);
        centerPanel.add(gvScroll);

        add(centerPanel, BorderLayout.CENTER);
    }
}

