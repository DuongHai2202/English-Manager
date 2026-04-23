package com.learningcenter.controller;

import com.learningcenter.dao.NhanVienDAO;
import com.learningcenter.view.dialog.NhanVienDialog;
import com.learningcenter.view.panel.StaffManagementPanel;
import com.opencsv.CSVWriter;

import java.awt.Frame;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class NhanVienController {

    public StaffManagementPanel v;
    NhanVienDAO dao = new NhanVienDAO();

    int k = -1; // dòng đang chọn
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // ===== CONSTRUCTOR =====
    public NhanVienController(StaffManagementPanel v) {
        this.v = v;

        v.btnAdd.addActionListener(new AddListener());
        v.btnEdit.addActionListener(new EditListener());
        v.btnDelete.addActionListener(new DeleteListener());
        v.btnSearch.addActionListener(new SearchListener());
        v.btnRefresh.addActionListener(new RefreshListener());
        v.btnExport.addActionListener(new ExportListener());
        v.table.addMouseListener(new TableListener());
        start();
    }

    // ================= LISTENER =================
    class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addNV();
        }
    }

    class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (k == -1) {
                JOptionPane.showMessageDialog(v, "Vui lòng chọn nhân viên cần sửa");
                return;
            }
            updateNV();
        }
    }

    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (k == -1)
                return;
            deleteNV();
            loadTable();
        }
    }

    class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            start();
        }
    }

    class TableListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            k = v.table.getSelectedRow();
        }
    }

    class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            searchNV();
        }

    }

    class ExportListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            exportNV();
        }

    }
    // ================= BUSINESS =================

    // ===== THÊM =====
    public void addNV() {
        Frame parent = (Frame) v.getTopLevelAncestor();
        NhanVienDialog dialog = new NhanVienDialog(parent);
        dialog.setLocationRelativeTo(v); // FIX dialog không hiện

        dialog.btnSave.addActionListener(e -> {
            if (!validateDialog(dialog))
                return;

            try {
                dao.insert(
                        dialog.txtMaNV.getText().trim(),
                        dialog.txtHoTen.getText().trim(),
                        sdf.parse(dialog.txtNgaySinh.getText().trim()),
                        dialog.cbGioiTinh.getSelectedItem().toString(),
                        dialog.txtSDT.getText().trim(),
                        dialog.txtEmail.getText().trim(),
                        dialog.txtDiaChi.getText().trim(),
                        dialog.cbChucVu.getSelectedItem().toString(),
                        sdf.parse(dialog.txtNgayVaoLam.getText().trim()),
                        Double.parseDouble(dialog.txtLuong.getText().trim()),
                        dialog.cbTrangThai.getSelectedItem().toString());
                dialog.dispose();
                start();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Lỗi thêm nhân viên");
            }
        });

        dialog.btnCancel.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    // ===== CẬP NHẬT =====
    public void updateNV() {
        Frame parent = (Frame) v.getTopLevelAncestor();
        NhanVienDialog dialog = new NhanVienDialog(parent);
        dialog.setLocationRelativeTo(v);

        // đổ dữ liệu
        dialog.txtMaNV.setText(v.tableModel.getValueAt(k, 1).toString());
        dialog.txtMaNV.setEditable(false);
        dialog.txtHoTen.setText(v.tableModel.getValueAt(k, 2).toString());
        dialog.txtNgaySinh.setText(v.tableModel.getValueAt(k, 3).toString());
        dialog.cbGioiTinh.setSelectedItem(v.tableModel.getValueAt(k, 4).toString());
        dialog.txtSDT.setText(v.tableModel.getValueAt(k, 5).toString());
        dialog.txtEmail.setText(v.tableModel.getValueAt(k, 6).toString());
        dialog.txtDiaChi.setText(v.tableModel.getValueAt(k, 7).toString());
        dialog.cbChucVu.setSelectedItem(v.tableModel.getValueAt(k, 8).toString());
        dialog.txtNgayVaoLam.setText(v.tableModel.getValueAt(k, 9).toString());
        dialog.txtLuong.setText(v.tableModel.getValueAt(k, 10).toString());
        dialog.cbTrangThai.setSelectedItem(v.tableModel.getValueAt(k, 11).toString());

        dialog.btnSave.addActionListener(e -> {
            if (!validateDialog(dialog))
                return;

            try {
                dao.update(
                        dialog.txtMaNV.getText().trim(),
                        dialog.txtHoTen.getText().trim(),
                        sdf.parse(dialog.txtNgaySinh.getText().trim()),
                        dialog.cbGioiTinh.getSelectedItem().toString(),
                        dialog.txtSDT.getText().trim(),
                        dialog.txtEmail.getText().trim(),
                        dialog.txtDiaChi.getText().trim(),
                        dialog.cbChucVu.getSelectedItem().toString(),
                        sdf.parse(dialog.txtNgayVaoLam.getText().trim()),
                        Double.parseDouble(dialog.txtLuong.getText().trim()),
                        dialog.cbTrangThai.getSelectedItem().toString(),
                        Integer.parseInt(v.tableModel.getValueAt(k, 0).toString()));
                dialog.dispose();
                start();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Lỗi cập nhật nhân viên");
            }
        });

        dialog.btnCancel.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    // ===== XÓA =====
    public void deleteNV() {
        try {
            int id = Integer.parseInt(v.tableModel.getValueAt(k, 0).toString());
            dao.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== VALIDATE =====
    private boolean validateDialog(NhanVienDialog d) {
        if (d.txtMaNV.getText().trim().isEmpty()
                || d.txtHoTen.getText().trim().isEmpty()
                || d.txtNgaySinh.getText().trim().isEmpty()
                || d.txtNgayVaoLam.getText().trim().isEmpty()
                || d.txtLuong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(d, "Vui lòng nhập đầy đủ thông tin");
            return false;
        }

        try {
            sdf.parse(d.txtNgaySinh.getText().trim());
            sdf.parse(d.txtNgayVaoLam.getText().trim());
            Double.parseDouble(d.txtLuong.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(d, "Ngày hoặc lương không hợp lệ (dd/MM/yyyy)");
            return false;
        }
        return true;
    }

    // ===== LOAD TABLE =====
    public void loadTable() {
        try {
            v.tableModel.setRowCount(0);
            ResultSet rs = dao.getCurrentRS();

            while (rs.next()) {
                java.sql.Date ngaySinh = rs.getDate("NgaySinh");
                java.sql.Date ngayVaoLam = rs.getDate("NgayVaoLam");

                v.tableModel.addRow(new Object[] {
                        rs.getInt("ID_NhanVien"),
                        rs.getString("MaNhanVien"),
                        rs.getString("HoTen"),
                        ngaySinh != null ? sdf.format(ngaySinh) : "", // THỊNH: Fix NPE
                        rs.getString("GioiTinh"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("DiaChi"),
                        rs.getString("ChucVu"),
                        ngayVaoLam != null ? sdf.format(ngayVaoLam) : "", // THỊNH: Fix NPE
                        rs.getDouble("Luong"),
                        rs.getString("TrangThai")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchNV() {
        String keyword = v.txtSearch.getText().trim();
        String criteria = v.cbFilter.getSelectedItem().toString();

        try {
            if (keyword.isEmpty()) {
                dao.getAll();
            } else if ("Theo tên".equals(criteria)) {
                dao.searchByName(keyword);
            } else if ("Theo mã".equals(criteria)) {
                dao.searchByCode(keyword);
            } else {
                dao.searchByNameAndCode(keyword);
            }

            loadTable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            dao.getAll();
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportNV() {
        String folderPath = "C:\\Users\\Admin\\Downloads\\English-Manager-master\\English-Manager-master" +
                "\\src\\main\\java\\com\\learningcenter\\report";

        File folder = new File(folderPath);

        String dateStr = new SimpleDateFormat("MM-dd-yyyy").format(new java.util.Date());
        String fileName = "báo cáo nhân viên ngày " + dateStr + ".csv";
        String fullPath = folderPath + File.separator + fileName;

        try (
                FileOutputStream fos = new FileOutputStream(fullPath);
                OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                CSVWriter writer = new CSVWriter(osw, ';', CSVWriter.DEFAULT_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END)) {
            // BOM UTF-8 để Excel không lỗi tiếng Việt
            fos.write(0xEF);
            fos.write(0xBB);
            fos.write(0xBF);

            // Header
            writer.writeNext(new String[] {
                    "ID",
                    "Mã Nhân Viên",
                    "Họ Tên",
                    "Ngày Sinh",
                    "Giới Tính",
                    "SĐT",
                    "Email",
                    "Địa Chỉ",
                    "Chức Vụ",
                    "Ngày Vào Làm",
                    "Lương",
                    "Trạng Thái"
            });
            dao.getAll();
            ResultSet rs = dao.getCurrentRS();

            while (rs.next()) {
                writer.writeNext(new String[] {
                        String.valueOf(rs.getInt("ID_NhanVien")),
                        rs.getString("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getDate("NgaySinh") != null ? rs.getDate("NgaySinh").toString() : "",
                        rs.getString("GioiTinh"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("DiaChi"),
                        rs.getString("ChucVu"),
                        rs.getDate("NgayVaoLam") != null ? rs.getDate("NgayVaoLam").toString() : "",
                        String.valueOf(rs.getDouble("Luong")),
                        rs.getString("TrangThai")
                });
            }

            JOptionPane.showMessageDialog(
                    v,
                    "Xuất file thành công!\n" + fullPath);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    v,
                    "Lỗi xuất file: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
