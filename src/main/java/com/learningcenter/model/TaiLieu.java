package com.learningcenter.model;

public class TaiLieu {

    private int idTaiLieu;
    private int idKhoaHoc;
    private String tieuDe;
    private String moTa;
    private String tenFile;
    private String duongDanFile;

    public TaiLieu() {
    }

    /* ===== GETTERS & SETTERS ===== */

    public int getIdTaiLieu() {
        return idTaiLieu;
    }

    public void setIdTaiLieu(int idTaiLieu) {
        this.idTaiLieu = idTaiLieu;
    }

    public int getIdKhoaHoc() {
        return idKhoaHoc;
    }

    public void setIdKhoaHoc(int idKhoaHoc) {
        this.idKhoaHoc = idKhoaHoc;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTenFile() {
        return tenFile;
    }

    public void setTenFile(String tenFile) {
        this.tenFile = tenFile;
    }

    public String getDuongDanFile() {
        return duongDanFile;
    }

    public void setDuongDanFile(String duongDanFile) {
        this.duongDanFile = duongDanFile;
    }
}
