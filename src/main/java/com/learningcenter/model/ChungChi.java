/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.model;

import java.sql.Timestamp;

/**
 *
 * @author admin
 */
public class ChungChi {
    private int ID_ChungChi;
    private String loaiChungChi;
    private String tenChungChi;
    private String moTaNgan;
    private Timestamp ngayTao;

    // Constructor không đối số
    public ChungChi() {
    }

    // Constructor đầy đủ đối số
    public ChungChi(int ID_ChungChi, String loaiChungChi, String tenChungChi, String moTaNgan, Timestamp ngayTao) {
        this.ID_ChungChi = ID_ChungChi;
        this.loaiChungChi = loaiChungChi;
        this.tenChungChi = tenChungChi;
        this.moTaNgan = moTaNgan;
        this.ngayTao = ngayTao;
    }

    // Getter và Setter
    public int getID_ChungChi() {
        return ID_ChungChi;
    }

    public void setID_ChungChi(int ID_ChungChi) {
        this.ID_ChungChi = ID_ChungChi;
    }

    public String getLoaiChungChi() {
        return loaiChungChi;
    }

    public void setLoaiChungChi(String loaiChungChi) {
        this.loaiChungChi = loaiChungChi;
    }

    public String getTenChungChi() {
        return tenChungChi;
    }

    public void setTenChungChi(String tenChungChi) {
        this.tenChungChi = tenChungChi;
    }

    public String getMoTaNgan() {
        return moTaNgan;
    }

    public void setMoTaNgan(String moTaNgan) {
        this.moTaNgan = moTaNgan;
    }

    public Timestamp getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Timestamp ngayTao) {
        this.ngayTao = ngayTao;
    }

    // Ghi đè phương thức toString để hỗ trợ debug/hiển thị nhanh
    @Override
    public String toString() {
        return "ChungChi{" + "ID_ChungChi=" + ID_ChungChi + ", loaiChungChi=" + loaiChungChi + ", tenChungChi="
                + tenChungChi + '}';
    }
}