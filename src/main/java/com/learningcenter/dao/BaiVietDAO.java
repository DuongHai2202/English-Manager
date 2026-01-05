package com.learningcenter.dao;

import com.learningcenter.model.BaiViet;
import com.learningcenter.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaiVietDAO {

    public List<BaiViet> getAll() {
        List<BaiViet> list = new ArrayList<>();
        String sql = "SELECT * FROM BAI_VIET ORDER BY NgayDang DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private BaiViet mapResultSetToBaiViet(ResultSet rs) throws SQLException {
    BaiViet bv = new BaiViet();
    bv.setIdBaiViet(rs.getInt("ID_BaiViet"));
    bv.setTieuDe(rs.getString("TieuDe"));
    bv.setNoiDung(rs.getString("NoiDung"));
    bv.setLoaiBaiViet(rs.getString("LoaiBaiViet"));
    bv.setTrangThai(rs.getString("TrangThai"));
    bv.setNgayDang(rs.getTimestamp("NgayDang")); // hoặc rs.getDate nếu bạn dùng Date
    return bv;
}

   public List<BaiViet> search(String keyword, String criteria) {
    List<BaiViet> list = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT * FROM BAI_VIET WHERE 1=1");

    boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
    String kw = hasKeyword ? "%" + keyword.trim() + "%" : null;

    if (hasKeyword) {
        if ("Theo tiêu đề".equals(criteria)) {
            sql.append(" AND TieuDe LIKE ?");
        } else if ("Theo loại".equals(criteria)) {
            sql.append(" AND LoaiBaiViet LIKE ?");
        } else { // Tất cả
            sql.append(" AND (TieuDe LIKE ? OR LoaiBaiViet LIKE ? OR NoiDung LIKE ?)");
        }
    }

    sql.append(" ORDER BY ID_BaiViet DESC");

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {

        if (hasKeyword) {
            if ("Tất cả".equals(criteria)) {
                ps.setString(1, kw);
                ps.setString(2, kw);
                ps.setString(3, kw);
            } else {
                ps.setString(1, kw);
            }
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToBaiViet(rs));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}
    public boolean add(BaiViet bv) {
        String sql = "INSERT INTO BAI_VIET (TieuDe, NoiDung, LoaiBaiViet, TrangThai) VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, bv.getTieuDe());
            ps.setString(2, bv.getNoiDung());
            ps.setString(3, bv.getLoaiBaiViet());
            ps.setString(4, bv.getTrangThai());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(BaiViet bv) {
        String sql = "UPDATE BAI_VIET SET TieuDe=?, NoiDung=?, LoaiBaiViet=?, TrangThai=? WHERE ID_BaiViet=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, bv.getTieuDe());
            ps.setString(2, bv.getNoiDung());
            ps.setString(3, bv.getLoaiBaiViet());
            ps.setString(4, bv.getTrangThai());
            ps.setInt(5, bv.getIdBaiViet());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM BAI_VIET WHERE ID_BaiViet=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private BaiViet map(ResultSet rs) throws SQLException {
        BaiViet bv = new BaiViet();
        bv.setIdBaiViet(rs.getInt("ID_BaiViet"));
        bv.setTieuDe(rs.getString("TieuDe"));
        bv.setNoiDung(rs.getString("NoiDung"));
        bv.setLoaiBaiViet(rs.getString("LoaiBaiViet"));
        bv.setTrangThai(rs.getString("TrangThai"));
        bv.setNgayDang(rs.getTimestamp("NgayDang"));
        return bv;
    }
}
