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
public class KhoaHocDAO {
    public ResultSet getAll() throws SQLException{
    String sql = "select * from khoa_hoc order by  ID_KhoaHoc ASC";
    Connection conn = DatabaseConnection.getConnection();
    Statement st = conn.createStatement();
    return st.executeQuery(sql);        
    }
    
    public void insert(String MaKhoaHoc, String TenKhoaHoc, String MoTa, String TrinhDo, int ThoiLuong, double HocPhi,String TrangThai) throws SQLException{
    String sql = "insert into khoa_hoc(MaKhoaHoc, TenKhoaHoc, MoTa, TrinhDo, ThoiLuong, HocPhi, TrangThai) values(?,?,?,?,?,?,?)";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareCall(sql);
    ps.setString(1, MaKhoaHoc);
    ps.setString(2, TenKhoaHoc);
    ps.setString(3, MoTa);
    ps.setString(4, TrinhDo);
    ps.setInt(5, ThoiLuong);
    ps.setDouble(6, HocPhi);
    ps.setString(7, TrangThai);
    ps.executeUpdate();
    }
    
    public void update(String MaKhoaHoc, String TenKhoaHoc, String MoTa, String TrinhDo,int ThoiLuong, double HocPhi, String TrangThai, int ID_KhoaHoc) throws SQLException{
    String sql = "update khoa_hoc set MaKhoaHoc=?, TenKhoaHoc =?, MoTa=?, TrinhDo=?, ThoiLuong =?, HocPhi=?, "
            + "TrangThai=? where ID_KhoaHoc=?";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareCall(sql);
    ps.setString(1, MaKhoaHoc);
    ps.setString(2, TenKhoaHoc);
    ps.setString(3, MoTa); 
    ps.setString(4, TrinhDo);
    ps.setInt(5, ThoiLuong);
    ps.setDouble(6, HocPhi);
    ps.setString(7, TrangThai);
    ps.setInt(8, ID_KhoaHoc);

    ps.executeUpdate();
    }
    
    public void delete(int id) throws SQLException{
    String sql = "delete from khoa_hoc where ID_KhoaHoc =?";
    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement ps = conn.prepareCall(sql);
    ps.setInt(1, id);
    ps.executeUpdate();
    }
    public ResultSet getAllForComboBox() throws SQLException {
    String sql = "SELECT ID_KhoaHoc, TenKhoaHoc FROM khoa_hoc ORDER BY TenKhoaHoc ASC";
    Connection conn = DatabaseConnection.getConnection();
    Statement st = conn.createStatement();
    return st.executeQuery(sql);
}

}
