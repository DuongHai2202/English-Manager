package com.learningcenter.model;

import java.util.Date;

public class HoTro {

    private int idHoTro;
    private String tenNguoiGui;
    private String emailNguoiGui;
    private String tieuDe;
    private String noiDung;
    private String chuyenMuc;
    private String doUuTien;
    private String trangThai;
    private Date ngayTao;

    public HoTro() {
    }

    public int getIdHoTro() {
        return idHoTro;
    }

    public void setIdHoTro(int idHoTro) {
        this.idHoTro = idHoTro;
    }

    public String getTenNguoiGui() {
        return tenNguoiGui;
    }

    public void setTenNguoiGui(String tenNguoiGui) {
        this.tenNguoiGui = tenNguoiGui;
    }

    public String getEmailNguoiGui() {
        return emailNguoiGui;
    }

    public void setEmailNguoiGui(String emailNguoiGui) {
        this.emailNguoiGui = emailNguoiGui;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getChuyenMuc() {
        return chuyenMuc;
    }

    public void setChuyenMuc(String chuyenMuc) {
        this.chuyenMuc = chuyenMuc;
    }

    public String getDoUuTien() {
        return doUuTien;
    }

    public void setDoUuTien(String doUuTien) {
        this.doUuTien = doUuTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
}
