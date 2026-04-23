package com.learningcenter.view.component;

import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class BarChartCustom extends JPanel {
    private Map<String, Double> data;
    private String title;

    public BarChartCustom(String title, Map<String, Double> data) {
        this.title = title;
        this.data = data;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 300));
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data == null || data.isEmpty())
            return;

        Graphics2D g2 = (Graphics2D) g;
        // Khử răng cưa cho nét vẽ mượt hơn
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int padding = 50;
        int labelPadding = 20;

        // Tính giá trị lớn nhất để căn tỉ lệ
        double maxValue = 0;
        for (double v : data.values()) {
            if (v > maxValue)
                maxValue = v;
        }

        // Vẽ hệ trục tọa độ
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawLine(padding, height - padding, width - padding, height - padding); // Trục X
        g2.drawLine(padding, padding, padding, height - padding); // Trục Y

        // Tính toán kích thước mỗi cột
        int barWidth = (width - 2 * padding) / data.size() - 20;
        int maxBarWidth = 60; // Giới hạn độ rộng tối đa của cột
        if (barWidth > maxBarWidth)
            barWidth = maxBarWidth;

        int totalWidth = data.size() * (barWidth + 20);
        int x = (width - totalWidth) / 2 + 10; // Căn giữa biểu đồ

        // Vẽ từng cột dữ liệu
        int i = 0;
        Color[] colors = { new Color(74, 175, 110), new Color(0, 188, 212), new Color(255, 152, 0),
                new Color(156, 39, 176) };

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            double value = entry.getValue();
            int barHeight = (int) ((value / maxValue) * (height - 2 * padding));

            // Vẽ cột (Bar) với màu sắc xoay vòng
            g2.setColor(colors[i % colors.length]);
            g2.fillRect(x, height - padding - barHeight, barWidth, barHeight);

            // Vẽ viền cột
            g2.setColor(g2.getColor().darker());
            g2.drawRect(x, height - padding - barHeight, barWidth, barHeight);

            // Vẽ nhãn bên dưới (Tên tháng/loại)
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            String key = entry.getKey();
            FontMetrics metrics = g2.getFontMetrics();
            int labelWidth = metrics.stringWidth(key);
            g2.drawString(key, x + (barWidth - labelWidth) / 2, height - padding + labelPadding);

            // Vẽ con số trên đỉnh cột
            String valStr = String.format("%.0f", value);
            int valWidth = metrics.stringWidth(valStr);
            g2.drawString(valStr, x + (barWidth - valWidth) / 2, height - padding - barHeight - 5);

            x += barWidth + 20;
            i++;
        }

        // Vẽ tiêu đề biểu đồ
        g2.setColor(Color.DARK_GRAY);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2.drawString(title, (width - g2.getFontMetrics().stringWidth(title)) / 2, padding - 10);
    }
}
