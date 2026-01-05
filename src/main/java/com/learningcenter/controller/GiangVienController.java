package com.learningcenter.controller;

import com.learningcenter.dao.GiangVienDAO;
import com.learningcenter.dao.LopHocDAO;
import com.learningcenter.view.dialog.GiangVienDialog;
import com.learningcenter.view.panel.GiangVienPanel;
import com.opencsv.CSVWriter;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class GiangVienController {

    public GiangVienPanel v;
    GiangVienDAO gvdao = new GiangVienDAO();
    LopHocDAO lopHocDAO = new LopHocDAO();

    int k = -1;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public GiangVienController(GiangVienPanel v) {
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
                JOptionPane.showMessageDialog(v, "Vui lòng chọn giảng viên cần sửa");
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
            if (k >= 0) {
                int idGiangVien = Integer.parseInt(
                        v.model.getValueAt(k, 0).toString());
                loadLopDay(idGiangVien);
            }
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

    public void addNV() {
        Frame parent = (Frame) v.getTopLevelAncestor();
        GiangVienDialog dialog = new GiangVienDialog(parent);
        dialog.setLocationRelativeTo(v);

        dialog.btnSave.addActionListener(e -> {
            if (!validateDialog(dialog))
                return;

            try {
                gvdao.insert(
                        dialog.txtMaNV.getText().trim(),
                        dialog.txtHoTen.getText().trim(),
                        sdf.parse(dialog.txtNgaySinh.getText().trim()),
                        dialog.cbGioiTinh.getSelectedItem().toString(),
                        dialog.txtSDT.getText().trim(),
                        dialog.txtEmail.getText().trim(),
                        dialog.txtChuyenMon.getText().trim(),
                        dialog.txtTrinhDo.getText().trim(),
                        sdf.parse(dialog.txtNgayVaoLam.getText().trim()),
                        Double.parseDouble(dialog.txtMucLuongGio.getText().trim()),
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

    public void updateNV() {
        Frame parent = (Frame) v.getTopLevelAncestor();
        GiangVienDialog dialog = new GiangVienDialog(parent);
        dialog.setLocationRelativeTo(v);

        // đổ dữ liệu
        dialog.txtMaNV.setText(v.model.getValueAt(k, 1).toString());
        dialog.txtMaNV.setEditable(false);
        dialog.txtHoTen.setText(v.model.getValueAt(k, 2).toString());
        dialog.txtNgaySinh.setText(v.model.getValueAt(k, 3).toString());
        dialog.cbGioiTinh.setSelectedItem(v.model.getValueAt(k, 4).toString());
        dialog.txtSDT.setText(v.model.getValueAt(k, 5).toString());
        dialog.txtEmail.setText(v.model.getValueAt(k, 6).toString());
        dialog.txtChuyenMon.setText(v.model.getValueAt(k, 7).toString());
        dialog.txtTrinhDo.setText(v.model.getValueAt(k, 8).toString());
        dialog.txtNgayVaoLam.setText(v.model.getValueAt(k, 9).toString());
        dialog.txtMucLuongGio.setText(v.model.getValueAt(k, 10).toString());
        dialog.cbTrangThai.setSelectedItem(v.model.getValueAt(k, 11).toString());

        dialog.btnSave.addActionListener(e -> {
            if (!validateDialog(dialog))
                return;

            try {
                gvdao.update(
                        dialog.txtMaNV.getText().trim(),
                        dialog.txtHoTen.getText().trim(),
                        sdf.parse(dialog.txtNgaySinh.getText().trim()),
                        dialog.cbGioiTinh.getSelectedItem().toString(),
                        dialog.txtSDT.getText().trim(),
                        dialog.txtEmail.getText().trim(),
                        dialog.txtChuyenMon.getText().trim(),
                        dialog.txtTrinhDo.getText().trim(),
                        sdf.parse(dialog.txtNgayVaoLam.getText().trim()),
                        Double.parseDouble(dialog.txtMucLuongGio.getText().trim()),
                        dialog.cbTrangThai.getSelectedItem().toString(),
                        Integer.parseInt(v.model.getValueAt(k, 0).toString()));
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

    public void deleteNV() {
        try {
            int id = Integer.parseInt(v.model.getValueAt(k, 0).toString());
            gvdao.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateDialog(GiangVienDialog d) {
        if (d.txtMaNV.getText().trim().isEmpty()
                || d.txtHoTen.getText().trim().isEmpty()
                || d.txtNgaySinh.getText().trim().isEmpty()
                || d.txtNgayVaoLam.getText().trim().isEmpty()
                || d.txtMucLuongGio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(d, "Vui lòng nhập đầy đủ thông tin");
            return false;
        }

        try {
            sdf.parse(d.txtNgaySinh.getText().trim());
            sdf.parse(d.txtNgayVaoLam.getText().trim());
            Double.parseDouble(d.txtMucLuongGio.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(d, "Ngày hoặc lương không hợp lệ (dd/MM/yyyy)");
            return false;
        }
        return true;
    }

    public void loadTable() {
        try {
            v.model.setRowCount(0);
            ResultSet rs = gvdao.getCurrentRS();

            while (rs.next()) {
                java.sql.Date ngaySinh = rs.getDate("NgaySinh");
                java.sql.Date ngayVaoLam = rs.getDate("NgayVaoLam");

                v.model.addRow(new Object[] {
                        rs.getInt("ID_GiangVien"),
                        rs.getString("MaGiangVien"),
                        rs.getString("HoTen"),
                        ngaySinh != null ? sdf.format(ngaySinh) : "",
                        rs.getString("GioiTinh"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("ChuyenMon"),
                        rs.getString("TrinhDoHocVan"),
                        ngayVaoLam != null ? sdf.format(ngayVaoLam) : "",
                        rs.getDouble("MucLuongGio"),
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
                gvdao.getAll();
            } else if ("Theo tên".equals(criteria)) {
                gvdao.searchByName(keyword);
            } else if ("Theo mã".equals(criteria)) {
                gvdao.searchByCode(keyword);
            } else {
                gvdao.searchByNameAndCode(keyword);
            }

            loadTable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            gvdao.getAll();
            loadTable();
            v.lopDayModel.setRowCount(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportNV() {
        String folderPath = "C:\\Users\\Admin\\Downloads\\English-Manager-master\\English-Manager-master" +
                "\\src\\main\\java\\com\\learningcenter\\report";

        File folder = new File(folderPath);

        String dateStr = new SimpleDateFormat("MM-dd-yyyy").format(new java.util.Date());
        String fileName = "báo cáo giảng viên ngày " + dateStr + ".csv";
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
                    "Mã Giảng Viên",
                    "Họ Tên",
                    "Ngày Sinh",
                    "Giới Tính",
                    "SĐT",
                    "Email",
                    "Chuyên Môn",
                    "Trình độ học vấn",
                    "Ngày Vào Làm",
                    "Mức Lương Giờ",
                    "Trạng Thái"
            });
            gvdao.getAll();
            ResultSet rs = gvdao.getCurrentRS();

            while (rs.next()) {
                writer.writeNext(new String[] {
                        String.valueOf(rs.getInt("ID_GiangVien")),
                        rs.getString("MaGiangVien"),
                        rs.getString("HoTen"),
                        rs.getDate("NgaySinh") != null ? rs.getDate("NgaySinh").toString() : "",
                        rs.getString("GioiTinh"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("ChuyenMon"),
                        rs.getString("TrinhDoHocVan"),
                        rs.getDate("NgayVaoLam") != null ? rs.getDate("NgayVaoLam").toString() : "",
                        String.valueOf(rs.getDouble("MucLuongGio")),
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

    private void loadLopDay(int idGiangVien) {
        try {
            v.lopDayModel.setRowCount(0);

            ResultSet rs = lopHocDAO.getLopDayByGiangVien(idGiangVien);

            while (rs.next()) {
                v.lopDayModel.addRow(new Object[] {
                        rs.getInt("ID_LopHoc"),
                        rs.getString("MaLopHoc"),
                        rs.getString("TenLopHoc"),
                        rs.getDate("NgayBatDau"),
                        rs.getDate("NgayKetThuc"),
                        rs.getInt("SiSoToiDa"),
                        rs.getString("TrangThai")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(v, "Lỗi load lớp dạy");
        }
    }

}
