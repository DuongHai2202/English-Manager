package com.learningcenter.dao;

import com.learningcenter.model.PhongHoc;
import com.learningcenter.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongHocDAO {

    // Lấy tất cả danh sách phòng học
    public List<PhongHoc> getAll() {
        return search("", "Tất cả");
    }

    // Tìm kiếm linh hoạt theo từ khóa và tiêu chí (Tên hoặc Trạng thái)
    public List<PhongHoc> search(String keyword, String criteria) {
        List<PhongHoc> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM PHONG_HOC WHERE 1=1");

        // Xử lý điều kiện lọc
        if (keyword != null && !keyword.trim().isEmpty()) {
            if ("Theo tên".equals(criteria)) {
                sql.append(" AND ten_phong LIKE ?");
            } else if ("Theo trạng thái".equals(criteria)) {
                sql.append(" AND trang_thai LIKE ?");
            } else { // Tất cả
                sql.append(" AND (ten_phong LIKE ? OR trang_thai LIKE ?)");
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
                    list.add(mapResultSetToPhongHoc(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm mới phòng học
    public boolean add(PhongHoc ph) {
        String sql = "INSERT INTO PHONG_HOC (ten_phong, suc_chua, tang, trang_thai) VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ph.getTenPhong());
            ps.setInt(2, ph.getSucChua());
            ps.setInt(3, ph.getTang());
            ps.setString(4, ph.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin phòng học
    public boolean update(PhongHoc ph) {
        String sql = "UPDATE PHONG_HOC SET ten_phong=?, suc_chua=?, tang=?, trang_thai=? WHERE id=?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ph.getTenPhong());
            ps.setInt(2, ph.getSucChua());
            ps.setInt(3, ph.getTang());
            ps.setString(4, ph.getTrangThai());
            ps.setInt(5, ph.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa phòng học theo ID
    public boolean delete(int id) {
        String sql = "DELETE FROM PHONG_HOC WHERE id=?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method: Ánh xạ từ ResultSet sang Object PhongHoc
    private PhongHoc mapResultSetToPhongHoc(ResultSet rs) throws SQLException {
        PhongHoc ph = new PhongHoc();
        ph.setId(rs.getInt("id"));
        ph.setTenPhong(rs.getString("ten_phong"));
        ph.setSucChua(rs.getInt("suc_chua"));
        ph.setTang(rs.getInt("tang"));
        ph.setTrangThai(rs.getString("trang_thai"));
        return ph;
    }
}