/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.model;

/**
 *
 * @author Admin
 */
public class KhoaHoc {
    private int ID_KhoaHoc;
    private String MaKhoaHoc;
    private String TenKhoaHoc;
    private String MoTa;
    private String TrinhDo;
    private int ThoiLuong;
    private double HocPhi;
    private String TrangThai;

    public KhoaHoc(int ID_KhoaHoc, String MaKhoaHoc, String TenKhoaHoc, String MoTa, String TrinhDo, int ThoiLuong, double HocPhi, String TrangThai) {
        this.ID_KhoaHoc = ID_KhoaHoc;
        this.MaKhoaHoc = MaKhoaHoc;
        this.TenKhoaHoc = TenKhoaHoc;
        this.MoTa = MoTa;
        this.TrinhDo = TrinhDo;
        this.ThoiLuong = ThoiLuong;
        this.HocPhi = HocPhi;
        this.TrangThai = TrangThai;
    }

    public int getID_KhoaHoc() {
        return ID_KhoaHoc;
    }

    public void setID_KhoaHoc(int ID_KhoaHoc) {
        this.ID_KhoaHoc = ID_KhoaHoc;
    }

    public String getMaKhoaHoc() {
        return MaKhoaHoc;
    }

    public void setMaKhoaHoc(String MaKhoaHoc) {
        this.MaKhoaHoc = MaKhoaHoc;
    }

    public String getTenKhoaHoc() {
        return TenKhoaHoc;
    }

    public void setTenKhoaHoc(String TenKhoaHoc) {
        this.TenKhoaHoc = TenKhoaHoc;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public String getTrinhDo() {
        return TrinhDo;
    }

    public void setTrinhDo(String TrinhDo) {
        this.TrinhDo = TrinhDo;
    }

    public int getThoiLuong() {
        return ThoiLuong;
    }

    public void setThoiLuong(int ThoiLuong) {
        this.ThoiLuong = ThoiLuong;
    }

    public double getHocPhi() {
        return HocPhi;
    }

    public void setHocPhi(double HocPhi) {
        this.HocPhi = HocPhi;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }
    
    @Override
    public String toString() {
        return TenKhoaHoc; // JComboBox hiển thị tên khoá học
    } 
}
