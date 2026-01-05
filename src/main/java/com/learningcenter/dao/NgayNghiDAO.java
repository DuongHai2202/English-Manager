package com.learningcenter.dao;

import com.learningcenter.model.NgayNghi;
import com.learningcenter.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NgayNghiDAO {

    // Lấy toàn bộ danh sách ngày nghỉ
    public List<NgayNghi> getAll() {
        List<NgayNghi> list = new ArrayList<>();
        String sql = "SELECT * FROM NGAY_NGHI ORDER BY id DESC";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToNgayNghi(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm mới ngày nghỉ
    public boolean add(NgayNghi nn) {
        String sql = "INSERT INTO NGAY_NGHI (ten_ngay_nghi, ngay_bat_dau, ngay_ket_thuc, ghi_chu) VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nn.getTenNgayNghi());
            ps.setDate(2, nn.getNgayBatDau() != null ? new java.sql.Date(nn.getNgayBatDau().getTime()) : null);
            ps.setDate(3, nn.getNgayKetThuc() != null ? new java.sql.Date(nn.getNgayKetThuc().getTime()) : null);
            ps.setString(4, nn.getGhiChu());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật ngày nghỉ
    public boolean update(NgayNghi nn) {
        String sql = "UPDATE NGAY_NGHI SET ten_ngay_nghi=?, ngay_bat_dau=?, ngay_ket_thuc=?, ghi_chu=? WHERE id=?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nn.getTenNgayNghi());
            ps.setDate(2, nn.getNgayBatDau() != null ? new java.sql.Date(nn.getNgayBatDau().getTime()) : null);
            ps.setDate(3, nn.getNgayKetThuc() != null ? new java.sql.Date(nn.getNgayKetThuc().getTime()) : null);
            ps.setString(4, nn.getGhiChu());
            ps.setInt(5, nn.getID_NgayNghi());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa ngày nghỉ
    public boolean delete(int id) {
        String sql = "DELETE FROM NGAY_NGHI WHERE id=?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm phụ để map dữ liệu từ ResultSet sang Object
    private NgayNghi mapResultSetToNgayNghi(ResultSet rs) throws SQLException {
        NgayNghi nn = new NgayNghi();
        nn.setID_NgayNghi(rs.getInt("id"));
        nn.setTenNgayNghi(rs.getString("ten_ngay_nghi"));
        nn.setNgayBatDau(rs.getDate("ngay_bat_dau"));
        nn.setNgayKetThuc(rs.getDate("ngay_ket_thuc"));
        nn.setGhiChu(rs.getString("ghi_chu"));
        return nn;
    }
}