package com.learningcenter.model;

import java.util.Date;

public class DiemDanh {
    private int idDiemDanh;
    private int idDangKy; // THỊNH: Liên kết với bảng DANG_KY
    private int idHocVien;
    private int idLopHoc;
    private Date ngayDiemDanh;
    private String trangThai;
    private String ghiChu;

    private String maHocVien;
    private String tenHocVien;
    private String maLopHoc;
    private String tenLopHoc;

    public DiemDanh() {
    }

    // Getters and Setters
    public int getIdDiemDanh() {
        return idDiemDanh;
    }

    public void setIdDiemDanh(int idDiemDanh) {
        this.idDiemDanh = idDiemDanh;
    }

    public int getIdDangKy() {
        return idDangKy;
    }

    public void setIdDangKy(int idDangKy) {
        this.idDangKy = idDangKy;
    }

    public int getIdHocVien() {
        return idHocVien;
    }

    public void setIdHocVien(int idHocVien) {
        this.idHocVien = idHocVien;
    }

    public int getIdLopHoc() {
        return idLopHoc;
    }

    public void setIdLopHoc(int idLopHoc) {
        this.idLopHoc = idLopHoc;
    }

    public Date getNgayDiemDanh() {
        return ngayDiemDanh;
    }

    public void setNgayDiemDanh(Date ngayDiemDanh) {
        this.ngayDiemDanh = ngayDiemDanh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaHocVien() {
        return maHocVien;
    }

    public void setMaHocVien(String maHocVien) {
        this.maHocVien = maHocVien;
    }

    public String getTenHocVien() {
        return tenHocVien;
    }

    public void setTenHocVien(String tenHocVien) {
        this.tenHocVien = tenHocVien;
    }

    public String getMaLopHoc() {
        return maLopHoc;
    }

    public void setMaLopHoc(String maLopHoc) {
        this.maLopHoc = maLopHoc;
    }

    public String getTenLopHoc() {
        return tenLopHoc;
    }

    public void setTenLopHoc(String tenLopHoc) {
        this.tenLopHoc = tenLopHoc;
    }
}
