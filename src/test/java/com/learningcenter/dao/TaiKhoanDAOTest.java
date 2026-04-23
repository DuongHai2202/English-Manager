package com.learningcenter.dao;

import com.learningcenter.model.TaiKhoan;
import com.learningcenter.util.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit Test cho chức năng Đăng Nhập (TaiKhoanDAO)")
public class TaiKhoanDAOTest {

    private TaiKhoanDAO taiKhoanDAO;

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private MockedStatic<DatabaseConnection> mockedDbConnection;

    @BeforeEach
    void setUp() throws SQLException {
        taiKhoanDAO = new TaiKhoanDAO();
        mockedDbConnection = Mockito.mockStatic(DatabaseConnection.class);
        mockedDbConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @AfterEach
    void tearDown() {
        if (mockedDbConnection != null) {
            mockedDbConnection.close();
        }
    }

    // ==========================================
    // 3 TRƯỜNG HỢP KIỂM THỬ CHO HÀM ĐĂNG NHẬP (ĐỘ PHỦ 100%)
    // ==========================================

    @Test
    @DisplayName("1. Đăng nhập thành công với tài khoản và mật khẩu đúng")
    void dangNhap_Success() throws SQLException {
        // Thiết lập giả lập CSDL
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Tìm thấy tài khoản trong Database
        
        // Đổ dữ liệu mẫu vào ResultSet để Object Mapping chạy thành công
        lenient().when(mockResultSet.getInt("ID_TaiKhoan")).thenReturn(1);
        lenient().when(mockResultSet.getString("TenDangNhap")).thenReturn("admin");
        lenient().when(mockResultSet.getString("HoTen")).thenReturn("Quản Trị Viên");
        lenient().when(mockResultSet.getString("Email")).thenReturn("admin@gmail.com");

        // Gọi hàm đăng nhập đang có trong source nguyên bản của bạn
        TaiKhoan tk = taiKhoanDAO.dangNhap("admin", "123456");

        // Đánh giá: Chắc chắn phải ra được đối tượng TaiKhoan
        assertNotNull(tk, "Đăng nhập đúng tài khoản/mật khẩu/Trạng thái = 1 thì phải bới được tài khoản!");
        assertEquals("admin", tk.getTenDangNhap());
        assertEquals("Quản Trị Viên", tk.getHoTen());
        
        // Đảm bảo là Prepared Statement đã được truyền tham số truy vấn đúng
        verify(mockPreparedStatement).setString(1, "admin");
        verify(mockPreparedStatement).setString(2, "123456");
    }

    @Test
    @DisplayName("2. Đăng nhập thất bại do sai tài khoản hoặc mật khẩu (rs.next = false)")
    void dangNhap_WrongCredentials() throws SQLException {
        // Thiết lập giả lập CSDL
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // Không tìm thấy bất kỳ tài khoản nào matching!

        TaiKhoan tk = taiKhoanDAO.dangNhap("admin", "matkhausai");

        // Đánh giá: Code gốc của bạn thiết kế khi rs.next() false thì sẽ chạy tới cuối bảng return null
        assertNull(tk, "Đăng nhập sai phải bị từ chối và trả về null");
    }

    @Test
    @DisplayName("3. Đăng nhập thất bại do kết nối Database bị sập (SQLException/Timeout)")
    void dangNhap_SQLException() throws SQLException {
        // Cố tình ép Câu truy vấn ném lỗi Đứt cáp vào Database
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Lỗi mất kết nối MySQL"));

        TaiKhoan tk = taiKhoanDAO.dangNhap("admin", "123456");

        // Đánh giá: Code gốc của bạn sẽ nhảy xuống catch block (in log e.printStackTrace()) rồi chốt return null
        assertNull(tk, "Gặp lỗi đứt mạng CSDL, hàm phải an toàn trả về null mà không làm chết app");
    }
}
