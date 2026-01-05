/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.dao;

import com.learningcenter.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.learningcenter.model.LopHoc;

/**
 *
 * @author Admin
 */
public class LopHocDAO {
    private ResultSet currentRS;

    public ResultSet getCurrentRS() {
        return currentRS;
    }

    public List<LopHoc> getAllList() {
        List<LopHoc> list = new ArrayList<>();
        String sql = "SELECT * FROM LOP_HOC";
        try (Connection con = DatabaseConnection.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new LopHoc(
                        rs.getInt("ID_LopHoc"),
                        rs.getInt("ID_KhoaHoc"),
                        rs.getString("MaLopHoc"),
                        rs.getString("TenLopHoc"),
                        rs.getInt("SiSoToiDa"),
                        rs.getDate("NgayBatDau"),
                        rs.getDate("NgayKetThuc"),
                        rs.getString("PhongHoc"),
                        rs.getString("TrangThai")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void getAll() throws SQLException {
        String sql = """
                    SELECT
                        lh.ID_LopHoc,
                        lh.MaLopHoc,
                        lh.TenLopHoc,
                        kh.TenKhoaHoc,
                        lh.SiSoToiDa,
                        lh.NgayBatDau,
                        lh.NgayKetThuc,
                        lh.PhongHoc,
                        lh.TrangThai
                    FROM lop_hoc lh
                    JOIN khoa_hoc kh ON lh.ID_KhoaHoc = kh.ID_KhoaHoc
                    ORDER BY lh.ID_LopHoc ASC
                """;
        Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        currentRS = st.executeQuery(sql);
    }

    public void insert(int ID_KhoaHoc, String MaLopHoc, String TenLopHoc, int SiSoToiDa, Date NgayBatDau,
            Date NgayKetThuc, String PhongHoc, String TrangThai) throws SQLException {
        String sql = "insert into lop_hoc(ID_KhoaHoc, MaLopHoc, TenLopHoc, SiSoToiDa, NgayBatDau, NgayKetThuc, PhongHoc, TrangThai)"
                + "values(?,?,?,?,?,?,?,?)";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareCall(sql);
        ps.setInt(1, ID_KhoaHoc);
        ps.setString(2, MaLopHoc);
        ps.setString(3, TenLopHoc);
        ps.setInt(4, SiSoToiDa);
        ps.setDate(5, NgayBatDau != null ? new java.sql.Date(NgayBatDau.getTime()) : null);
        ps.setDate(6, NgayKetThuc != null ? new java.sql.Date(NgayKetThuc.getTime()) : null);
        ps.setString(7, PhongHoc);
        ps.setString(8, TrangThai);
        ps.executeUpdate();
    }

    public void update(int ID_KhoaHoc, String MaLopHoc, String TenLopHoc, int SiSoToiDa, Date NgayBatDau,
            Date NgayKetThuc, String PhongHoc, String TrangThai, int ID_LopHoc) throws SQLException {
        String sql = "update lop_hoc set ID_KhoaHoc=?, MaLopHoc=?, TenLopHoc=?, SiSoToiDa=?, NgayBatDau=?, NgayKetThuc=?, PhongHoc=?, "
                + "TrangThai =? where ID_LopHoc=?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareCall(sql);
        ps.setInt(1, ID_KhoaHoc);
        ps.setString(2, MaLopHoc);
        ps.setString(3, TenLopHoc);
        ps.setInt(4, SiSoToiDa);
        ps.setDate(5, NgayBatDau != null ? new java.sql.Date(NgayBatDau.getTime()) : null);
        ps.setDate(6, NgayKetThuc != null ? new java.sql.Date(NgayKetThuc.getTime()) : null);
        ps.setString(7, PhongHoc);
        ps.setString(8, TrangThai);
        ps.setInt(9, ID_LopHoc);
        ps.executeUpdate();
    }

    public void delete(int ID_LopHoc) throws SQLException {
        String sql = "delete from lop_hoc where ID_LopHoc = ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareCall(sql);
        ps.setInt(1, ID_LopHoc);
        ps.executeUpdate();
    }

    public void searchByName(String keyword) throws SQLException {
        String sql = """
                    SELECT
                        lh.ID_LopHoc,
                        lh.MaLopHoc,
                        lh.TenLopHoc,
                        kh.TenKhoaHoc,
                        lh.SiSoToiDa,
                        lh.NgayBatDau,
                        lh.NgayKetThuc,
                        lh.PhongHoc,
                        lh.TrangThai
                    FROM lop_hoc lh
                    JOIN khoa_hoc kh ON lh.ID_KhoaHoc = kh.ID_KhoaHoc
                    WHERE lh.TenLopHoc LIKE ?
                    ORDER BY lh.ID_LopHoc ASC
                """;
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + keyword.trim() + "%");
        currentRS = ps.executeQuery();
    }

    public void searchByCode(String keyword) throws SQLException {
        String sql = """
                    SELECT
                        lh.ID_LopHoc,
                        lh.MaLopHoc,
                        lh.TenLopHoc,
                        kh.TenKhoaHoc,
                        lh.SiSoToiDa,
                        lh.NgayBatDau,
                        lh.NgayKetThuc,
                        lh.PhongHoc,
                        lh.TrangThai
                    FROM lop_hoc lh
                    JOIN khoa_hoc kh ON lh.ID_KhoaHoc = kh.ID_KhoaHoc
                    WHERE lh.MaLopHoc LIKE ?
                    ORDER BY lh.ID_LopHoc ASC
                """;
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + keyword.trim() + "%");
        currentRS = ps.executeQuery();
    }

    public void searchByStatus(String keyword) throws SQLException {
        String sql = """
                    SELECT
                        lh.ID_LopHoc,
                        lh.MaLopHoc,
                        lh.TenLopHoc,
                        kh.TenKhoaHoc,
                        lh.SiSoToiDa,
                        lh.NgayBatDau,
                        lh.NgayKetThuc,
                        lh.PhongHoc,
                        lh.TrangThai
                    FROM lop_hoc lh
                    JOIN khoa_hoc kh ON lh.ID_KhoaHoc = kh.ID_KhoaHoc
                    WHERE lh.TrangThai like ?
                    ORDER BY lh.ID_LopHoc ASC
                """;
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + keyword.trim() + "%");
        currentRS = ps.executeQuery();
    }

    public void searchByAll(String keyword) throws SQLException {
        String sql = """
                    SELECT
                        lh.ID_LopHoc,
                        lh.MaLopHoc,
                        lh.TenLopHoc,
                        kh.TenKhoaHoc,
                        lh.SiSoToiDa,
                        lh.NgayBatDau,
                        lh.NgayKetThuc,
                        lh.PhongHoc,
                        lh.TrangThai
                    FROM lop_hoc lh
                    JOIN khoa_hoc kh ON lh.ID_KhoaHoc = kh.ID_KhoaHoc
                    WHERE lh.TenLopHoc LIKE ? or lh.MaLopHoc LIKE ? or lh.TrangThai like ?
                    ORDER BY lh.ID_LopHoc ASC
                """;

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        String val = "%" + keyword.trim() + "%";
        ps.setString(1, val);
        ps.setString(2, val);
        ps.setString(3, val);
        currentRS = ps.executeQuery();
    }

    // trong LopHocDAO
    public ResultSet getLopChuaCoGiangVienByKhoaHoc(int idKhoaHoc) throws SQLException {
        String sql = """
                    SELECT
                        lh.ID_LopHoc,
                        lh.MaLopHoc,
                        lh.TenLopHoc,
                        lh.NgayBatDau,
                        lh.NgayKetThuc,
                        lh.SiSoToiDa
                    FROM lop_hoc lh
                    LEFT JOIN phan_lop pl ON lh.ID_LopHoc = pl.ID_LopHoc
                    WHERE pl.ID_LopHoc IS NULL
                      AND lh.ID_KhoaHoc = ?
                """;

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idKhoaHoc);
        currentRS = ps.executeQuery();
        return currentRS;
    }

    public void phanCongGiangVien(int idLopHoc, int idGiangVien) throws SQLException {
        String sql = """
                    INSERT INTO phan_lop (ID_LopHoc, ID_GiangVien)
                    VALUES (?, ?)
                """;

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idLopHoc);
        ps.setInt(2, idGiangVien);
        ps.executeUpdate();
    }

    // trong LopHocDAO
    public ResultSet getLopDayByGiangVien(int idGiangVien) throws SQLException {
        String sql = """
                    SELECT
                        lh.ID_LopHoc,
                        lh.MaLopHoc,
                        lh.TenLopHoc,
                        lh.NgayBatDau,
                        lh.NgayKetThuc,
                        lh.SiSoToiDa,
                        lh.TrangThai
                    FROM lop_hoc lh
                    JOIN phan_lop pl ON lh.ID_LopHoc = pl.ID_LopHoc
                    WHERE pl.ID_GiangVien = ?
                """;

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idGiangVien);

        currentRS = ps.executeQuery();
        return currentRS;
    }

}
