/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.dao;

import com.learningcenter.util.DatabaseConnection;
import java.sql.*;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class NhanVienDAO {
    private ResultSet currentRS;
    public ResultSet getCurrentRS(){
    return currentRS;
    }
    public void getAll() throws SQLException{
    String sql = "select * from nhan_vien order by  ID_NhanVien ASC";
    Connection conn = DatabaseConnection.getConnection();
    Statement st = conn.createStatement();
    currentRS = st.executeQuery(sql);        
    }
    
    public void insert(String MaNhanVien, String HoTen, Date NgaySinh, String GioiTinh,String SoDienThoai, String Email, String DiaChi, 
            String ChucVu,Date NgayVaoLam, Double Luong, String TrangThai) throws SQLException{
    String sql = "insert into nhan_vien(MaNhanVien, HoTen, NgaySinh, GioiTinh, SoDienThoai, Email, DiaChi, ChucVu"
            + ", NgayVaoLam, Luong, TrangThai) values(?,?,?,?,?,?,?,?,?,?,?)";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareCall(sql);
    ps.setString(1, MaNhanVien);
    ps.setString(2, HoTen);
    ps.setDate(3, NgaySinh != null ? new java.sql.Date(NgaySinh.getTime()) : null);
    ps.setString(4, GioiTinh);
    ps.setString(5, SoDienThoai);
    ps.setString(6, Email);
    ps.setString(7, DiaChi);
    ps.setString(8, ChucVu);
    ps.setDate(9, NgayVaoLam != null ? new java.sql.Date(NgayVaoLam.getTime()) : null);
    ps.setDouble(10, Luong);
    ps.setString(11, TrangThai);
    ps.executeUpdate();
    }
    
    public void update(String MaNhanVien, String HoTen, Date NgaySinh, String GioiTinh,String SoDienThoai, String Email, String DiaChi, 
            String ChucVu,Date NgayVaoLam, Double Luong, String TrangThai, int ID_NhanVien) throws SQLException{
    String sql = "update nhan_vien set MaNhanVien=?, HoTen =?, NgaySinh=?, GioiTinh=?, SoDienThoai =?, Email=?, "
            + "DiaChi=?, ChucVu=?, NgayVaoLam=?, Luong=?, TrangThai=? where ID_NhanVien=?";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareCall(sql);
    ps.setString(1, MaNhanVien);
    ps.setString(2, HoTen);
    ps.setDate(3, NgaySinh != null ? new java.sql.Date(NgaySinh.getTime()) : null);
    ps.setString(4, GioiTinh);
    ps.setString(5, SoDienThoai);
    ps.setString(6, Email);
    ps.setString(7, DiaChi);
    ps.setString(8, ChucVu);
    ps.setDate(9, NgayVaoLam != null ? new java.sql.Date(NgayVaoLam.getTime()) : null);
    ps.setDouble(10, Luong);
    ps.setString(11, TrangThai);
    ps.setInt(12, ID_NhanVien);
    ps.executeUpdate();
    }
    
    public void delete(int id) throws SQLException{
    String sql = "delete from nhan_vien where ID_NhanVien =?";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareCall(sql);
    ps.setInt(1, id);
    ps.executeUpdate();
    }
	
public void searchByName(String keyword) throws SQLException {
    String sql = "SELECT * FROM nhan_vien WHERE HoTen LIKE ? ORDER BY ID_NhanVien ASC";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, "%" + keyword.trim() + "%");
    currentRS = ps.executeQuery();
}

public void searchByCode(String keyword) throws SQLException {
    String sql = "SELECT * FROM nhan_vien WHERE MaNhanVien LIKE ? ORDER BY ID_NhanVien ASC";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, "%" + keyword.trim() + "%");
    currentRS = ps.executeQuery();
}

public void searchByNameAndCode(String keyword) throws SQLException {
    String sql = """
        SELECT * FROM nhan_vien
        WHERE HoTen LIKE ? or MaNhanVien LIKE ?
        ORDER BY ID_NhanVien ASC
    """;
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareStatement(sql);
    String val = "%" + keyword.trim() + "%";
    ps.setString(1, val);
    ps.setString(2, val);
    currentRS = ps.executeQuery();
}
}
