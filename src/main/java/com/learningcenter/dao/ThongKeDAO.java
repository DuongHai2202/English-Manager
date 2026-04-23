package com.learningcenter.dao;

import com.learningcenter.util.DatabaseConnection;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ThongKeDAO {

    /**
     * Thống kê tổng số lượng (Học viên, Giao dịch, Doanh thu)
     */
    public Map<String, Object> getTongQuan() {
        Map<String, Object> map = new LinkedHashMap<>();
        String sql = "SELECT " +
                "(SELECT COUNT(*) FROM HOC_VIEN) as TongHocVien, " +
                "(SELECT COUNT(*) FROM GIAO_DICH) as TongGiaoDich, " +
                "(SELECT SUM(SoTien) FROM GIAO_DICH WHERE (LoaiGiaoDich LIKE '%Học phí%') AND TrangThai = 'Hoàn thành') as TongDoanhThu";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                map.put("TongHocVien", rs.getInt("TongHocVien"));
                map.put("TongGiaoDich", rs.getInt("TongGiaoDich"));
                map.put("TongDoanhThu", rs.getDouble("TongDoanhThu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Thống kê doanh thu theo tháng trong năm hiện tại
     */
    public Map<String, Double> getDoanhThuTheoThang() {
        Map<String, Double> map = new LinkedHashMap<>();
        // Khởi tạo 12 tháng với giá trị 0
        for (int i = 1; i <= 12; i++) {
            map.put("T" + i, 0.0);
        }

        String sql = "SELECT MONTH(NgayGiaoDich) as Thang, SUM(SoTien) as DoanhThu " +
                "FROM GIAO_DICH " +
                "WHERE (LoaiGiaoDich LIKE '%Học phí%') AND TrangThai = 'Hoàn thành' " +
                "GROUP BY MONTH(NgayGiaoDich)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                map.put("T" + rs.getInt("Thang"), rs.getDouble("DoanhThu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Thống kê cơ sở vật chất theo tình trạng
     */
    public Map<String, Double> getCSVCTheoTinhTrang() {
        Map<String, Double> map = new LinkedHashMap<>();
        String sql = "SELECT TinhTrang, SUM(SoLuong) as SoLuong FROM CO_SO_VAT_CHAT GROUP BY TinhTrang";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                map.put(rs.getString("TinhTrang"), (double) rs.getInt("SoLuong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Lấy danh sách giao dịch gần nhất
     */
    public java.util.List<Map<String, Object>> getGiaoDichGanNhat() {
        java.util.List<Map<String, Object>> list = new java.util.ArrayList<>();
        String sql = "SELECT GD.ID_GiaoDich, HV.HoTen, GD.LoaiGiaoDich, GD.SoTien, GD.NgayGiaoDich, GD.TrangThai " +
                "FROM GIAO_DICH GD " +
                "JOIN HOC_VIEN HV ON GD.ID_HocVien = HV.ID_HocVien " +
                "ORDER BY GD.NgayGiaoDich DESC LIMIT 10";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("MaGiaoDich", rs.getString("ID_GiaoDich"));
                map.put("HoTen", rs.getString("HoTen"));
                map.put("LoaiGiaoDich", rs.getString("LoaiGiaoDich"));
                map.put("SoTien", rs.getDouble("SoTien"));
                map.put("NgayGiaoDich", rs.getString("NgayGiaoDich"));
                map.put("TrangThai", rs.getString("TrangThai"));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
