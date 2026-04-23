/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class GiangVien {
    private int ID_GiangVien;
    private String MaGiangVien;
    private String HoTen;
    private Date NgaySinh;
    private String GioiTinh;
    private String SoDienThoai;
    private String Email;
    private String ChuyenMon;
    private String TrinhDoHocVan;
    private Date NgayVaoLam;
    private Double MucLuongGio;
    private String TrangThai;

    public GiangVien(int ID_GiangVien, String MaGiangVien, String HoTen, 
            Date NgaySinh, String GioiTinh, String SoDienThoai, String Email, 
            String ChuyenMon, String TrinhDoHocVan, Date NgayVaoLam, Double MucLuongGio, String TrangThai) {
        this.ID_GiangVien = ID_GiangVien;
        this.MaGiangVien = MaGiangVien;
        this.HoTen = HoTen;
        this.NgaySinh = NgaySinh;
        this.GioiTinh = GioiTinh;
        this.SoDienThoai = SoDienThoai;
        this.Email = Email;
        this.ChuyenMon = ChuyenMon;
        this.TrinhDoHocVan = TrinhDoHocVan;
        this.NgayVaoLam = NgayVaoLam;
        this.MucLuongGio = MucLuongGio;
        this.TrangThai = TrangThai;
    }

    public int getID_GiangVien() {
        return ID_GiangVien;
    }

    public void setID_GiangVien(int ID_GiangVien) {
        this.ID_GiangVien = ID_GiangVien;
    }

    public String getMaGiangVien() {
        return MaGiangVien;
    }

    public void setMaGiangVien(String MaGiangVien) {
        this.MaGiangVien = MaGiangVien;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getChuyenMon() {
        return ChuyenMon;
    }

    public void setChuyenMon(String ChuyenMon) {
        this.ChuyenMon = ChuyenMon;
    }

    public String getTrinhDoHocVan() {
        return TrinhDoHocVan;
    }

    public void setTrinhDoHocVan(String TrinhDoHocVan) {
        this.TrinhDoHocVan = TrinhDoHocVan;
    }

    public Date getNgayVaoLam() {
        return NgayVaoLam;
    }

    public void setNgayVaoLam(Date NgayVaoLam) {
        this.NgayVaoLam = NgayVaoLam;
    }

    public Double getMucLuongGio() {
        return MucLuongGio;
    }

    public void setMucLuongGio(Double MucLuongGio) {
        this.MucLuongGio = MucLuongGio;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }
}
