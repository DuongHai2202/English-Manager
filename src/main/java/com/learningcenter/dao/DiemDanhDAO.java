package com.learningcenter.dao;

import com.learningcenter.model.DiemDanh;
import com.learningcenter.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiemDanhDAO {

    public List<DiemDanh> getAll() {
        return search("", "Tất cả");
    }

    public List<DiemDanh> search(String keyword, String criteria) {
        List<DiemDanh> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT DD.*, HV.HoTen as TenHocVien, HV.MaHocVien, LH.TenLopHoc, LH.MaLopHoc, DK.ID_HocVien, DK.ID_LopHoc "
                        +
                        "FROM DIEM_DANH DD " +
                        "JOIN DANG_KY DK ON DD.ID_DangKy = DK.ID_DangKy " +
                        "JOIN HOC_VIEN HV ON DK.ID_HocVien = HV.ID_HocVien " +
                        "JOIN LOP_HOC LH ON DK.ID_LopHoc = LH.ID_LopHoc " +
                        "WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            if ("Theo tên học viên".equals(criteria)) {
                sql.append(" AND HV.HoTen LIKE ?");
            } else if ("Theo lớp học".equals(criteria)) {
                sql.append(" AND LH.TenLopHoc LIKE ?");
            } else if ("Theo trạng thái".equals(criteria)) {
                sql.append(" AND DD.TrangThai LIKE ?");
            } else { // Tất cả
                sql.append(" AND (HV.HoTen LIKE ? OR LH.TenLopHoc LIKE ? OR DD.TrangThai LIKE ?)");
            }
        }

        sql.append(" ORDER BY DD.NgayDiemDanh DESC, DD.ID_DiemDanh DESC");

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql.toString())) {

            if (keyword != null && !keyword.trim().isEmpty()) {
                String val = "%" + keyword.trim() + "%";
                if ("Tất cả".equals(criteria)) {
                    ps.setString(1, val);
                    ps.setString(2, val);
                    ps.setString(3, val);
                } else {
                    ps.setString(1, val);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToDiemDanh(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DiemDanh> getByLopHoc(int idLopHoc) {
        List<DiemDanh> list = new ArrayList<>();
        String sql = "SELECT DD.*, HV.HoTen as TenHocVien, HV.MaHocVien, LH.TenLopHoc, LH.MaLopHoc, DK.ID_HocVien, DK.ID_LopHoc "
                +
                "FROM DIEM_DANH DD " +
                "JOIN DANG_KY DK ON DD.ID_DangKy = DK.ID_DangKy " +
                "JOIN HOC_VIEN HV ON DK.ID_HocVien = HV.ID_HocVien " +
                "JOIN LOP_HOC LH ON DK.ID_LopHoc = LH.ID_LopHoc " +
                "WHERE DK.ID_LopHoc = ? " +
                "ORDER BY DD.NgayDiemDanh DESC";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idLopHoc);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToDiemDanh(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DiemDanh> getByDate(java.util.Date date) {
        List<DiemDanh> list = new ArrayList<>();
        String sql = "SELECT DD.*, HV.HoTen as TenHocVien, HV.MaHocVien, LH.TenLopHoc, LH.MaLopHoc, DK.ID_HocVien, DK.ID_LopHoc "
                +
                "FROM DIEM_DANH DD " +
                "JOIN DANG_KY DK ON DD.ID_DangKy = DK.ID_DangKy " +
                "JOIN HOC_VIEN HV ON DK.ID_HocVien = HV.ID_HocVien " +
                "JOIN LOP_HOC LH ON DK.ID_LopHoc = LH.ID_LopHoc " +
                "WHERE DATE(DD.NgayDiemDanh) = ? " +
                "ORDER BY LH.TenLopHoc, HV.HoTen";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(date.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToDiemDanh(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(DiemDanh dd) {
        // THỊNH: Tìm ID_DangKy dựa trên ID_HocVien và ID_LopHoc
        int idDangKy = getRegistrationId(dd.getIdHocVien(), dd.getIdLopHoc());
        if (idDangKy == -1) {
            System.err.println("Không tìm thấy thông tin đăng ký lớp học cho học viên này!");
            return false;
        }

        String sql = "INSERT INTO DIEM_DANH (ID_DangKy, NgayDiemDanh, TrangThai, GhiChu) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDangKy);
            ps.setDate(2, dd.getNgayDiemDanh() != null ? new java.sql.Date(dd.getNgayDiemDanh().getTime()) : null);
            ps.setString(3, dd.getTrangThai());
            ps.setString(4, dd.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(DiemDanh dd) {
        // THỊNH: Cập nhật dựa trên ID_DangKy nếu cần, nhưng ID_DiemDanh là trung tâm
        String sql = "UPDATE DIEM_DANH SET NgayDiemDanh=?, TrangThai=?, GhiChu=? " +
                "WHERE ID_DiemDanh=?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, dd.getNgayDiemDanh() != null ? new java.sql.Date(dd.getNgayDiemDanh().getTime()) : null);
            ps.setString(2, dd.getTrangThai());
            ps.setString(3, dd.getGhiChu());
            ps.setInt(4, dd.getIdDiemDanh());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getRegistrationId(int idHocVien, int idLopHoc) {
        String sql = "SELECT ID_DangKy FROM DANG_KY WHERE ID_HocVien = ? AND ID_LopHoc = ?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idHocVien);
            ps.setInt(2, idLopHoc);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_DangKy");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM DIEM_DANH WHERE ID_DiemDanh=?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkExists(int idHocVien, int idLopHoc, java.util.Date ngayDiemDanh) {
        // THỊNH: Kiểm tra sự tồn tại dựa trên ID_DangKy
        int idDangKy = getRegistrationId(idHocVien, idLopHoc);
        if (idDangKy == -1)
            return false;

        String sql = "SELECT COUNT(*) FROM DIEM_DANH " +
                "WHERE ID_DangKy=? AND DATE(NgayDiemDanh)=?";
        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDangKy);
            ps.setDate(2, new java.sql.Date(ngayDiemDanh.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private DiemDanh mapResultSetToDiemDanh(ResultSet rs) throws SQLException {
        DiemDanh dd = new DiemDanh();
        dd.setIdDiemDanh(rs.getInt("ID_DiemDanh"));
        dd.setIdDangKy(rs.getInt("ID_DangKy")); // THỊNH
        dd.setIdHocVien(rs.getInt("ID_HocVien"));
        dd.setIdLopHoc(rs.getInt("ID_LopHoc"));
        dd.setNgayDiemDanh(rs.getDate("NgayDiemDanh"));
        dd.setTrangThai(rs.getString("TrangThai"));
        dd.setGhiChu(rs.getString("GhiChu"));
        dd.setTenHocVien(rs.getString("TenHocVien"));
        dd.setMaHocVien(rs.getString("MaHocVien"));
        dd.setTenLopHoc(rs.getString("TenLopHoc"));
        dd.setMaLopHoc(rs.getString("MaLopHoc"));
        return dd;
    }
}