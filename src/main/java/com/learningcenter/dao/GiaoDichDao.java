package com.learningcenter.dao;

import com.learningcenter.model.GiaoDich;
import com.learningcenter.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GiaoDichDao {

    public List<GiaoDich> search(String keyword, String criteria) {
        List<GiaoDich> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT gd.*, hv.HoTen as TenHocVien FROM GIAO_DICH gd "
                + "LEFT JOIN HOC_VIEN hv ON gd.ID_HocVien = hv.ID_HocVien WHERE 1 = 1");
        List<Object> params = new ArrayList<>();

        // Kiểm tra keyword khác null và rỗng
        if (keyword != null && !keyword.trim().isEmpty()) {
            String searchVal = "%" + keyword.trim() + "%";

            if ("Theo số tiền".equals(criteria)) {
                sql.append(" AND gd.SoTien = ?");
                params.add(keyword.trim());
            } else if ("Theo tháng".equals(criteria)) {
                sql.append(" AND MONTH(gd.NgayGiaoDich) = ?");
                params.add(keyword.trim());
            } else if ("Theo loại giao dịch".equals(criteria)) {
                sql.append(" AND gd.LoaiGiaoDich LIKE ?");
                params.add(searchVal);
            } else { // Tất cả
                sql.append(
                        " AND (gd.LoaiGiaoDich LIKE ? OR gd.NoiDung LIKE ? OR gd.PhuongThucThanhToan LIKE ? OR hv.HoTen LIKE ?)");
                params.add(searchVal);
                params.add(searchVal);
                params.add(searchVal);
                params.add(searchVal);
            }
        }

        // Xếp mới nhất lên trên
        sql.append(" ORDER BY gd.ID_GiaoDich DESC");

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Gán tham số động
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToGiaoDich(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<GiaoDich> getAll() {
        return search(null, "Tất cả");
    }

    public boolean add(GiaoDich gd) {
        String sql = "INSERT INTO GIAO_DICH (ID_HocVien, LoaiGiaoDich, SoTien, PhuongThucThanhToan, NgayGiaoDich, TrangThai, NoiDung) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gd.getIdHocVien());
            ps.setString(2, gd.getLoaiGiaoDich());
            ps.setDouble(3, gd.getSoTien());
            ps.setString(4, gd.getPhuongThucThanhToan());
            ps.setDate(5, gd.getNgayGiaoDich() != null ? new Date(gd.getNgayGiaoDich().getTime())
                    : new Date(System.currentTimeMillis()));
            ps.setString(6, gd.getTrangThai());
            ps.setString(7, gd.getNoiDung());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(GiaoDich gd) {
        String sql = "UPDATE GIAO_DICH SET ID_HocVien = ?, LoaiGiaoDich = ?, SoTien = ?, PhuongThucThanhToan = ?, "
                + "NgayGiaoDich = ?, TrangThai = ?, NoiDung = ? WHERE ID_GiaoDich = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gd.getIdHocVien());
            ps.setString(2, gd.getLoaiGiaoDich());
            ps.setDouble(3, gd.getSoTien());
            ps.setString(4, gd.getPhuongThucThanhToan());
            ps.setDate(5, gd.getNgayGiaoDich() != null ? new Date(gd.getNgayGiaoDich().getTime())
                    : new Date(System.currentTimeMillis()));
            ps.setString(6, gd.getTrangThai());
            ps.setString(7, gd.getNoiDung());
            ps.setInt(8, gd.getIdGiaoDich());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM GIAO_DICH WHERE ID_GiaoDich = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public GiaoDich mapResultSetToGiaoDich(ResultSet rs) throws SQLException {
        GiaoDich gd = new GiaoDich();
        gd.setIdGiaoDich(rs.getInt("ID_GiaoDich"));
        gd.setIdHocVien(rs.getInt("ID_HocVien"));
        gd.setLoaiGiaoDich(rs.getString("LoaiGiaoDich"));
        gd.setSoTien(rs.getDouble("SoTien"));
        gd.setPhuongThucThanhToan(rs.getString("PhuongThucThanhToan"));
        gd.setNgayGiaoDich(rs.getDate("NgayGiaoDich"));
        gd.setTrangThai(rs.getString("TrangThai"));
        gd.setNoiDung(rs.getString("NoiDung"));

        // Lấy thêm tên học viên từ kết quả JOIN
        try {
            gd.setTenHocVien(rs.getString("TenHocVien"));
        } catch (SQLException e) {
            // Cột có thể không tồn tại trong một số truy vấn khác nếu không JOIN
        }

        return gd;
    }
}
