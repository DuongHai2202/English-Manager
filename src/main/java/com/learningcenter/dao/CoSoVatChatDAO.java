package com.learningcenter.dao;

import com.learningcenter.model.CoSoVatChat;
import com.learningcenter.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoSoVatChatDAO {

    public List<CoSoVatChat> getAll() {
        return search("", "Tất cả");
    }

    public List<CoSoVatChat> search(String keyword, String criteria) {
        List<CoSoVatChat> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM CO_SO_VAT_CHAT WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            if ("Tất cả".equals(criteria)) {
                sql.append("AND (TenCoSoVatChat LIKE ? OR LoaiCoSoVatChat LIKE ?)");
                params.add("%" + keyword + "%");
                params.add("%" + keyword + "%");
            } else if ("Theo tên".equals(criteria)) {
                sql.append("AND TenCoSoVatChat LIKE ?");
                params.add("%" + keyword + "%");
            } else if ("Theo loại".equals(criteria)) {
                sql.append("AND LoaiCoSoVatChat LIKE ?");
                params.add("%" + keyword + "%");
            }
        }
        sql.append(" ORDER BY ID_CoSoVatChat DESC");

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(CoSoVatChat csvc) {
        String sql = "INSERT INTO CO_SO_VAT_CHAT (TenCoSoVatChat, LoaiCoSoVatChat, SoLuong, DonViTinh, TinhTrang, NgayMua, GhiChu) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, csvc.getTenCoSoVatChat());
            ps.setString(2, csvc.getLoaiCoSoVatChat());
            ps.setInt(3, csvc.getSoLuong());
            ps.setString(4, csvc.getDonViTinh());
            ps.setString(5, csvc.getTinhTrang());
            ps.setDate(6, csvc.getNgayMua() != null ? new java.sql.Date(csvc.getNgayMua().getTime()) : null);
            ps.setString(7, csvc.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(CoSoVatChat csvc) {
        String sql = "UPDATE CO_SO_VAT_CHAT SET TenCoSoVatChat=?, LoaiCoSoVatChat=?, SoLuong=?, DonViTinh=?, TinhTrang=?, NgayMua=?, GhiChu=? WHERE ID_CoSoVatChat=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, csvc.getTenCoSoVatChat());
            ps.setString(2, csvc.getLoaiCoSoVatChat());
            ps.setInt(3, csvc.getSoLuong());
            ps.setString(4, csvc.getDonViTinh());
            ps.setString(5, csvc.getTinhTrang());
            ps.setDate(6, csvc.getNgayMua() != null ? new java.sql.Date(csvc.getNgayMua().getTime()) : null);
            ps.setString(7, csvc.getGhiChu());
            ps.setInt(8, csvc.getIdCoSoVatChat());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM CO_SO_VAT_CHAT WHERE ID_CoSoVatChat=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private CoSoVatChat map(ResultSet rs) throws SQLException {
        CoSoVatChat csvc = new CoSoVatChat();
        csvc.setIdCoSoVatChat(rs.getInt("ID_CoSoVatChat"));
        csvc.setTenCoSoVatChat(rs.getString("TenCoSoVatChat"));
        csvc.setLoaiCoSoVatChat(rs.getString("LoaiCoSoVatChat"));
        csvc.setSoLuong(rs.getInt("SoLuong"));
        csvc.setDonViTinh(rs.getString("DonViTinh"));
        csvc.setTinhTrang(rs.getString("TinhTrang"));
        csvc.setNgayMua(rs.getDate("NgayMua"));
        csvc.setGhiChu(rs.getString("GhiChu"));
        return csvc;
    }
}
