/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.controller;

import com.learningcenter.dao.KhoaHocDAO;
import com.learningcenter.view.dialog.KhoaHocDialog;
import com.learningcenter.view.dialog.KhoaHocDialog;
import com.learningcenter.view.panel.KhoaHocPanel;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class KhoaHocController {
    public KhoaHocPanel v;
    KhoaHocDAO dao = new KhoaHocDAO();
    int k=-1;
    
    class AddKhoaHocListener implements ActionListener{
    @Override
        public void actionPerformed(ActionEvent e) {
            addKH();
            loadTable();
        }
    }
     class XoaKhoaHocListener implements ActionListener{
    @Override
        public void actionPerformed(ActionEvent e) {
            deleteKH();
            loadTable();
        }
    }
     class SuaKhoaHocListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            updateKH();
            loadTable();
        }

     }
    public KhoaHocController(KhoaHocPanel v){
        this.v = v;
        v.btnAdd.addActionListener(new AddKhoaHocListener());
        v.btnDelete.addActionListener(new XoaKhoaHocListener());
        v.btnEdit.addActionListener(new SuaKhoaHocListener());
        v.table.addMouseListener(new TableListener());
        
        loadTable();
    }
    
    class TableListener extends MouseAdapter{
    @Override
        public void mouseClicked(MouseEvent e){
        k = v.table.getSelectedRow();
        }
    }

    public void addKH() {
        Frame parent = (Frame) v.getTopLevelAncestor();
        KhoaHocDialog dialog = new KhoaHocDialog(parent);
        dialog.setLocationRelativeTo(v); 

        dialog.btnSave.addActionListener(e -> {
            if (!validateDialog(dialog)) return;

            try {
                dao.insert(
                    dialog.txtMaKH.getText().trim(),
                    dialog.txtTenKH.getText().trim(),
                    dialog.txtMoTa.getText().trim(),
                    dialog.cbTrinhDo.getSelectedItem().toString(),
                    Integer.parseInt(dialog.txtThoiLuong.getText().trim()),
                    Double.parseDouble(dialog.txtHocPhi.getText().trim()),
                    dialog.cbTrangThai.getSelectedItem().toString()
                );
                dialog.dispose();
                loadTable();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Lỗi thêm khoá học");
            }
        });

        dialog.btnCancel.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }
    
     public void updateKH() {
        Frame parent = (Frame) v.getTopLevelAncestor();
        KhoaHocDialog dialog = new KhoaHocDialog(parent);
        dialog.setLocationRelativeTo(v);


        dialog.txtMaKH.setText(v.model.getValueAt(k, 1).toString());
        dialog.txtMaKH.setEditable(false);
        dialog.txtTenKH.setText(v.model.getValueAt(k, 2).toString());
        dialog.txtMoTa.setText(v.model.getValueAt(k, 3).toString());
        dialog.cbTrinhDo.setSelectedItem(v.model.getValueAt(k, 4).toString());
        dialog.txtThoiLuong.setText(v.model.getValueAt(k, 5).toString());
        dialog.txtHocPhi.setText(v.model.getValueAt(k, 6).toString());
        dialog.cbTrangThai.setSelectedItem(v.model.getValueAt(k, 7).toString());

        dialog.btnSave.addActionListener(e -> {
            if (!validateDialog(dialog)) return;

            try {
                dao.update(
                    dialog.txtMaKH.getText().trim(),
                    dialog.txtTenKH.getText().trim(),
                    dialog.txtMoTa.getText().trim(),
                    dialog.cbTrinhDo.getSelectedItem().toString(),
                    Integer.parseInt(dialog.txtThoiLuong.getText().trim()),
                    Double.parseDouble(dialog.txtHocPhi.getText().trim()),
                    dialog.cbTrangThai.getSelectedItem().toString(),
                    Integer.parseInt(v.model.getValueAt(k, 0).toString())
                );
                dialog.dispose();
                loadTable();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Lỗi cập nhật nhân viên");
            }
        });

        dialog.btnCancel.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public void deleteKH() {
        try {
            int id = Integer.parseInt(v.model.getValueAt(k, 0).toString());
            dao.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateDialog(KhoaHocDialog d) {
        if (d.txtMaKH.getText().trim().isEmpty()
                || d.txtTenKH.getText().trim().isEmpty()
                || d.txtMoTa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(d, "Vui lòng nhập đầy đủ thông tin");
            return false;
        }

        try {
            Integer.parseInt(d.txtThoiLuong.getText().trim());
            Double.parseDouble(d.txtHocPhi.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(d, "Thời lượng hoặc học phí không hợp lệ");
            return false;
        }
        return true;
    }

    public void loadTable() {
        try {
            v.model.setRowCount(0);
            ResultSet rs = dao.getAll();

            while (rs.next()) {
                v.model.addRow(new Object[]{
                    rs.getInt("ID_KhoaHoc"),
                    rs.getString("MaKhoaHoc"),
                    rs.getString("TenKhoaHoc"),
                    rs.getString("MoTa"),
                    rs.getString("TrinhDo"),
                    rs.getInt("ThoiLuong"),
                    rs.getDouble("HocPhi"),
                    rs.getString("TrangThai")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    } 