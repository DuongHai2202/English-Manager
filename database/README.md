# Hướng dẫn Import Database

## Yêu cầu
- MySQL Server 5.7 trở lên
- User: `root`
- Password: `123456`

## Cách 1: Import bằng MySQL Command Line

```bash
# Đăng nhập vào MySQL
mysql -u root -p

# Nhập password: 123456

# Chạy script
source c:/Users/z/OneDrive/Documents/NetBeansProjects/GiangVien/database/database_schema.sql

# Hoặc thoát MySQL và chạy trực tiếp
mysql -u root -p123456 < c:/Users/z/OneDrive/Documents/NetBeansProjects/GiangVien/database/database_schema.sql
```

## Cách 2: Import bằng MySQL Workbench

1. Mở MySQL Workbench
2. Kết nối tới MySQL Server (localhost:3306)
3. File → Open SQL Script
4. Chọn file `database_schema.sql`
5. Execute (⚡ icon hoặc Ctrl+Shift+Enter)

## Cách 3: Import bằng phpMyAdmin

1. Truy cập phpMyAdmin
2. Chọn tab "Import"
3. Click "Choose File" và chọn `database_schema.sql`
4. Click "Go"

## Kiểm tra Database

```sql
-- Kiểm tra database đã tạo
SHOW DATABASES;

-- Sử dụng database
USE learning_center_db;

-- Xem các bảng
SHOW TABLES;

-- Kiểm tra dữ liệu mẫu
SELECT * FROM users;
SELECT * FROM students;
SELECT * FROM courses;
SELECT * FROM classes;

-- Kiểm tra số lượng records
SELECT 'users' as table_name, COUNT(*) as record_count FROM users
UNION ALL
SELECT 'students', COUNT(*) FROM students
UNION ALL
SELECT 'staff', COUNT(*) FROM staff
UNION ALL
SELECT 'instructors', COUNT(*) FROM instructors
UNION ALL
SELECT 'courses', COUNT(*) FROM courses
UNION ALL
SELECT 'classes', COUNT(*) FROM classes
UNION ALL
SELECT 'enrollments', COUNT(*) FROM enrollments
UNION ALL
SELECT 'attendance', COUNT(*) FROM attendance
UNION ALL
SELECT 'transactions', COUNT(*) FROM transactions;
```

## Thông tin Tài khoản Mẫu

### Admin
- Username: `admin`
- Password: `admin123`
- Email: admin@learningcenter.com

### Staff
- Username: `staff01`
- Password: `staff123`
- Email: staff01@learningcenter.com

### Instructor
- Username: `instructor01`
- Password: `instructor123`
- Email: instructor01@learningcenter.com

### Student
- Username: `student01`
- Password: `student123`
- Email: student01@learningcenter.com

## Cấu trúc Database

### Các bảng chính:
1. **users** - Tài khoản người dùng (4 roles: admin, staff, instructor, student)
2. **students** - Thông tin học viên (5 records mẫu)
3. **staff** - Thông tin nhân viên (2 records mẫu)
4. **instructors** - Thông tin giảng viên (3 records mẫu)
5. **courses** - Khóa học (5 khóa mẫu)
6. **classes** - Lớp học (3 lớp mẫu)
7. **schedules** - Lịch học theo tuần
8. **enrollments** - Đăng ký học của học viên
9. **attendance** - Điểm danh
10. **transactions** - Giao dịch thanh toán
11. **posts** - Bài viết/thông báo
12. **documents** - Tài liệu học tập
13. **support_tickets** - Yêu cầu hỗ trợ
14. **ticket_replies** - Phản hồi hỗ trợ

## Tính năng Database

✅ UTF-8 encoding (utf8mb4_unicode_ci)
✅ Foreign key constraints
✅ Indexes cho tìm kiếm nhanh
✅ Auto increment primary keys
✅ Timestamps tự động
✅ ENUM types cho data validation
✅ Sample data đầy đủ để test

## Troubleshooting

### Lỗi: "Access denied for user 'root'@'localhost'"
- Kiểm tra password MySQL của bạn
- Nếu password khác, sửa trong code DatabaseConnection.java

### Lỗi: "Unknown database 'learning_center_db'"
- Chạy lại script SQL để tạo database

### Lỗi: "Table already exists"
- Script đã có DROP DATABASE, nên sẽ xóa DB cũ trước khi tạo mới
- Nếu muốn giữ data cũ, backup trước khi chạy script
