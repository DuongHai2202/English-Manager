package com.learningcenter.controller;

import com.learningcenter.dao.KhoaHocDAO;
import com.learningcenter.dao.LopHocDAO;
import com.learningcenter.dao.GiangVienDAO;
import com.learningcenter.model.KhoaHoc;
import com.learningcenter.view.PhanLop;


import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class PhanLopController {

    public PhanLop v;

    LopHocDAO lopHocDAO = new LopHocDAO();
    GiangVienDAO giangVienDAO = new GiangVienDAO();

    int rowLop = -1;
    int rowGV  = -1;

    int idLopHoc = -1;
    int idGiangVien = -1;

    public PhanLopController(PhanLop v) {
        this.v = v;

        v.tblLopChuaCoGV.addMouseListener(new LopListener());
        v.tblGiangVien.addMouseListener(new GiangVienListener());

        v.btnPhanCong.addActionListener(new PhanCongListener());
        v.btnLamMoi.addActionListener(new RefreshListener());
        v.cbKhoaHoc.addActionListener(new KhoaHocChangeListener());

        start();
    }

    /* ================= LISTENERS ================= */

    class LopListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            rowLop = v.tblLopChuaCoGV.getSelectedRow();
            if (rowLop >= 0) {
                idLopHoc = Integer.parseInt(
                    v.lopModel.getValueAt(rowLop, 0).toString()
                );
                checkEnable();
            }
        }
    }

    class GiangVienListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            rowGV = v.tblGiangVien.getSelectedRow();
            if (rowGV >= 0) {
                idGiangVien = Integer.parseInt(
                    v.gvModel.getValueAt(rowGV, 0).toString()
                );
                checkEnable();
            }
        }
    }

    class PhanCongListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            phanCong();
        }
    }

    class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            start();
        }
    }

    class KhoaHocChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (v.cbKhoaHoc.getSelectedItem() != null) {
                KhoaHoc kh = (KhoaHoc) v.cbKhoaHoc.getSelectedItem();
                loadLopChuaCoGV(kh.getID_KhoaHoc());
            }
        }
    }

    /* ================= CORE ================= */

    public void start() {
        reset();
        loadKhoaHoc();
        loadGiangVien();
    }

    private void reset() {
        rowLop = rowGV = -1;
        idLopHoc = idGiangVien = -1;

        v.lopModel.setRowCount(0);
        v.gvModel.setRowCount(0);
        v.btnPhanCong.setEnabled(false);
    }

    private void loadKhoaHoc() {
        try {
            KhoaHocDAO dao = new KhoaHocDAO();
            ResultSet rs = dao.getAllForComboBox();

            v.cbKhoaHoc.removeAllItems();
            while (rs.next()) {
                v.cbKhoaHoc.addItem(
                    new KhoaHoc(
                        rs.getInt("ID_KhoaHoc"),
                        null,
                        rs.getString("TenKhoaHoc"),
                        null, null, 0, 0, null
                    )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLopChuaCoGV(int idKhoaHoc) {
        try {
            v.lopModel.setRowCount(0);
            ResultSet rs = lopHocDAO.getLopChuaCoGiangVienByKhoaHoc(idKhoaHoc);

            while (rs.next()) {
                v.lopModel.addRow(new Object[]{
                    rs.getInt("ID_LopHoc"),
                    rs.getString("MaLopHoc"),
                    rs.getString("TenLopHoc"),
                    rs.getDate("NgayBatDau"),
                    rs.getDate("NgayKetThuc"),
                    rs.getInt("SiSoToiDa")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGiangVien() {
        try {
            v.gvModel.setRowCount(0);
            ResultSet rs = giangVienDAO.getAllDangHoatDong();

            while (rs.next()) {
                v.gvModel.addRow(new Object[]{
                    rs.getInt("ID_GiangVien"),
                    rs.getString("MaGiangVien"),
                    rs.getString("HoTen"),
                    rs.getString("GioiTinh"),
                    rs.getString("SoDienThoai"),
                    rs.getString("Email")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void phanCong() {
        if (idLopHoc == -1 || idGiangVien == -1) {
            JOptionPane.showMessageDialog(v, "Vui lòng chọn lớp và giảng viên!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            v,
            "Xác nhận phân công giảng viên cho lớp?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            lopHocDAO.phanCongGiangVien(idLopHoc, idGiangVien);
            JOptionPane.showMessageDialog(v, "Phân công thành công!");

            KhoaHoc kh = (KhoaHoc) v.cbKhoaHoc.getSelectedItem();
            if (kh != null) {
                loadLopChuaCoGV(kh.getID_KhoaHoc());
            }
            reset();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi phân công!");
        }
    }

    private void checkEnable() {
        v.btnPhanCong.setEnabled(idLopHoc != -1 && idGiangVien != -1);
    }
}
