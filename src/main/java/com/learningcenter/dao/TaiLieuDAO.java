package com.learningcenter.dao;

import com.learningcenter.model.TaiLieu;
import com.learningcenter.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiLieuDAO {

    /* ================= GET ALL ================= */
    public List<TaiLieu> getAll() {
        List<TaiLieu> list = new ArrayList<>();
        String sql = "SELECT * FROM TAI_LIEU ORDER BY ID_TaiLieu DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ================= SEARCH ================= */
    public List<TaiLieu> search(String keyword, String criteria) {
        List<TaiLieu> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM TAI_LIEU WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            switch (criteria) {
                case "Theo tiêu đề":
                    sql.append(" AND TieuDe LIKE ?");
                    break;
                case "Theo khóa học":
                    sql.append(" AND ID_KhoaHoc = ?");
                    break;
                default: // Tất cả
                    sql.append(" AND (TieuDe LIKE ? OR TenFile LIKE ?)");
                    break;
            }
        }

        sql.append(" ORDER BY ID_TaiLieu DESC");

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            if (keyword != null && !keyword.trim().isEmpty()) {
                if ("Theo khóa học".equals(criteria)) {
                    ps.setInt(1, Integer.parseInt(keyword));
                } else if ("Tất cả".equals(criteria)) {
                    String val = "%" + keyword.trim() + "%";
                    ps.setString(1, val);
                    ps.setString(2, val);
                } else {
                    ps.setString(1, "%" + keyword.trim() + "%");
                }
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ================= FIND BY ID ================= */
    public TaiLieu findById(int id) {
        String sql = "SELECT * FROM TAI_LIEU WHERE ID_TaiLieu = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* ================= ADD ================= */
    public boolean add(TaiLieu tl) {
        String sql = """
                INSERT INTO TAI_LIEU
                (ID_KhoaHoc, TieuDe, MoTa, TenFile, DuongDanFile)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, tl.getIdKhoaHoc());
            ps.setString(2, tl.getTieuDe());
            ps.setString(3, tl.getMoTa());
            ps.setString(4, tl.getTenFile());
            ps.setString(5, tl.getDuongDanFile());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ================= UPDATE ================= */
    public boolean update(TaiLieu tl) {
        String sql = """
                UPDATE TAI_LIEU SET
                ID_KhoaHoc = ?,
                TieuDe = ?,
                MoTa = ?,
                TenFile = ?,
                DuongDanFile = ?
                WHERE ID_TaiLieu = ?
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, tl.getIdKhoaHoc());
            ps.setString(2, tl.getTieuDe());
            ps.setString(3, tl.getMoTa());
            ps.setString(4, tl.getTenFile());
            ps.setString(5, tl.getDuongDanFile());
            ps.setInt(6, tl.getIdTaiLieu());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ================= DELETE ================= */
    public boolean delete(int id) {
        String sql = "DELETE FROM TAI_LIEU WHERE ID_TaiLieu = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ================= MAP RESULT ================= */
    private TaiLieu mapResultSet(ResultSet rs) throws SQLException {
        TaiLieu tl = new TaiLieu();
        tl.setIdTaiLieu(rs.getInt("ID_TaiLieu"));
        tl.setIdKhoaHoc(rs.getInt("ID_KhoaHoc"));
        tl.setTieuDe(rs.getString("TieuDe"));
        tl.setMoTa(rs.getString("MoTa"));
        tl.setTenFile(rs.getString("TenFile"));
        tl.setDuongDanFile(rs.getString("DuongDanFile"));
        return tl;
    }
}
