package com.learningcenter.dao;

import com.learningcenter.model.HocVien;
import com.learningcenter.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HocVienDAO {
    public List<HocVien> getAll() {
        return search("", "Tất cả");
    }

    public List<HocVien> search(String keyword, String criteria) {
        List<HocVien> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM HOC_VIEN WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            String val = "%" + keyword.trim() + "%";
            if ("Theo tên".equals(criteria)) {
                sql.append(" AND HoTen LIKE ?");
                params.add(val);
            } else if ("Theo mã".equals(criteria)) {
                sql.append(" AND MaHocVien LIKE ?");
                params.add(val);
            } else if ("Theo id".equals(criteria)) {
                sql.append(" AND ID_HocVien LIKE ?");
            } else { // Tất cả
                sql.append(" AND (HoTen LIKE ? OR MaHocVien LIKE ?)");
                params.add(val);
                params.add(val);
            }
        }

        sql.append(" ORDER BY ID_HocVien DESC");

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql.toString())) {

            // Gán tham số động
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToHocVien(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(HocVien hv) {
        String sql = "INSERT INTO HOC_VIEN (MaHocVien, HoTen, NgaySinh, GioiTinh, SoDienThoai, Email, DiaChi, TrinhDo, NgayNhapHoc, TrangThai, GhiChu) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, hv.getMaHocVien());
            ps.setString(2, hv.getHoTen());
            ps.setDate(3, hv.getNgaySinh() != null ? new java.sql.Date(hv.getNgaySinh().getTime()) : null);
            ps.setString(4, hv.getGioiTinh());
            ps.setString(5, hv.getSoDienThoai());
            ps.setString(6, hv.getEmail());
            ps.setString(7, hv.getDiaChi());
            ps.setString(8, hv.getTrinhDo());
            ps.setDate(9, new java.sql.Date(new java.util.Date().getTime())); // Default to now
            ps.setString(10, hv.getTrangThai());
            ps.setString(11, hv.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(HocVien hv) {
        String sql = "UPDATE HOC_VIEN SET MaHocVien=?, HoTen=?, NgaySinh=?, GioiTinh=?, SoDienThoai=?, Email=?, DiaChi=?, TrinhDo=?, TrangThai=?, GhiChu=? "
                +
                "WHERE ID_HocVien=?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, hv.getMaHocVien());
            ps.setString(2, hv.getHoTen());
            ps.setDate(3, hv.getNgaySinh() != null ? new java.sql.Date(hv.getNgaySinh().getTime()) : null);
            ps.setString(4, hv.getGioiTinh());
            ps.setString(5, hv.getSoDienThoai());
            ps.setString(6, hv.getEmail());
            ps.setString(7, hv.getDiaChi());
            ps.setString(8, hv.getTrinhDo());
            ps.setString(9, hv.getTrangThai());
            ps.setString(10, hv.getGhiChu());
            ps.setInt(11, hv.getIdHocVien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM HOC_VIEN WHERE ID_HocVien=?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private HocVien mapResultSetToHocVien(ResultSet rs) throws SQLException {
        HocVien hv = new HocVien();
        hv.setIdHocVien(rs.getInt("ID_HocVien"));
        hv.setMaHocVien(rs.getString("MaHocVien"));
        hv.setHoTen(rs.getString("HoTen"));
        hv.setNgaySinh(rs.getDate("NgaySinh"));
        hv.setGioiTinh(rs.getString("GioiTinh"));
        hv.setSoDienThoai(rs.getString("SoDienThoai"));
        hv.setEmail(rs.getString("Email"));
        hv.setDiaChi(rs.getString("DiaChi"));
        hv.setTrinhDo(rs.getString("TrinhDo"));
        hv.setNgayNhapHoc(rs.getDate("NgayNhapHoc"));
        hv.setTrangThai(rs.getString("TrangThai"));
        hv.setGhiChu(rs.getString("GhiChu"));
        return hv;
    }
}
