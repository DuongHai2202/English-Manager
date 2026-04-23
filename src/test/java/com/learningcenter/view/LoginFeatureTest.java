package com.learningcenter.view;

import org.junit.jupiter.api.*;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Kiểm thử Tích hợp: UI tới Database (Chức năng Đăng Nhập)")
public class LoginFeatureTest {

    private LoginView loginView;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblStatus;

    @BeforeEach
    void setUp() throws Exception {
        // 1. Mở Cửa Sổ Ứng dụng thật (Sẽ khởi động và tải UI y như thật)
        loginView = new LoginView();
        
        // 2. Vì bạn đặt các biến là private, chúng ta dùng Reflection để lấy các bộ phận của Giao diện
        Field userField = LoginView.class.getDeclaredField("txtUsername");
        userField.setAccessible(true);
        txtUsername = (JTextField) userField.get(loginView);

        Field passField = LoginView.class.getDeclaredField("txtPassword");
        passField.setAccessible(true);
        txtPassword = (JPasswordField) passField.get(loginView);

        Field btnField = LoginView.class.getDeclaredField("btnLogin");
        btnField.setAccessible(true);
        btnLogin = (JButton) btnField.get(loginView);

        Field statusField = LoginView.class.getDeclaredField("lblStatus");
        statusField.setAccessible(true);
        lblStatus = (JLabel) statusField.get(loginView);
    }

    @AfterEach
    void tearDown() {
        // Đóng form sau khi test xong dọn dẹp bộ nhớ
        if (loginView != null) {
            loginView.dispose();
        }
    }

    // ===============================================
    // TEST LUỒNG CHÍNH - KHÔNG DÙNG MOCK - CHẠY THẬT
    // Cảnh báo: Phải bật XAMPP / SQL Server thì test này mới pass
    // ===============================================

    @Test
    @DisplayName("Kịch bản 1: Để trống dữ liệu bấm Đăng nhập -> Hiện cảnh báo")
    void testLogin_EmptyInput_UI() throws Exception {
        // Hành động như một User trên phím: Không gõ gì cả
        txtUsername.setText("");
        txtPassword.setText("");

        // Act: Ấn nút Đăng Nhập
        btnLogin.doClick();

        // Assert: Chắc chắn ô Label thông báo lỗi phải hiện dòng chữ cảnh báo
        assertEquals("Vui lòng nhập đầy đủ thông tin!", lblStatus.getText());
        assertTrue(loginView.isDisplayable(), "Cửa sổ Login phải vẫn đang hiển thị, chưa được tắt");
    }

    @Test
    @DisplayName("Kịch bản 2: Nhập sai tài khoản -> Chọc thẳng DB báo lỗi")
    void testLogin_WrongInput_DbReject() throws Exception {
        // Arrange
        txtUsername.setText("nickchualenh12345");
        txtPassword.setText("passbậybạ123s");

        // Act: Ấn nút Đăng Nhập (Lúc này nó sẽ đi thẳng vào TaiKhoanDAO.java gọi Cơ sở dữ liệu thật)
        btnLogin.doClick();

        // Assert: Thất bại do DB trả về rỗng, UI phải đổi trạng thái
        assertEquals("Sai tài khoản hoặc mật khẩu!", lblStatus.getText());
        assertTrue(loginView.isDisplayable(), "Cửa sổ Login phải vẫn hien thi");
    }

    /**
     * Test này có thể Fail nếu trong Database thật của bạn chưa có sẵn nick "admin"/"123456"
     * Bạn hãy thay đổi username và pass tương ứng với dòng data trong CSDL của bạn nhé!
     */
    @Test
    @DisplayName("Kịch bản 3: Nhập đúng tài khoản -> DB cho phép -> Đóng cửa sổ mở MainFrame")
    void testLogin_Success_EndToEnd() throws Exception {
        // Bơm thông tin tài khoản Thật có dưới Database
        txtUsername.setText("admin"); // <-- Sửa đúng tài khoản CSDL 
        txtPassword.setText("123456");   // <-- Sửa mật khẩu thực tế

        // Ấn nút!
        btnLogin.doClick();

        // Vì hàm chạy đăng nhập đúng trong View có code: "this.dispose(); new MainFrame..."
        // Assert: Kiểm tra cửa sổ UI hiện tại đã đóng cúp chưa
        assertFalse(loginView.isDisplayable(), "Nếu đăng nhập đúng, cửa sổ Login phải BỊ TẮT ĐI ngay lập tức!");
        
        // Assert phụ: Label trạng thái không được hiện chữ thông báo sai tài khoản
        assertNotEquals("Sai tài khoản hoặc mật khẩu!", lblStatus.getText());
    }
}
