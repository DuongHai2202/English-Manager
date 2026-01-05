package com.learningcenter.dao;

import com.learningcenter.model.HoTro;
import com.learningcenter.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoTroDAO {

    public List<HoTro> getAll() {
        return search("", "Tất cả");
    }

    public List<HoTro> search(String keyword, String criteria) {
        List<HoTro> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM HO_TRO WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            switch (criteria) {
                case "Theo tiêu đề":
                    sql.append(" AND TieuDe LIKE ?");
                    break;
                case "Theo người gửi":
                    sql.append(" AND TenNguoiGui LIKE ?");
                    break;
                case "Theo trạng thái":
                    sql.append(" AND TrangThai LIKE ?");
                    break;
                default: // Tất cả
                    sql.append(" AND (TieuDe LIKE ? OR TenNguoiGui LIKE ? OR TrangThai LIKE ?)");
                    break;
            }
        }

        sql.append(" ORDER BY ID_HoTro DESC");

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            if (keyword != null && !keyword.trim().isEmpty()) {
                String val = "%" + keyword.trim() + "%";

                if ("Tất cả".equals(criteria)) {
                    ps.setString(1, val);
                    ps.setString(2, val);
                    ps.setString(3, val);
                } else {
                    ps.setString(1, val);
                }
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToHoTro(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean add(HoTro ht) {
        String sql = "INSERT INTO HO_TRO (TenNguoiGui, EmailNguoiGui, TieuDe, NoiDung, ChuyenMuc, DoUuTien, TrangThai) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ht.getTenNguoiGui());
            ps.setString(2, ht.getEmailNguoiGui());
            ps.setString(3, ht.getTieuDe());
            ps.setString(4, ht.getNoiDung());
            ps.setString(5, ht.getChuyenMuc());
            ps.setString(6, ht.getDoUuTien());
            ps.setString(7, ht.getTrangThai());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(HoTro ht) {
        String sql = "UPDATE HO_TRO SET TenNguoiGui=?, EmailNguoiGui=?, TieuDe=?, NoiDung=?, "
                   + "ChuyenMuc=?, DoUuTien=?, TrangThai=? WHERE ID_HoTro=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ht.getTenNguoiGui());
            ps.setString(2, ht.getEmailNguoiGui());
            ps.setString(3, ht.getTieuDe());
            ps.setString(4, ht.getNoiDung());
            ps.setString(5, ht.getChuyenMuc());
            ps.setString(6, ht.getDoUuTien());
            ps.setString(7, ht.getTrangThai());
            ps.setInt(8, ht.getIdHoTro());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM HO_TRO WHERE ID_HoTro=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private HoTro mapResultSetToHoTro(ResultSet rs) throws SQLException {
        HoTro ht = new HoTro();
        ht.setIdHoTro(rs.getInt("ID_HoTro"));
        ht.setTenNguoiGui(rs.getString("TenNguoiGui"));
        ht.setEmailNguoiGui(rs.getString("EmailNguoiGui"));
        ht.setTieuDe(rs.getString("TieuDe"));
        ht.setNoiDung(rs.getString("NoiDung"));
        ht.setChuyenMuc(rs.getString("ChuyenMuc"));
        ht.setDoUuTien(rs.getString("DoUuTien"));
        ht.setTrangThai(rs.getString("TrangThai"));
        ht.setNgayTao(rs.getTimestamp("NgayTao"));
        return ht;
    }
}
