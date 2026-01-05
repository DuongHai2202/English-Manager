package com.learningcenter.dao;

import com.learningcenter.model.ChungChi;
import com.learningcenter.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChungChiDAO {

    public List<ChungChi> getAll() {
        return search("", "Tất cả");
    }

    /**
     * Tìm kiếm chứng chỉ theo từ khóa và tiêu chí
     * 
     * @param keyword:  Từ khóa tìm kiếm
     * @param criteria: "Theo loại", "Theo tên" hoặc "Tất cả"
     */
    public List<ChungChi> search(String keyword, String criteria) {
        List<ChungChi> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM CHUNG_CHI WHERE 1=1"); // THỊNH

        if (keyword != null && !keyword.trim().isEmpty()) {
            if ("Theo loại".equals(criteria)) {
                sql.append(" AND loai_chung_chi LIKE ?");
            } else if ("Theo tên".equals(criteria)) {
                sql.append(" AND ten_chung_chi LIKE ?");
            } else { // Tất cả
                sql.append(" AND (loai_chung_chi LIKE ? OR ten_chung_chi LIKE ?)");
            }
        }

        sql.append(" ORDER BY id DESC");

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
                    list.add(mapResultSetToChungChi(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(ChungChi cc) {
        String sql = "INSERT INTO CHUNG_CHI (loai_chung_chi, ten_chung_chi, mo_ta_ngan) VALUES (?, ?, ?)"; // THỊNH
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cc.getLoaiChungChi());
            ps.setString(2, cc.getTenChungChi());
            ps.setString(3, cc.getMoTaNgan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(ChungChi cc) {
        String sql = "UPDATE CHUNG_CHI SET loai_chung_chi=?, ten_chung_chi=?, mo_ta_ngan=? WHERE id=?"; // THỊNH
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cc.getLoaiChungChi());
            ps.setString(2, cc.getTenChungChi());
            ps.setString(3, cc.getMoTaNgan());
            ps.setInt(4, cc.getID_ChungChi());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM CHUNG_CHI WHERE id=?"; // THỊNH
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ChungChi mapResultSetToChungChi(ResultSet rs) throws SQLException {
        ChungChi cc = new ChungChi();
        cc.setID_ChungChi(rs.getInt("id"));
        cc.setLoaiChungChi(rs.getString("loai_chung_chi"));
        cc.setTenChungChi(rs.getString("ten_chung_chi"));
        cc.setMoTaNgan(rs.getString("mo_ta_ngan"));
        return cc;
    }
}