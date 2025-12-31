-- ======================================================
-- HỆ THỐNG QUẢN LÝ TRUNG TÂM HỌC VIÊN
-- Database Schema (Phiên bản ADMIN duy nhất - Tiếng Việt)
-- ======================================================

DROP DATABASE IF EXISTS learning_center_db;
CREATE DATABASE learning_center_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE learning_center_db;

-- 1. BẢNG TAI_KHOAN
-- Mọi tài khoản đều có quyền Admin toàn cục (Không phân quyền)
CREATE TABLE TAI_KHOAN (
    ID_TaiKhoan INT AUTO_INCREMENT PRIMARY KEY,
    TenDangNhap VARCHAR(50) NOT NULL UNIQUE,
    MatKhau VARCHAR(255) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    HoTen VARCHAR(100),
    TrangThai BOOLEAN DEFAULT TRUE, -- TRUE: Hoạt động, FALSE: Bị khóa
    NgayTao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 2. BẢNG HOC_VIEN
CREATE TABLE HOC_VIEN (
    ID_HocVien INT AUTO_INCREMENT PRIMARY KEY,
    MaHocVien VARCHAR(20) NOT NULL UNIQUE,
    HoTen VARCHAR(100) NOT NULL,
    NgaySinh DATE,
    GioiTinh VARCHAR(10),
    SoDienThoai VARCHAR(20),
    Email VARCHAR(100),
    DiaChi TEXT,
    TrinhDo VARCHAR(50), -- Level học viên
    NgayNhapHoc DATE NOT NULL,
    TrangThai VARCHAR(50) DEFAULT 'Đang học',
    GhiChu TEXT
) ENGINE=InnoDB;

-- 3. BẢNG NHAN_VIEN
CREATE TABLE NHAN_VIEN (
    ID_NhanVien INT AUTO_INCREMENT PRIMARY KEY,
    MaNhanVien VARCHAR(20) NOT NULL UNIQUE,
    HoTen VARCHAR(100) NOT NULL,
    NgaySinh DATE,
    GioiTinh VARCHAR(10),
    SoDienThoai VARCHAR(20),
    Email VARCHAR(100),
    DiaChi TEXT,
    ChucVu VARCHAR(100),
    PhongBan VARCHAR(100),
    NgayVaoLam DATE NOT NULL,
    Luong DECIMAL(12, 2),
    TrangThai VARCHAR(50) DEFAULT 'Đang làm việc'
) ENGINE=InnoDB;

-- 4. BẢNG GIANG_VIEN
CREATE TABLE GIANG_VIEN (
    ID_GiangVien INT AUTO_INCREMENT PRIMARY KEY,
    MaGiangVien VARCHAR(20) NOT NULL UNIQUE,
    HoTen VARCHAR(100) NOT NULL,
    NgaySinh DATE,
    GioiTinh VARCHAR(10),
    SoDienThoai VARCHAR(20),
    Email VARCHAR(100),
    ChuyenMon VARCHAR(200),
    TrinhDoHocVan TEXT,
    NgayVaoLam DATE NOT NULL,
    MucLuongGio DECIMAL(10, 2),
    TrangThai VARCHAR(50) DEFAULT 'Đang dạy'
) ENGINE=InnoDB;

-- 5. BẢNG KHOA_HOC
CREATE TABLE KHOA_HOC (
    ID_KhoaHoc INT AUTO_INCREMENT PRIMARY KEY,
    MaKhoaHoc VARCHAR(20) NOT NULL UNIQUE,
    TenKhoaHoc VARCHAR(200) NOT NULL,
    MoTa TEXT,
    TrinhDo VARCHAR(50),
    ThoiLuong INT, -- Tổng số giờ học
    HocPhi DECIMAL(12, 2) NOT NULL,
    TrangThai VARCHAR(50) DEFAULT 'Đang mở'
) ENGINE=InnoDB;

-- 6. BẢNG LOP_HOC
CREATE TABLE LOP_HOC (
    ID_LopHoc INT AUTO_INCREMENT PRIMARY KEY,
    ID_KhoaHoc INT NOT NULL,
    ID_GiangVien INT,
    MaLopHoc VARCHAR(20) NOT NULL UNIQUE,
    TenLopHoc VARCHAR(200) NOT NULL,
    SiSoToiDa INT DEFAULT 30,
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE,
    PhongHoc VARCHAR(50),
    TrangThai VARCHAR(50) DEFAULT 'Sắp mở',
    FOREIGN KEY (ID_KhoaHoc) REFERENCES KHOA_HOC(ID_KhoaHoc) ON DELETE CASCADE,
    FOREIGN KEY (ID_GiangVien) REFERENCES GIANG_VIEN(ID_GiangVien) ON DELETE SET NULL
) ENGINE=InnoDB;

-- 7. BẢNG LICH_HOC
CREATE TABLE LICH_HOC (
    ID_LichHoc INT AUTO_INCREMENT PRIMARY KEY,
    ID_LopHoc INT NOT NULL,
    ThuTrongTuan VARCHAR(20) NOT NULL, -- Thứ 2, Thứ 3...
    GioBatDau TIME NOT NULL,
    GioKetThuc TIME NOT NULL,
    PhongHoc VARCHAR(50),
    FOREIGN KEY (ID_LopHoc) REFERENCES LOP_HOC(ID_LopHoc) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 8. BẢNG DANG_KY (Học viên vào lớp)
CREATE TABLE DANG_KY (
    ID_DangKy INT AUTO_INCREMENT PRIMARY KEY,
    ID_HocVien INT NOT NULL,
    ID_LopHoc INT NOT NULL,
    NgayDangKy DATE NOT NULL,
    TrangThai VARCHAR(50) DEFAULT 'Đã đăng ký',
    TienDo DECIMAL(5, 2) DEFAULT 0.00,
    DiemSo VARCHAR(5),
    GhiChu TEXT,
    FOREIGN KEY (ID_HocVien) REFERENCES HOC_VIEN(ID_HocVien) ON DELETE CASCADE,
    FOREIGN KEY (ID_LopHoc) REFERENCES LOP_HOC(ID_LopHoc) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 9. BẢNG DIEM_DANH
CREATE TABLE DIEM_DANH (
    ID_DiemDanh INT AUTO_INCREMENT PRIMARY KEY,
    ID_DangKy INT NOT NULL,
    NgayDiemDanh DATE NOT NULL,
    TrangThai VARCHAR(50) NOT NULL, -- Có mặt, Vắng, Muộn
    GhiChu TEXT,
    FOREIGN KEY (ID_DangKy) REFERENCES DANG_KY(ID_DangKy) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 10. BẢNG GIAO_DICH (Doanh thu)
CREATE TABLE GIAO_DICH (
    ID_GiaoDich INT AUTO_INCREMENT PRIMARY KEY,
    ID_HocVien INT NOT NULL,
    LoaiGiaoDich VARCHAR(100) NOT NULL, -- Học phí, Tài liệu...
    SoTien DECIMAL(12, 2) NOT NULL,
    PhuongThucThanhToan VARCHAR(50),
    NgayGiaoDich DATETIME NOT NULL,
    TrangThai VARCHAR(50) DEFAULT 'Hoàn thành',
    NoiDung TEXT,
    FOREIGN KEY (ID_HocVien) REFERENCES HOC_VIEN(ID_HocVien) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 11. BẢNG BAI_VIET (Tin tức/Thông báo)
CREATE TABLE BAI_VIET (
    ID_BaiViet INT AUTO_INCREMENT PRIMARY KEY,
    TieuDe VARCHAR(255) NOT NULL,
    NoiDung TEXT NOT NULL,
    LoaiBaiViet VARCHAR(50), -- Tin tức/Thông báo
    TrangThai VARCHAR(50) DEFAULT 'Đã đăng',
    NgayDang DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 12. BẢNG TAI_LIEU
CREATE TABLE TAI_LIEU (
    ID_TaiLieu INT AUTO_INCREMENT PRIMARY KEY,
    ID_KhoaHoc INT,
    TieuDe VARCHAR(255) NOT NULL,
    MoTa TEXT,
    TenFile VARCHAR(255) NOT NULL,
    DuongDanFile VARCHAR(500) NOT NULL,
    FOREIGN KEY (ID_KhoaHoc) REFERENCES KHOA_HOC(ID_KhoaHoc) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 13. BẢNG HO_TRO (Yêu cầu hỗ trợ)
CREATE TABLE HO_TRO (
    ID_HoTro INT AUTO_INCREMENT PRIMARY KEY,
    TenNguoiGui VARCHAR(100) NOT NULL,
    EmailNguoiGui VARCHAR(100),
    TieuDe VARCHAR(255) NOT NULL,
    NoiDung TEXT NOT NULL,
    ChuyenMuc VARCHAR(50),
    DoUuTien VARCHAR(20) DEFAULT 'Trung bình',
    TrangThai VARCHAR(50) DEFAULT 'Mở',
    NgayTao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 14. BẢNG PHAN_HOI_HO_TRO
CREATE TABLE PHAN_HOI_HO_TRO (
    ID_PhanHoi INT AUTO_INCREMENT PRIMARY KEY,
    ID_HoTro INT NOT NULL,
    NoiDung TEXT NOT NULL,
    NgayPhanHoi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ID_HoTro) REFERENCES HO_TRO(ID_HoTro) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ======================================================
-- DỮ LIỆU MẪU (Dành cho Admin)
-- ======================================================

-- 1. Tài khoản Admin (Mật khẩu: 123456)
INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, Email, HoTen) VALUES
('admin', '123456', 'admin@trungtam.com', 'Quản Trị Viên Hệ Thống');

-- 2. Nhân viên
INSERT INTO NHAN_VIEN (MaNhanVien, HoTen, NgayVaoLam, ChucVu, Luong) VALUES
('NV001', 'Nguyễn Thị Tuyết', '2024-01-01', 'Kế toán', 12000000),
('NV002', 'Lê Văn Nam', '2024-02-15', 'Tư vấn viên', 10000000),
('NV003', 'Hoàng Minh Châu', '2024-03-10', 'Quản lý đào tạo', 18000000),
('NV004', 'Trịnh Xuân Bình', '2024-05-20', 'Kỹ thuật', 15000000);

-- 3. Giảng viên
INSERT INTO GIANG_VIEN (MaGiangVien, HoTen, NgayVaoLam, ChuyenMon, MucLuongGio) VALUES
('GV001', 'Phạm Minh Đức', '2023-12-01', 'Tiếng Anh IELTS', 250000),
('GV002', 'Hoàng Thu Trang', '2024-01-15', 'Lập trình Java', 300000),
('GV003', 'Nguyễn Tiến Dũng', '2024-02-20', 'Tiếng Nhật N3', 200000),
('GV004', 'Lương Mỹ Linh', '2024-04-01', 'Marketing Online', 350000);

-- 4. Học viên
INSERT INTO HOC_VIEN (MaHocVien, HoTen, NgaySinh, GioiTinh, SoDienThoai, Email, NgayNhapHoc, TrinhDo) VALUES
('HV001', 'Trần Bảo An', '2005-04-12', 'Nam', '0912345678', 'an.tran@gmail.com', '2024-01-20', 'Cơ bản'),
('HV002', 'Nguyễn Minh Quân', '2006-08-22', 'Nam', '0987654321', 'quan.ng@gmail.com', '2024-02-05', 'Nâng cao'),
('HV003', 'Lê Thị Mai', '2007-01-15', 'Nữ', '0901234567', 'mai.le@gmail.com', '2024-02-15', 'Trung bình'),
('HV004', 'Phạm Hồng Phúc', '2004-11-30', 'Nam', '0934567890', 'phuc.pham@gmail.com', '2024-03-01', 'Cơ bản'),
('HV005', 'Vũ Thúy Hiền', '2005-09-10', 'Nữ', '0945678901', 'hien.vu@gmail.com', '2024-03-10', 'Nâng cao'),
('HV006', 'Đỗ Anh Tuấn', '2003-05-05', 'Nam', '0956789012', 'tuan.do@gmail.com', '2024-03-20', 'Trung bình'),
('HV007', 'Ngô Phương Thảo', '2008-02-28', 'Nữ', '0967890123', 'thao.ngo@gmail.com', '2024-04-05', 'Cơ bản'),
('HV008', 'Bùi Xuân Hùng', '2004-07-14', 'Nam', '0978901234', 'hung.bui@gmail.com', '2024-04-15', 'Nâng cao'),
('HV009', 'Lý Gia Hân', '2006-12-12', 'Nữ', '0323456789', 'han.ly@gmail.com', '2024-05-01', 'Trung bình'),
('HV010', 'Trương Quốc Bảo', '2005-03-25', 'Nam', '0334567890', 'bao.truong@gmail.com', '2024-05-10', 'Cơ bản'),
('HV011', 'Phan Minh Tuyến', '2002-10-18', 'Nam', '0345678901', 'tuyen.phan@gmail.com', '2024-05-20', 'Nâng cao'),
('HV012', 'Đặng Mỹ Duyên', '2007-06-06', 'Nữ', '0356789012', 'duyen.dang@gmail.com', '2024-06-01', 'Trung bình');

-- 5. Khóa học
INSERT INTO KHOA_HOC (MaKhoaHoc, TenKhoaHoc, HocPhi, ThoiLuong, TrinhDo) VALUES
('KH001', 'Luyện thi IELTS 6.5', 8000000, 120, 'Nâng cao'),
('KH002', 'Java Swing for Beginners', 4000000, 60, 'Cơ bản'),
('KH003', 'Tiếng Nhật Giao tiếp N3', 5500000, 80, 'Trung bình'),
('KH004', 'Digital Marketing căn bản', 3500000, 45, 'Cơ bản'),
('KH005', 'Lập trình Web ReactJS', 6000000, 90, 'Nâng cao');

-- 6. Lớp học
INSERT INTO LOP_HOC (ID_KhoaHoc, ID_GiangVien, MaLopHoc, TenLopHoc, NgayBatDau, PhongHoc, TrangThai) VALUES
(1, 1, 'L001', 'IELTS 6.5 - Tối 2.4.6', '2024-03-01', 'Phòng 201', 'Đang học'),
(2, 2, 'L002', 'Java Swing - Sáng T7.CN', '2024-03-15', 'Phòng Lab', 'Đang học'),
(3, 3, 'L003', 'Tiếng Nhật N3 - Chiều 3.5.7', '2024-04-01', 'Phòng 102', 'Đang học'),
(4, 4, 'L004', 'Marketing K01 - Tối Thứ 6', '2024-05-15', 'Phòng 303', 'Sắp mở');

-- 7. Lịch học
INSERT INTO LICH_HOC (ID_LopHoc, ThuTrongTuan, GioBatDau, GioKetThuc, PhongHoc) VALUES
(1, 'Thứ 2', '18:00:00', '20:00:00', 'Phòng 201'),
(1, 'Thứ 4', '18:00:00', '20:00:00', 'Phòng 201'),
(1, 'Thứ 6', '18:00:00', '20:00:00', 'Phòng 201'),
(2, 'Thứ 7', '08:00:00', '11:00:00', 'Phòng Lab'),
(2, 'Chủ nhật', '08:00:00', '11:00:00', 'Phòng Lab'),
(3, 'Thứ 3', '14:00:00', '16:00:00', 'Phòng 102'),
(3, 'Thứ 5', '14:00:00', '16:00:00', 'Phòng 102'),
(3, 'Thứ 7', '14:00:00', '16:00:00', 'Phòng 102');

-- 8. Đăng ký & Giao dịch
INSERT INTO DANG_KY (ID_HocVien, ID_LopHoc, NgayDangKy) VALUES 
(1, 1, '2024-02-20'),
(2, 1, '2024-02-22'),
(3, 2, '2024-03-01'),
(4, 2, '2024-03-05'),
(5, 3, '2024-03-20'),
(6, 3, '2024-03-25');

INSERT INTO GIAO_DICH (ID_HocVien, LoaiGiaoDich, SoTien, PhuongThucThanhToan, NgayGiaoDich, NoiDung) VALUES
(1, 'Học phí', 8000000, 'Chuyển khoản', '2024-02-20 09:30:00', 'Học phí lớp L001'),
(2, 'Học phí', 8000000, 'Tiền mặt', '2024-02-22 14:15:00', 'Học phí lớp L001'),
(3, 'Học phí', 4000000, 'Chuyển khoản', '2024-03-01 10:00:00', 'Học phí lớp L002'),
(4, 'Học phí', 4000000, 'Thẻ tín dụng', '2024-03-05 16:45:00', 'Học khóa Java Swing');

-- 9. Bài viết
INSERT INTO BAI_VIET (TieuDe, NoiDung, LoaiBaiViet) VALUES
('Thông báo nghỉ lễ', 'Trung tâm nghỉ lễ từ ngày 30/4 đến hết ngày 1/5.', 'Thông báo'),
('Khai giảng lớp ReactJS', 'Lớp ReactJS mới sẽ bắt đầu vào tháng 7 tới. Đăng ký ngay để nhận ưu đãi 20%.', 'Tin tức'),
('Hội thảo du học Nhật Bản', 'Buổi hội thảo chia sẻ kinh nghiệm săn học bổng Nhật Bản diễn ra sáng Chủ nhật tuần tới.', 'Tin tức');
