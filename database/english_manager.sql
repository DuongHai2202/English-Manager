CREATE DATABASE english_center CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE english_center;

CREATE TABLE TAI_KHOAN (
    ID_TaiKhoan INT AUTO_INCREMENT PRIMARY KEY,
    TenDangNhap VARCHAR(50) NOT NULL UNIQUE,
    MatKhau VARCHAR(255) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    HoTen VARCHAR(100),
    TrangThai BOOLEAN DEFAULT TRUE,
    NgayTao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE HOC_VIEN (
    ID_HocVien INT AUTO_INCREMENT PRIMARY KEY,
    MaHocVien VARCHAR(20) NOT NULL UNIQUE,
    HoTen VARCHAR(100) NOT NULL,
    NgaySinh DATE,
    GioiTinh VARCHAR(10),
    SoDienThoai VARCHAR(20),
    Email VARCHAR(100),
    DiaChi TEXT,
    TrinhDo VARCHAR(50),
    NgayNhapHoc DATE NOT NULL,
    TrangThai VARCHAR(50) DEFAULT 'Đang học',
    GhiChu TEXT
) ENGINE=InnoDB;

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

CREATE TABLE KHOA_HOC (
    ID_KhoaHoc INT AUTO_INCREMENT PRIMARY KEY,
    MaKhoaHoc VARCHAR(20) NOT NULL UNIQUE,
    TenKhoaHoc VARCHAR(200) NOT NULL,
    MoTa TEXT,
    TrinhDo VARCHAR(50),
    ThoiLuong INT,
    HocPhi DECIMAL(12, 2) NOT NULL,
    TrangThai VARCHAR(50) DEFAULT 'Đang mở'
) ENGINE=InnoDB;

CREATE TABLE LOP_HOC (
    ID_LopHoc INT AUTO_INCREMENT PRIMARY KEY,
    ID_KhoaHoc INT NOT NULL,
    MaLopHoc VARCHAR(20) NOT NULL UNIQUE,
    TenLopHoc VARCHAR(200) NOT NULL,
    SiSoToiDa INT DEFAULT 30,
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE,
    PhongHoc VARCHAR(50),
    TrangThai VARCHAR(50) DEFAULT 'Sắp mở',
    FOREIGN KEY (ID_KhoaHoc) REFERENCES KHOA_HOC(ID_KhoaHoc) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE PHAN_LOP (
    ID_LopHoc INT NOT NULL,
    ID_GiangVien INT NOT NULL,
    PRIMARY KEY (ID_LopHoc, ID_GiangVien),
    FOREIGN KEY (ID_LopHoc) REFERENCES LOP_HOC(ID_LopHoc) ON DELETE CASCADE,
    FOREIGN KEY (ID_GiangVien) REFERENCES GIANG_VIEN(ID_GiangVien) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE LICH_HOC (
    ID_LichHoc INT AUTO_INCREMENT PRIMARY KEY,
    ID_LopHoc INT NOT NULL,
    ThuTrongTuan VARCHAR(20) NOT NULL,
    GioBatDau TIME NOT NULL,
    GioKetThuc TIME NOT NULL,
    PhongHoc VARCHAR(50),
    FOREIGN KEY (ID_LopHoc) REFERENCES LOP_HOC(ID_LopHoc) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE DANG_KY (
    ID_DangKy INT AUTO_INCREMENT PRIMARY KEY,
    ID_HocVien INT NOT NULL,
    ID_LopHoc INT NOT NULL,
    NgayDangKy DATE NOT NULL,
    TrangThai VARCHAR(50) DEFAULT 'Đã đăng ký',
    TienDo DECIMAL(5, 2) DEFAULT 0.00,
    DiemSo DECIMAL(5, 2),
    GhiChu TEXT,
    FOREIGN KEY (ID_HocVien) REFERENCES HOC_VIEN(ID_HocVien) ON DELETE CASCADE,
    FOREIGN KEY (ID_LopHoc) REFERENCES LOP_HOC(ID_LopHoc) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE DIEM_DANH (
    ID_DiemDanh INT AUTO_INCREMENT PRIMARY KEY,
    ID_DangKy INT NOT NULL,
    NgayDiemDanh DATE NOT NULL,
    TrangThai VARCHAR(50) NOT NULL,
    GhiChu TEXT,
    FOREIGN KEY (ID_DangKy) REFERENCES DANG_KY(ID_DangKy) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE GIAO_DICH (
    ID_GiaoDich INT AUTO_INCREMENT PRIMARY KEY,
    ID_HocVien INT NOT NULL,
    LoaiGiaoDich VARCHAR(100) NOT NULL,
    SoTien DECIMAL(12, 2) NOT NULL,
    PhuongThucThanhToan VARCHAR(50),
    NgayGiaoDich DATETIME NOT NULL,
    TrangThai VARCHAR(50) DEFAULT 'Hoàn thành',
    NoiDung TEXT,
    FOREIGN KEY (ID_HocVien) REFERENCES HOC_VIEN(ID_HocVien) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE BAI_VIET (
    ID_BaiViet INT AUTO_INCREMENT PRIMARY KEY,
    TieuDe VARCHAR(255) NOT NULL,
    NoiDung TEXT NOT NULL,
    LoaiBaiViet VARCHAR(50),
    TrangThai VARCHAR(50) DEFAULT 'Đã đăng',
    NgayDang DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE TAI_LIEU (
    ID_TaiLieu INT AUTO_INCREMENT PRIMARY KEY,
    ID_KhoaHoc INT,
    TieuDe VARCHAR(255) NOT NULL,
    MoTa TEXT,
    TenFile VARCHAR(255) NOT NULL,
    DuongDanFile VARCHAR(500) NOT NULL,
    FOREIGN KEY (ID_KhoaHoc) REFERENCES KHOA_HOC(ID_KhoaHoc) ON DELETE CASCADE
) ENGINE=InnoDB;

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

CREATE TABLE PHAN_HOI_HO_TRO (
    ID_PhanHoi INT AUTO_INCREMENT PRIMARY KEY,
    ID_HoTro INT NOT NULL,
    NoiDung TEXT NOT NULL,
    NgayPhanHoi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ID_HoTro) REFERENCES HO_TRO(ID_HoTro) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE CO_SO_VAT_CHAT (
    ID_CoSoVatChat INT AUTO_INCREMENT PRIMARY KEY,
    TenCoSoVatChat VARCHAR(200) NOT NULL,
    LoaiCoSoVatChat VARCHAR(100),
    SoLuong INT DEFAULT 0,
    DonViTinh VARCHAR(50),
    TinhTrang VARCHAR(50),
    NgayMua DATE,
    GhiChu TEXT
) ENGINE=InnoDB;

CREATE TABLE CHUNG_CHI (
    id INT AUTO_INCREMENT PRIMARY KEY,
    loai_chung_chi VARCHAR(100) NOT NULL,
    ten_chung_chi VARCHAR(255) NOT NULL,
    mo_ta_ngan TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE NGAY_NGHI (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ten_ngay_nghi VARCHAR(255) NOT NULL,
    ngay_bat_dau DATE NOT NULL,
    ngay_ket_thuc DATE NOT NULL,
    ghi_chu TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE PHONG_HOC (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ten_phong VARCHAR(100) NOT NULL,
    suc_chua INT DEFAULT 0,
    tang INT,
    trang_thai VARCHAR(50) DEFAULT 'Trống'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO TAI_KHOAN (ID_TaiKhoan, TenDangNhap, MatKhau, Email, HoTen, TrangThai, NgayTao) VALUES
(1, 'admin', '123456', 'admin@trungtam.com', 'Quản Trị Viên Hệ Thống', 1, '2026-01-01 13:05:52');

INSERT INTO NHAN_VIEN (ID_NhanVien, MaNhanVien, HoTen, NgaySinh, GioiTinh, SoDienThoai, Email, DiaChi, ChucVu, NgayVaoLam, Luong, TrangThai) VALUES
(7, 'NV01', 'Phạm Kiên Hưng', '2005-01-12', 'Nam', '0981686941', 'pkhung@gmail.com', 'Bắc Ninh', 'Quản lý', '2025-12-30', 1000000.00, 'Đang làm'),
(8, 'NV02', 'Nguyễn Thu Hà', '2005-12-23', 'Nữ', '09412841241', 'hanie@gmail.com', 'Bắc Ninh', 'Kế Toán', '2025-12-23', 1000000.00, 'Đang làm');

INSERT INTO GIANG_VIEN (ID_GiangVien, MaGiangVien, HoTen, NgaySinh, GioiTinh, SoDienThoai, Email, ChuyenMon, TrinhDoHocVan, NgayVaoLam, MucLuongGio, TrangThai) VALUES
(1, 'GV001', 'Phạm Minh Đức', NULL, NULL, NULL, NULL, 'Tiếng Anh IELTS', NULL, '2023-12-01', 250000.00, 'Đang dạy'),
(2, 'GV002', 'Hoàng Thu Trang', NULL, NULL, NULL, NULL, 'Lập trình Java', NULL, '2024-01-15', 300000.00, 'Đang dạy'),
(3, 'GV003', 'Nguyễn Tiến Dũng', NULL, NULL, NULL, NULL, 'Tiếng Nhật N3', NULL, '2024-02-20', 200000.00, 'Đang dạy'),
(4, 'GV004', 'Lương Mỹ Linh', NULL, NULL, NULL, NULL, 'Marketing Online', NULL, '2024-04-01', 350000.00, 'Đang dạy'),
(5, 'GV05', 'Thu Thuỷ', '2000-09-12', 'Nữ', '0123141242', 'thuy@gmail.com', 'IELTS', '8.5', '2024-01-23', 2500000.00, 'Đang làm'),
(6, 'GV06', 'Hải Dương', '2004-12-15', 'Nam', '0124124912', 'haid@gmail.com', 'Toeic', '850+', '2023-09-23', 300000.00, 'Đang làm');

INSERT INTO HOC_VIEN (ID_HocVien, MaHocVien, HoTen, NgaySinh, GioiTinh, SoDienThoai, Email, TrinhDo, NgayNhapHoc, TrangThai) VALUES
(1, 'HV001', 'Trần Bảo An', '2005-04-12', 'Nam', '0912345678', 'an.tran@gmail.com', 'Cơ bản', '2024-01-20', 'Đang học'),
(2, 'HV002', 'Nguyễn Minh Quân', '2006-08-22', 'Nam', '0987654321', 'quan.ng@gmail.com', 'Nâng cao', '2024-02-05', 'Đang học'),
(3, 'HV003', 'Lê Thị Mai', '2007-01-15', 'Nữ', '0901234567', 'mai.le@gmail.com', 'Trung bình', '2024-02-15', 'Đang học'),
(4, 'HV004', 'Phạm Hồng Phúc', '2004-11-30', 'Nam', '0934567890', 'phuc.pham@gmail.com', 'Cơ bản', '2024-03-01', 'Đang học'),
(5, 'HV005', 'Vũ Thúy Hiền', '2005-09-10', 'Nữ', '0945678901', 'hien.vu@gmail.com', 'Nâng cao', '2024-03-10', 'Đang học'),
(6, 'HV006', 'Đỗ Anh Tuấn', '2003-05-05', 'Nam', '0956789012', 'tuan.do@gmail.com', 'Trung bình', '2024-03-20', 'Đang học'),
(7, 'HV007', 'Ngô Phương Thảo', '2008-02-28', 'Nữ', '0967890123', 'thao.ngo@gmail.com', 'Cơ bản', '2024-04-05', 'Đang học'),
(8, 'HV008', 'Bùi Xuân Hùng', '2004-07-14', 'Nam', '0978901234', 'hung.bui@gmail.com', 'Nâng cao', '2024-04-15', 'Đang học'),
(9, 'HV009', 'Lý Gia Hân', '2006-12-12', 'Nữ', '0323456789', 'han.ly@gmail.com', 'Trung bình', '2024-05-01', 'Đang học'),
(10, 'HV010', 'Trương Quốc Bảo', '2005-03-25', 'Nam', '0334567890', 'bao.truong@gmail.com', 'Cơ bản', '2024-05-10', 'Đang học'),
(11, 'HV011', 'Phan Minh Tuyến', '2002-10-18', 'Nam', '0345678901', 'tuyen.phan@gmail.com', 'Nâng cao', '2024-05-20', 'Đang học'),
(12, 'HV012', 'Đặng Mỹ Duyên', '2007-06-06', 'Nữ', '0356789012', 'duyen.dang@gmail.com', 'Trung bình', '2024-06-01', 'Đang học');

INSERT INTO KHOA_HOC (ID_KhoaHoc, MaKhoaHoc, TenKhoaHoc, MoTa, TrinhDo, ThoiLuong, HocPhi, TrangThai) VALUES
(1, 'KH001', 'Luyện thi IELTS 6.5', 'Ielst 6.5', 'Nâng cao', 120, 8000000.00, 'Đang mở'),
(2, 'KH002', 'IELTS Advanced', 'IELTS 5.0', 'Cơ bản', 60, 4000000.00, 'Đang mở'),
(3, 'KH003', 'Tiếng Nhật Giao tiếp N3', 'N3', 'Trung bình', 80, 5500000.00, 'Đang mở'),
(4, 'KH004', 'Toeic Beginner', 'Toeic 350+', 'Cơ bản', 45, 3500000.00, 'Đang mở'),
(5, 'KH005', 'Toeic Advanced', 'Toeic 550+', 'Nâng cao', 90, 6000000.00, 'Đang mở'),
(6, 'KH06', 'Toeic Intensive', 'Toeic 450+', 'Cơ bản', 30, 100000.00, 'Đang mở');

INSERT INTO LOP_HOC (ID_LopHoc, ID_KhoaHoc, MaLopHoc, TenLopHoc, SiSoToiDa, NgayBatDau, NgayKetThuc, PhongHoc, TrangThai) VALUES
(1, 1, 'L001', 'IELTS 6.5 - Tối 2.4.6', 30, '2024-03-01', '2025-01-12', 'Phòng 201', 'Đã kết thúc'),
(2, 2, 'L002', 'Java Swing - Sáng T7.CN', 30, '2025-03-12', '2025-12-24', 'Phòng Lab', 'Đang học'),
(3, 3, 'L003', 'Tiếng Nhật N3 - Chiều 3.5.7', 30, '2025-04-08', '2025-12-16', 'Phòng 102', 'Đang học'),
(4, 4, 'L004', 'Marketing K01 - Tối Thứ 6', 30, '2025-05-23', '2026-01-02', 'Phòng 303', 'Sắp mở'),
(5, 2, 'LH005', 'IELTS Advanced Tối', 15, '2025-12-23', '2026-02-03', 'Phòng 203', 'Đang học');

INSERT INTO PHAN_LOP (ID_LopHoc, ID_GiangVien) VALUES 
(1, 1),
(2, 6),
(2, 2),
(3, 3),
(4, 4);

INSERT INTO LICH_HOC (ID_LopHoc, ThuTrongTuan, GioBatDau, GioKetThuc, PhongHoc) VALUES
(1, 'Thứ 2', '18:00:00', '20:00:00', 'Phòng 201'),
(1, 'Thứ 4', '18:00:00', '20:00:00', 'Phòng 201'),
(1, 'Thứ 6', '18:00:00', '20:00:00', 'Phòng 201'),
(2, 'Thứ 7', '08:00:00', '11:00:00', 'Phòng Lab'),
(2, 'Chủ nhật', '08:00:00', '11:00:00', 'Phòng Lab'),
(3, 'Thứ 3', '14:00:00', '16:00:00', 'Phòng 102'),
(3, 'Thứ 5', '14:00:00', '16:00:00', 'Phòng 102'),
(3, 'Thứ 7', '14:00:00', '16:00:00', 'Phòng 102');

INSERT INTO DANG_KY (ID_DangKy, ID_HocVien, ID_LopHoc, NgayDangKy, TrangThai, TienDo, DiemSo, GhiChu) VALUES 
(1, 1, 1, '2024-02-20', 'Đã đăng ký', 0.00, NULL, NULL),
(2, 2, 1, '2024-02-22', 'Đã đăng ký', 0.00, NULL, NULL),
(3, 3, 2, '2024-03-01', 'Đã đăng ký', 0.00, NULL, NULL),
(4, 4, 2, '2024-03-05', 'Đã đăng ký', 0.00, NULL, NULL),
(5, 5, 3, '2024-03-20', 'Đã đăng ký', 0.00, NULL, NULL),
(6, 6, 3, '2024-03-25', 'Đã đăng ký', 0.00, NULL, NULL),
(9, 7, 1, '2026-01-04', 'Đã đăng ký', 0.00, NULL, NULL),
(10, 8, 2, '2026-01-04', 'Đã đăng ký', 0.00, NULL, NULL),
(12, 9, 3, '2026-01-04', 'Bảo lưu', 2.00, 0, '');

INSERT INTO GIAO_DICH (ID_GiaoDich, ID_HocVien, LoaiGiaoDich, SoTien, PhuongThucThanhToan, NgayGiaoDich, TrangThai, NoiDung) VALUES
(1, 1, 'Học phí', 8000000.00, 'Chuyển khoản', '2024-02-20 09:30:00', 'Hoàn thành', 'Học phí lớp L001'),
(2, 2, 'Học phí', 8000000.00, 'Tiền mặt', '2024-02-22 14:15:00', 'Hoàn thành', 'Học phí lớp L001'),
(3, 3, 'Học phí', 4000000.00, 'Chuyển khoản', '2024-03-01 10:00:00', 'Hoàn thành', 'Học phí lớp L002'),
(4, 4, 'Học phí', 4000000.00, 'Thẻ tín dụng', '2024-03-05 16:45:00', 'Hoàn thành', 'Học phí khóa Java Swing'),
(7, 5, 'Thu phí', 22222.00, 'Tiền mặt', '2026-01-03 00:00:00', 'Thành công', ''),
(10, 6, 'Thu phí', 88888.00, 'Tiền mặt', '2026-01-04 00:00:00', 'Thành công', ''),
(12, 7, 'Thu phí', 7777777.00, 'Tiền mặt', '2026-01-05 08:50:31', 'Thành công', ''),
(13, 8, 'Thu phí', 8888888.00, 'Tiền mặt', '2026-01-05 08:55:04', 'Thành công', '');

INSERT INTO BAI_VIET (ID_BaiViet, TieuDe, NoiDung, LoaiBaiViet, NgayDang) VALUES
(1, 'Thông báo nghỉ lễ', 'Trung tâm nghỉ lễ từ ngày 30/4 đến hết ngày 1/5.', 'Thông báo', '2026-01-02 09:42:03'),
(2, 'Khai giảng lớp ReactJS', 'Lớp ReactJS mới sẽ bắt đầu vào tháng 7 tới. Đăng ký ngay để nhận ưu đãi 20%.', 'Tin tức', '2026-01-02 09:42:03'),
(3, 'Hội thảo du học Nhật Bản', 'Buổi hội thảo chia sẻ kinh nghiệm săn học bổng Nhật Bản diễn ra sáng Chủ nhật tuần tới.', 'Tin tức', '2026-01-02 09:42:03');

INSERT INTO CO_SO_VAT_CHAT (ID_CoSoVatChat, TenCoSoVatChat, LoaiCoSoVatChat, SoLuong, DonViTinh, TinhTrang, NgayMua) VALUES
(1, 'Máy chiếu Sony 4K', 'Thiết bị', 5, 'Cái', 'Tốt', '2024-01-10'),
(2, 'Bộ loa máy tính Logitech', 'Thiết bị', 10, 'Bộ', 'Tốt', '2024-01-15'),
(3, 'Bàn làm việc nhân viên', 'Nội thất', 15, 'Cái', 'Tốt', '2024-02-01'),
(4, 'Ghế xoay văn phòng', 'Nội thất', 20, 'Cái', 'Tốt', '2024-02-01'),
(5, 'Bảng từ viết bút lông', 'Thiết bị', 4, 'Cái', 'Tốt', '2024-03-05'),
(6, 'Điều hòa Panasonic 12000BTU', 'Thiết bị', 6, 'Cái', 'Cần bảo trì', '2024-01-05');

INSERT INTO HO_TRO (TenNguoiGui, EmailNguoiGui, TieuDe, NoiDung, ChuyenMuc, DoUuTien, TrangThai) VALUES
('Nguyễn Văn A','vana@gmail.com','Không đăng nhập được hệ thống','Em đăng nhập bằng tài khoản đã cấp nhưng hệ thống báo sai mật khẩu.','Tài khoản','Cao','Mở'),
('Trần Thị B','tranb@gmail.com','Lỗi không hiển thị lịch học','Sau khi đăng nhập, mục Lịch học không hiển thị dữ liệu.','Lịch học','Trung bình','Mở'),
('Lê Minh C','leminhc@gmail.com','Không tải được tài liệu','Em bấm tải tài liệu nhưng hệ thống không phản hồi.','Tài liệu','Thấp','Đang xử lý'),
('Phạm Hoàng D','hoangd@gmail.com','Sai thông tin học phí','Học phí hiển thị không đúng với khóa học đã đăng ký.','Tài chính','Cao','Đã xử lý');

INSERT INTO TAI_LIEU (ID_KhoaHoc, TieuDe, MoTa, TenFile, DuongDanFile) VALUES 
(1, 'Giáo trình IELTS Practice Plus 3', 'Bộ đề luyện thi IELTS chuyên sâu có đáp án chi tiết', 'ielts-plus-3.pdf', '/uploads/docs/ielts/ielts-plus-3.pdf'), 
(1, 'Vocabulary for IELTS Advanced', 'Từ vựng nâng cao cho mục tiêu band 7.0+', 'vocab-ielts-adv.pdf', '/uploads/docs/ielts/vocab-ielts-adv.pdf'), 
(2, 'Slide bài giảng Java Swing', 'Tổng hợp các UI components và Event Handling', 'java-swing-slides.pptx', '/uploads/docs/java/java-swing-slides.pptx'), 
(2, 'Project mẫu Quản lý thư viện', 'Mã nguồn mẫu sử dụng Java Swing và JDBC', 'library-management-src.zip', '/uploads/docs/java/library-management-src.zip'), 
(3, 'Ngữ pháp Tiếng Nhật N3', 'Tổng hợp 100 cấu trúc ngữ pháp quan trọng', 'grammar-n3-summary.pdf', '/uploads/docs/japanese/grammar-n3.pdf'), 
(3, 'Audio Mimikara Oboeru N3', 'File nghe phục vụ bài tập nghe hiểu', 'n3-listening-audio.rar', '/uploads/docs/japanese/n3-audio.rar'), 
(4, 'Digital Marketing Handbook', 'Cẩm nang lập kế hoạch Marketing đa kênh', 'dm-handbook.pdf', '/uploads/docs/marketing/dm-handbook.pdf'), 
(5, 'ReactJS Hooks Cheat Sheet', 'Bảng tra cứu nhanh các Hooks trong React', 'react-hooks-cheat.pdf', '/uploads/docs/react/react-hooks-cheat.pdf');

INSERT INTO CHUNG_CHI (id, loai_chung_chi, ten_chung_chi, mo_ta_ngan) VALUES
(1, 'Ngoại ngữ', 'IELTS', 'Chứng chỉ tiếng Anh học thuật, thang điểm 9.0.'),
(2, 'Ngoại ngữ', 'TOEIC', 'Chứng chỉ tiếng Anh giao tiếp quốc tế, thang điểm 990.'),
(3, 'Ngoại ngữ', 'JLPT', 'Kỳ thi năng lực Nhật ngữ (N1 - N5).'),
(4, 'Tin học', 'MOS', 'Chứng chỉ tin học văn phòng Microsoft Office Specialist.'),
(5, 'Tin học', 'IC3', 'Chứng chỉ quốc tế về sử dụng máy tính và Internet cơ bản.'),
(6, 'Kỹ năng', 'Project Management Professional', 'Chứng chỉ quản lý dự án chuyên nghiệp (PMP).');

INSERT INTO NGAY_NGHI (id, ten_ngay_nghi, ngay_bat_dau, ngay_ket_thuc, ghi_chu) VALUES
(1, 'Lễ Quốc Khánh 2/9', '2026-09-02', '2026-09-02', 'Nghỉ lễ theo quy định nhà nước'),
(2, 'Nghỉ Hè', '2026-06-01', '2026-08-31', 'Thời gian nghỉ hè của học sinh/sinh viên'),
(3, 'Tết Nguyên Đán', '2026-02-16', '2026-02-22', 'Nghỉ Tết âm lịch truyền thống'),
(4, 'Giải phóng miền Nam', '2026-04-30', '2026-04-30', 'Ngày thống nhất đất nước'),
(5, 'Quốc tế Lao động', '2026-05-01', '2026-05-01', 'Nghỉ lễ quốc tế');

INSERT INTO PHONG_HOC (id, ten_phong, suc_chua, tang, trang_thai) VALUES
(1, 'Phòng 101', 45, 1, 'Trống'),
(2, 'Phòng 102', 50, 1, 'Đang sử dụng'),
(3, 'Phòng 201', 35, 2, 'Trống'),
(4, 'Phòng 205', 120, 2, 'Đang sửa chữa'),
(5, 'Phòng 301', 60, 3, 'Trống');

INSERT INTO DIEM_DANH (ID_DangKy, NgayDiemDanh, TrangThai, GhiChu) VALUES
(1, '2026-01-04', 'Có mặt', 'Đúng giờ'),
(2, '2026-01-04', 'Có mặt', 'Đúng giờ'),
(9, '2026-01-04', 'Vắng', 'Nghỉ không phép'),
(3, '2026-01-05', 'Có mặt', 'Đúng giờ'),
(4, '2026-01-05', 'Muộn', 'Muộn 15 phút do hỏng xe'),
(10, '2026-01-05', 'Có mặt', 'Đúng giờ');
