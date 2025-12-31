package com.learningcenter.dao;

import com.learningcenter.model.TaiKhoan;
import com.learningcenter.util.DatabaseConnection;
import java.sql.*;

public class TaiKhoanDAO {
    public TaiKhoan dangNhap(String user, String pass) {
        String sql = "SELECT * FROM TAI_KHOAN WHERE TenDangNhap = ? AND MatKhau = ? AND TrangThai = 1";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user);
            ps.setString(2, pass);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TaiKhoan tk = new TaiKhoan();
                    tk.setIdTaiKhoan(rs.getInt("ID_TaiKhoan"));
                    tk.setTenDangNhap(rs.getString("TenDangNhap"));
                    tk.setHoTen(rs.getString("HoTen"));
                    tk.setEmail(rs.getString("Email"));
                    return tk;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
