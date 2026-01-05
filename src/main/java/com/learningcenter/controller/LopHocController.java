package com.learningcenter.controller;

import com.learningcenter.dao.KhoaHocDAO;
import com.learningcenter.dao.LopHocDAO;
import com.learningcenter.model.KhoaHoc;
import com.learningcenter.view.dialog.LopHocDialog;
import com.learningcenter.view.panel.LopHocPanel;
import com.opencsv.CSVWriter;

import java.awt.Frame;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class LopHocController {

    public LopHocPanel v;
    LopHocDAO dao = new LopHocDAO();
    int k = -1; 
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public LopHocController(LopHocPanel v) {
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
            addLH();
        }
    }

    class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (k == -1) {
                JOptionPane.showMessageDialog(v, "Vui lòng chọn lớp học cần sửa");
                return;
            }
            updateLH();
        }
    }

    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (k == -1) return;
            deleteLH();
            start();
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
            searchLH();
        }
    }
    class ExportListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           exportLH();
        }
    
    }
    public void addLH() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(v);
        LopHocDialog dialog = new LopHocDialog(parent);

        loadKhoaHocToCombo(dialog);

        dialog.btnSave.addActionListener(e -> {
            try {
                KhoaHoc kh = (KhoaHoc) dialog.cbKhoaHoc.getSelectedItem();

                dao.insert(
                        kh.getID_KhoaHoc(),
                        dialog.txtMaLH.getText().trim(),
                        dialog.txtTenLH.getText().trim(),
                        Integer.parseInt(dialog.txtSiSoToiDa.getText().trim()),
                        sdf.parse(dialog.txtNgayBatDau.getText().trim()),
                        sdf.parse(dialog.txtNgayKetThuc.getText().trim()),
                        dialog.txtPhongHoc.getText().trim(),
                        dialog.cbTrangThai.getSelectedItem().toString()
                );

                dialog.dispose();
                start();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Lỗi thêm lớp học");
            }
        });

        dialog.btnCancel.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public void updateLH() {
        Frame parent = (Frame) SwingUtilities.getWindowAncestor(v);
        LopHocDialog dialog = new LopHocDialog(parent);

        loadKhoaHocToCombo(dialog);

        int idLopHoc = Integer.parseInt(v.tableModel.getValueAt(k, 0).toString());

        dialog.txtMaLH.setText(v.tableModel.getValueAt(k, 1).toString());
        dialog.txtTenLH.setText(v.tableModel.getValueAt(k, 2).toString());
        dialog.txtSiSoToiDa.setText(v.tableModel.getValueAt(k, 4).toString());
        dialog.txtNgayBatDau.setText(v.tableModel.getValueAt(k, 5).toString());
        dialog.txtNgayKetThuc.setText(v.tableModel.getValueAt(k, 6).toString());
        dialog.txtPhongHoc.setText(v.tableModel.getValueAt(k, 7).toString());
        dialog.cbTrangThai.setSelectedItem(v.tableModel.getValueAt(k, 8).toString());

        String tenKhoaHoc = v.tableModel.getValueAt(k, 3).toString();
        for (int i = 0; i < dialog.cbKhoaHoc.getItemCount(); i++) {
            KhoaHoc kh = (KhoaHoc) dialog.cbKhoaHoc.getItemAt(i);
            if (kh.getTenKhoaHoc().equals(tenKhoaHoc)) {
                dialog.cbKhoaHoc.setSelectedIndex(i);
                break;
            }
        }

        dialog.btnSave.addActionListener(e -> {
            try {
                KhoaHoc kh = (KhoaHoc) dialog.cbKhoaHoc.getSelectedItem();

                dao.update(
                        kh.getID_KhoaHoc(),
                        dialog.txtMaLH.getText().trim(),
                        dialog.txtTenLH.getText().trim(),
                        Integer.parseInt(dialog.txtSiSoToiDa.getText().trim()),
                        sdf.parse(dialog.txtNgayBatDau.getText().trim()),
                        sdf.parse(dialog.txtNgayKetThuc.getText().trim()),
                        dialog.txtPhongHoc.getText().trim(),
                        dialog.cbTrangThai.getSelectedItem().toString(),
                        idLopHoc
                );

                dialog.dispose();
                start();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Lỗi cập nhật lớp học");
            }
        });

        dialog.btnCancel.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public void deleteLH() {
        try {
            int id = Integer.parseInt(v.tableModel.getValueAt(k, 0).toString());
            dao.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTable() {
        try {
            v.tableModel.setRowCount(0);
            ResultSet rs = dao.getCurrentRS();

            while (rs.next()) {
                v.tableModel.addRow(new Object[]{
                        rs.getInt("ID_LopHoc"),
                        rs.getString("MaLopHoc"),
                        rs.getString("TenLopHoc"),
                        rs.getString("TenKhoaHoc"),
                        rs.getInt("SiSoToiDa"),
                        rs.getDate("NgayBatDau"),
                        rs.getDate("NgayKetThuc"),
                        rs.getString("PhongHoc"),
                        rs.getString("TrangThai")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchLH() {
        String keyword = v.txtSearch.getText().trim();
        String filter = v.cbFilter.getSelectedItem().toString();

        try {
            if (keyword.isEmpty()) {
                dao.getAll();
            } else if (filter.equals("Theo tên")) {
                dao.searchByName(keyword);
            } else if (filter.equals("Theo mã")) {
                dao.searchByCode(keyword);
            } else if (filter.equals("Theo tình trạng")) {
                dao.searchByStatus(keyword);
            } else if (filter.equals("Tất cả")){
                dao.searchByAll(keyword);
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

    private void loadKhoaHocToCombo(LopHocDialog dialog) {
        try {
            KhoaHocDAO khoaHocDAO = new KhoaHocDAO();
            ResultSet rs = khoaHocDAO.getAllForComboBox();

            dialog.cbKhoaHoc.removeAllItems();
            while (rs.next()) {
                dialog.cbKhoaHoc.addItem(
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
    private void exportLH() {
    String folderPath =
        "C:\\Users\\Admin\\Downloads\\English-Manager-master\\English-Manager-master" +
        "\\src\\main\\java\\com\\learningcenter\\report";

    File folder = new File(folderPath);

    String dateStr = new SimpleDateFormat("MM-dd-yyyy").format(new java.util.Date());
    String fileName = "báo cáo lớp học ngày " + dateStr + ".csv";
    String fullPath = folderPath + File.separator + fileName;

    try (
        FileOutputStream fos = new FileOutputStream(fullPath);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        CSVWriter writer = new CSVWriter(osw,';',CSVWriter.DEFAULT_QUOTE_CHARACTER,
        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
        CSVWriter.DEFAULT_LINE_END)
    ) {

        fos.write(0xEF);
        fos.write(0xBB);
        fos.write(0xBF);

        writer.writeNext(new String[]{
            "ID",
            "Mã lớp học",
            "Tên lớp học",
            "Tên khoá học",
            "Sĩ số tối đa",
            "Ngày bắt đầu",
            "Ngày kết thúc",
            "Phòng học",
            "Trạng Thái"
        });
        dao.getAll();
        ResultSet rs = dao.getCurrentRS();

        while (rs.next()) {
            writer.writeNext(new String[]{
                String.valueOf(rs.getInt("ID_LopHoc")),
                rs.getString("MaLopHoc"),
                rs.getString("TenLopHoc"),
                rs.getString("TenKhoaHoc"),
                rs.getString("SiSoToiDa"),
                rs.getDate("NgayBatDau") != null ? rs.getDate("NgayBatDau").toString() : "",
                rs.getDate("NgayKetThuc") != null ? rs.getDate("NgayKetThuc").toString() : "",
                rs.getString("PhongHoc"),
                rs.getString("TrangThai")
            });
        }

        JOptionPane.showMessageDialog(
            v,
            "Xuất file thành công!\n" + fullPath
        );

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(
            v,
            "Lỗi xuất file: " + e.getMessage(),
            "Lỗi",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
}
