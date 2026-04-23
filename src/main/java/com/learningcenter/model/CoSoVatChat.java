package com.learningcenter.model;

import java.util.Date;

public class CoSoVatChat {
    private int idCoSoVatChat;
    private String tenCoSoVatChat;
    private String loaiCoSoVatChat;
    private int soLuong;
    private String donViTinh;
    private String tinhTrang;
    private Date ngayMua;
    private String ghiChu;

    public CoSoVatChat() {
    }

    public int getIdCoSoVatChat() {
        return idCoSoVatChat;
    }

    public void setIdCoSoVatChat(int idCoSoVatChat) {
        this.idCoSoVatChat = idCoSoVatChat;
    }

    public String getTenCoSoVatChat() {
        return tenCoSoVatChat;
    }

    public void setTenCoSoVatChat(String tenCoSoVatChat) {
        this.tenCoSoVatChat = tenCoSoVatChat;
    }

    public String getLoaiCoSoVatChat() {
        return loaiCoSoVatChat;
    }

    public void setLoaiCoSoVatChat(String loaiCoSoVatChat) {
        this.loaiCoSoVatChat = loaiCoSoVatChat;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
