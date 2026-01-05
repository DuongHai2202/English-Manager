/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learningcenter.model;

/**
 *
 * @author admin
 */
public class PhongHoc {
    private int id;
    private String tenPhong;
    private int sucChua;
    private int tang;
    private String trangThai;

   
    public PhongHoc() {
    }

    
    public PhongHoc(int id, String tenPhong, int sucChua, int tang, String trangThai) {
        this.id = id;
        this.tenPhong = tenPhong;
        this.sucChua = sucChua;
        this.tang = tang;
        this.trangThai = trangThai;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public int getSucChua() {
        return sucChua;
    }

    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
    }

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    
}