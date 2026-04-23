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
public class LopHoc {
    private int ID_LopHoc;
    private int ID_KhoaHoc;
    private String MaLopHoc;
    private String TenLopHoc;
    private int SiSoToiDa;
    private Date NgayBatDau;
    private Date NgayKetThuc;
    private String PhongHoc;
    private String TrangThai;

    public LopHoc(int ID_LopHoc, int ID_KhoaHoc, String MaLopHoc, String TenLopHoc, int SiSoToiDa, Date NgayBatDau,
            Date NgayKetThuc, String PhongHoc, String TrangThai) {
        this.ID_LopHoc = ID_LopHoc;
        this.ID_KhoaHoc = ID_KhoaHoc;
        this.MaLopHoc = MaLopHoc;
        this.TenLopHoc = TenLopHoc;
        this.SiSoToiDa = SiSoToiDa;
        this.NgayBatDau = NgayBatDau;
        this.NgayKetThuc = NgayKetThuc;
        this.PhongHoc = PhongHoc;
        this.TrangThai = TrangThai;
    }

    public int getID_LopHoc() {
        return ID_LopHoc;
    }

    public void setID_LopHoc(int ID_LopHoc) {
        this.ID_LopHoc = ID_LopHoc;
    }

    public int getID_KhoaHoc() {
        return ID_KhoaHoc;
    }

    public void setID_KhoaHoc(int ID_KhoaHoc) {
        this.ID_KhoaHoc = ID_KhoaHoc;
    }

    public String getMaLopHoc() {
        return MaLopHoc;
    }

    public void setMaLopHoc(String MaLopHoc) {
        this.MaLopHoc = MaLopHoc;
    }

    public String getTenLopHoc() {
        return TenLopHoc;
    }

    public void setTenLopHoc(String TenLopHoc) {
        this.TenLopHoc = TenLopHoc;
    }

    public int getSiSoToiDa() {
        return SiSoToiDa;
    }

    public void setSiSoToiDa(int SiSoToiDa) {
        this.SiSoToiDa = SiSoToiDa;
    }

    public Date getNgayBatDau() {
        return NgayBatDau;
    }

    public void setNgayBatDau(Date NgayBatDau) {
        this.NgayBatDau = NgayBatDau;
    }

    public Date getNgayKetThuc() {
        return NgayKetThuc;
    }

    public void setNgayKetThuc(Date NgayKetThuc) {
        this.NgayKetThuc = NgayKetThuc;
    }

    public String getPhongHoc() {
        return PhongHoc;
    }

    public void setPhongHoc(String PhongHoc) {
        this.PhongHoc = PhongHoc;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    @Override
    public String toString() {
        return MaLopHoc + " - " + TenLopHoc;
    }
}
