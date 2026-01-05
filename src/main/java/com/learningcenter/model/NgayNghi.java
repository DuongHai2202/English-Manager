/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.model;

import java.util.Date;

/**
 *
 * @author admin
 */
public class NgayNghi {
    private int ID_NgayNghi;
    private String tenNgayNghi;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String ghiChu;

    
    public NgayNghi() {
    }

    
    public NgayNghi(int ID_NgayNghi, String tenNgayNghi, Date ngayBatDau, Date ngayKetThuc, String ghiChu) {
        this.ID_NgayNghi = ID_NgayNghi;
        this.tenNgayNghi = tenNgayNghi;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.ghiChu = ghiChu;
    }

   
    public int getID_NgayNghi() {
        return ID_NgayNghi;
    }

    public void setID_NgayNghi(int ID_NgayNghi) {
        this.ID_NgayNghi = ID_NgayNghi;
    }

    public String getTenNgayNghi() {
        return tenNgayNghi;
    }

    public void setTenNgayNghi(String tenNgayNghi) {
        this.tenNgayNghi = tenNgayNghi;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}