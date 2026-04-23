package com.learningcenter.controller;

import com.learningcenter.dao.ThongKeDAO;
import com.learningcenter.view.panel.DashboardPanel;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

public class DashboardController {
    private static DashboardController instance;

    public static void refreshData() {
        if (instance != null) {
            instance.loadData();
        }
    }

    private DashboardPanel view;
    private ThongKeDAO dao;

    public DashboardController(DashboardPanel view) {
        this.view = view;
        this.dao = new ThongKeDAO();

        instance = this;
        loadData();
        initListeners();
    }

    private void initListeners() {
        // No buttons to listen to now
    }

    public void loadData() {
        // 1. Lấy thống kê tổng quan (Cards)
        Map<String, Object> tongQuan = dao.getTongQuan();
        int tongHocVien = (int) tongQuan.getOrDefault("TongHocVien", 0);
        int tongGD = (int) tongQuan.getOrDefault("TongGiaoDich", 0);
        double tongDoanhThu = (double) tongQuan.getOrDefault("TongDoanhThu", 0.0);

        view.updateStats(tongHocVien, tongGD, tongDoanhThu);

        // 2. Lấy dữ liệu biểu đồ
        Map<String, Double> revenueData = dao.getDoanhThuTheoThang();
        Map<String, Double> csvcData = dao.getCSVCTheoTinhTrang();

        view.setCharts(revenueData, csvcData);

        // 3. Lấy giao dịch gần nhất
        loadRecentTransactions();
    }

    private void loadRecentTransactions() {
        DefaultTableModel model = view.getTableModelRecent();
        model.setRowCount(0);
        java.util.List<Map<String, Object>> list = dao.getGiaoDichGanNhat();
        for (Map<String, Object> map : list) {
            model.addRow(new Object[] {
                    map.get("MaGiaoDich"),
                    map.get("HoTen"),
                    map.get("LoaiGiaoDich"),
                    String.format("%,.0f", (Double) map.get("SoTien")),
                    map.get("NgayGiaoDich"),
                    map.get("TrangThai")
            });
        }
    }
}
