/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.dao;

import com.learningcenter.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class GiangVienDAO {
    private ResultSet CurrentRS;
    public ResultSet getCurrentRS(){
        return CurrentRS;
    }
     public void getAll() throws SQLException{
    String sql = "select * from giang_vien order by  ID_GiangVien ASC";
    Connection conn = DatabaseConnection.getConnection();
    Statement st = conn.createStatement();
    CurrentRS = st.executeQuery(sql);        
    }
    public void insert(String MaGiangVien, String HoTen, Date NgaySinh, String GioiTinh,String SoDienThoai, String Email, String ChuyenMon, 
            String TrinhDoHocVan,Date NgayVaoLam, Double MucLuongGio, String TrangThai) throws SQLException{
    String sql = "insert into giang_vien(MaGiangVien,HoTen, NgaySinh, GioiTinh, SoDienThoai, Email, ChuyenMon,"
            + "TrinhDoHocVan, NgayVaoLam, MucLuongGio, TrangThai ) values(?,?,?,?,?,?,?,?,?,?,?)";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareCall(sql);
    ps.setString(1, MaGiangVien);
    ps.setString(2, HoTen);
    ps.setDate(3, NgaySinh != null ? new java.sql.Date(NgaySinh.getTime()) : null);
    ps.setString(4, GioiTinh);
    ps.setString(5, SoDienThoai);
    ps.setString(6, Email);
    ps.setString(7, ChuyenMon);
    ps.setString(8, TrinhDoHocVan);
    ps.setDate(9, NgayVaoLam != null ? new java.sql.Date(NgayVaoLam.getTime()) : null);
    ps.setDouble(10, MucLuongGio);
    ps.setString(11, TrangThai);
    ps.executeUpdate();
    }
    public void update(String MaGiangVien, String HoTen, Date NgaySinh, String GioiTinh,String SoDienThoai, String Email, String ChuyenMon, 
            String TrinhDoHocVan,Date NgayVaoLam, Double MucLuongGio, String TrangThai, int ID_GiangVien) throws SQLException{
    String sql = "update giang_vien set MaGiangVien=?, HoTen =?, NgaySinh=?, GioiTinh=?, SoDienThoai =?, Email=?, "
            + "ChuyenMon=?, TrinhDoHocVan=?, NgayVaoLam=?, MucLuongGio=?, TrangThai=? where ID_GiangVien=?";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareCall(sql);
    ps.setString(1, MaGiangVien);
    ps.setString(2, HoTen);
    ps.setDate(3, NgaySinh != null ? new java.sql.Date(NgaySinh.getTime()) : null);
    ps.setString(4, GioiTinh);
    ps.setString(5, SoDienThoai);
    ps.setString(6, Email);
    ps.setString(7, ChuyenMon);
    ps.setString(8, TrinhDoHocVan);
    ps.setDate(9, NgayVaoLam != null ? new java.sql.Date(NgayVaoLam.getTime()) : null);
    ps.setDouble(10, MucLuongGio);
    ps.setString(11, TrangThai);
    ps.setInt(12, ID_GiangVien);
    ps.executeUpdate();
    }
    
    public void delete(int id) throws SQLException{
    String sql = "delete from giang_vien where ID_GiangVien =?";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareCall(sql);
    ps.setInt(1, id);
    ps.executeUpdate();
    }
    public void searchByName(String keyword) throws SQLException {
    String sql = "SELECT * FROM giang_vien WHERE HoTen LIKE ? ORDER BY ID_GiangVien ASC";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, "%" + keyword.trim() + "%");
    CurrentRS = ps.executeQuery();
}

public void searchByCode(String keyword) throws SQLException {
    String sql = "SELECT * FROM giang_vien WHERE MaGiangVien LIKE ? ORDER BY ID_GiangVien ASC";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, "%" + keyword.trim() + "%");
    CurrentRS = ps.executeQuery();
}

public void searchByNameAndCode(String keyword) throws SQLException {
    String sql = """
        SELECT * FROM giang_vien
        WHERE HoTen LIKE ? or MaGiangVien LIKE ?
        ORDER BY ID_GiangVien ASC
    """;
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(sql);
    String val = "%" + keyword.trim() + "%";
    ps.setString(1, val);
    ps.setString(2, val);
    CurrentRS = ps.executeQuery();
}

public ResultSet getAllDangHoatDong() throws SQLException {
    String sql = """
        SELECT 
            ID_GiangVien,
            MaGiangVien,
            HoTen,
            GioiTinh,
            SoDienThoai,
            Email
        FROM giang_vien
        WHERE TrangThai = 'Đang làm'
    """;

    Connection conn = DatabaseConnection.getConnection();
    Statement st = conn.createStatement();
    return st.executeQuery(sql);
}

}
    
