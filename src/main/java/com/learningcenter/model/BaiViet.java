package com.learningcenter.model;

import java.util.Date;

public class BaiViet {

    private int idBaiViet;
    private String tieuDe;
    private String noiDung;
    private String loaiBaiViet;
    private String trangThai;
    private Date ngayDang;

    public BaiViet() {
    }

    // Getters & Setters
    public int getIdBaiViet() {
        return idBaiViet;
    }

    public void setIdBaiViet(int idBaiViet) {
        this.idBaiViet = idBaiViet;
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

    public String getLoaiBaiViet() {
        return loaiBaiViet;
    }

    public void setLoaiBaiViet(String loaiBaiViet) {
        this.loaiBaiViet = loaiBaiViet;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(Date ngayDang) {
        this.ngayDang = ngayDang;
    }
}
