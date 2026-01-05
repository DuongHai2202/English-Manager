package com.learningcenter.dao;

import com.learningcenter.model.LichHoc;
import com.learningcenter.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LichHocDAO {

    public List<LichHoc> getAll() {
        return search("", "Tất cả");
    }

    public List<LichHoc> search(String keyword, String criteria) {
        List<LichHoc> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT lh.ID_LichHoc, lh.ID_LopHoc, l.MaLopHoc, l.TenLopHoc, " +
                "lh.ThuTrongTuan, lh.GioBatDau, lh.GioKetThuc, lh.PhongHoc " +
                "FROM LICH_HOC lh " +
                "JOIN LOP_HOC l ON lh.ID_LopHoc = l.ID_LopHoc " +
                "WHERE 1=1"
        );

        if (keyword != null && !keyword.trim().isEmpty()) {
            if ("Theo lớp".equals(criteria)) {
                sql.append(" AND l.MaLopHoc LIKE ?");
            } else if ("Theo thứ".equals(criteria)) {
                sql.append(" AND lh.ThuTrongTuan LIKE ?");
            } else { // Tất cả
                sql.append(" AND (l.MaLopHoc LIKE ? OR lh.ThuTrongTuan LIKE ?)");
            }
        }

        sql.append(" ORDER BY lh.ID_LichHoc DESC");

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            if (keyword != null && !keyword.trim().isEmpty()) {
                String val = "%" + keyword.trim() + "%";
                if ("Tất cả".equals(criteria)) {
                    ps.setString(1, val);
                    ps.setString(2, val);
                } else {
                    ps.setString(1, val);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToLichHoc(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
public Integer getIdLopHocByMa(String maLop) {
    String sql = "SELECT ID_LopHoc FROM LOP_HOC WHERE MaLopHoc = ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, maLop);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
   public boolean add(LichHoc lh) {
    String sql = "INSERT INTO LICH_HOC (ID_LopHoc, ThuTrongTuan, GioBatDau, GioKetThuc, PhongHoc) VALUES (?, ?, ?, ?, ?)";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, lh.getIdLopHoc());      // BẮT BUỘC
        ps.setString(2, lh.getThuTrongTuan());
        ps.setTime(3, lh.getGioBatDau());
        ps.setTime(4, lh.getGioKetThuc());
        ps.setString(5, lh.getPhongHoc());

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();   // để bạn thấy lỗi thật
        return false;
    }
}

    public boolean update(LichHoc lh) {
    String sql = """
        UPDATE LICH_HOC
        SET ThuTrongTuan=?, GioBatDau=?, GioKetThuc=?, PhongHoc=?
        WHERE ID_LichHoc=?
    """;

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, lh.getThuTrongTuan());
        ps.setTime(2, lh.getGioBatDau());
        ps.setTime(3, lh.getGioKetThuc());
        ps.setString(4, lh.getPhongHoc());
        ps.setInt(5, lh.getIdLichHoc()); // 🔴 PHẢI CÓ

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    public boolean delete(int id) {
        String sql = "DELETE FROM LICH_HOC WHERE ID_LichHoc=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private LichHoc mapResultSetToLichHoc(ResultSet rs) throws SQLException {
        LichHoc lh = new LichHoc();
        lh.setIdLichHoc(rs.getInt("ID_LichHoc"));
        lh.setIdLopHoc(rs.getInt("ID_LopHoc"));
        lh.setMaLopHoc(rs.getString("MaLopHoc"));
        lh.setTenLopHoc(rs.getString("TenLopHoc"));
        lh.setThuTrongTuan(rs.getString("ThuTrongTuan"));
        lh.setGioBatDau(rs.getTime("GioBatDau"));
        lh.setGioKetThuc(rs.getTime("GioKetThuc"));
        lh.setPhongHoc(rs.getString("PhongHoc"));
        return lh;
    }
}
