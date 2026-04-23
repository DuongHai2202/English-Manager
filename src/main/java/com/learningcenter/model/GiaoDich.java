package com.learningcenter.model;

import java.sql.Date;

public class GiaoDich {
    private int idGiaoDich;
    private int idHocVien;
    private String loaiGiaoDich;
    private double soTien;
    private String phuongThucThanhToan;
    private Date ngayGiaoDich;
    private String trangThai;
    private String noiDung;
    private String tenHocVien; // Thêm tên học viên để hiển thị

    public GiaoDich() {
    }

    public String getTenHocVien() {
        return tenHocVien;
    }

    public void setTenHocVien(String tenHocVien) {
        this.tenHocVien = tenHocVien;
    }

    public GiaoDich(int idGiaoDich, int idHocVien, String loaiGiaoDich, double soTien, String phuongThucThanhToan,
            Date ngayGiaoDich, String trangThai, String noiDung) {
        this.idGiaoDich = idGiaoDich;
        this.idHocVien = idHocVien;
        this.loaiGiaoDich = loaiGiaoDich;
        this.soTien = soTien;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.ngayGiaoDich = ngayGiaoDich;
        this.trangThai = trangThai;
        this.noiDung = noiDung;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getIdGiaoDich() {
        return idGiaoDich;
    }

    public void setIdGiaoDich(int idGiaoDich) {
        this.idGiaoDich = idGiaoDich;
    }

    public int getIdHocVien() {
        return idHocVien;
    }

    public void setIdHocVien(int idHocVien) {
        this.idHocVien = idHocVien;
    }

    public String getLoaiGiaoDich() {
        return loaiGiaoDich;
    }

    public void setLoaiGiaoDich(String loaiGiaoDich) {
        this.loaiGiaoDich = loaiGiaoDich;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public Date getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(Date ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
