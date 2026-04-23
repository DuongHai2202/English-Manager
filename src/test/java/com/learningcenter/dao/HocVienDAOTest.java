package com.learningcenter.dao;

import com.learningcenter.model.HocVien;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HocVienDAOTest {

    private HocVienDAO hocVienDao;

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private MockedStatic<DatabaseConnection> mockedDbConnection;

    @BeforeEach
    void setUp() throws SQLException {
        hocVienDao = new HocVienDAO();
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
    // NHÓM 1: TEST getAll()
    // ==========================================
    @Test
    @DisplayName("getAll() - Thành công, trả về 1 kết quả")
    void getAll_Success() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        lenient().when(mockResultSet.getInt(anyString())).thenReturn(1);
        lenient().when(mockResultSet.getString(anyString())).thenReturn("Nguyen Van A");

        List<HocVien> lists = hocVienDao.getAll();

        assertNotNull(lists);
        assertEquals(1, lists.size());
        assertEquals("Nguyen Van A", lists.get(0).getHoTen());
    }

    @Test
    @DisplayName("getAll() - Bắt exception khi CSDL lỗi")
    void getAll_Exception() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Query Error"));

        List<HocVien> lists = hocVienDao.getAll();

        // Theo luồng catch, khi lỗi SQLException chạy e.printStackTrace(), List sẽ được
        // ném ra rỗng.
        assertNotNull(lists);
        assertTrue(lists.isEmpty(), "Mảng trả về phải rỗng khi có lỗi SQL");
    }

    // ==========================================
    // NHÓM 2: TEST search() BẰNG CÁC BRANCH TIÊU CHÍ KHÁC NHAU
    // ==========================================
    @Test
    @DisplayName("search() - Trống keyword, bỏ qua Filter")
    void search_EmptyKeyword() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        List<HocVien> lists = hocVienDao.search("   ", "Theo tên"); // Chuỗi toàn dấu cách

        assertNotNull(lists);
        assertTrue(lists.isEmpty());
        // Verify không truyền tham số nào (không gọi setObject)
        verify(mockPreparedStatement, never()).setObject(anyInt(), any());
    }

    @Test
    @DisplayName("search() - Tiêu chí: Theo Tên")
    void search_ByName() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        hocVienDao.search("Kieu", "Theo tên");

        // setObject(1, "%Kieu%") phải được gọi
        verify(mockPreparedStatement).setObject(1, "%Kieu%");
    }

    @Test
    @DisplayName("search() - Tiêu chí: Theo Mã")
    void search_ByCode() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        hocVienDao.search("HV001", "Theo mã");

        verify(mockPreparedStatement).setObject(1, "%HV001%");
    }

    @Test
    @DisplayName("search() - Tiêu chí: Theo ID")
    void search_ById() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        hocVienDao.search("10", "Theo id");

        verify(mockPreparedStatement).setObject(1, "%10%");
    }

    @Test
    @DisplayName("search() - Tiêu chí: Tất cả")
    void search_ByAllCriteria() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        hocVienDao.search("A", "Tất cả");

        // Mệnh đề "Tất cả" chèn cả Name lẫn Mã -> tốn 2 params
        verify(mockPreparedStatement).setObject(1, "%A%");
        verify(mockPreparedStatement).setObject(2, "%A%");
    }

    // ==========================================
    // NHÓM 3: TEST add()
    // ==========================================
    @Test
    @DisplayName("add() - Insert thành công lớn hơn 0 dòng")
    void add_Success() throws SQLException {
        HocVien hv = new HocVien();
        hv.setMaHocVien("HV001");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertTrue(hocVienDao.add(hv));
        verify(mockPreparedStatement).setString(1, "HV001");
    }

    @Test
    @DisplayName("add() - Insert thất bại khi CSDL không nạp dòng nào")
    void add_Fail_ZeroRowsUpdated() throws SQLException {
        HocVien hv = new HocVien();
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        assertFalse(hocVienDao.add(hv));
    }

    @Test
    @DisplayName("add() - Bắt Exception an toàn trả về false")
    void add_Exception() throws SQLException {
        HocVien hv = new HocVien();
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Lỗi Insert DB"));
        assertFalse(hocVienDao.add(hv));
    }

    // ==========================================
    // NHÓM 4: TEST update()
    // ==========================================
    @Test
    @DisplayName("update() - Update thành công")
    void update_Success() throws SQLException {
        HocVien hv = new HocVien();
        hv.setIdHocVien(10);
        hv.setHoTen("New Name");

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertTrue(hocVienDao.update(hv));
        // Kiểm tra xem trường HoTen có chọc đúng vào vị trí Param 2 hay không
        verify(mockPreparedStatement).setString(2, "New Name");
        // Kiểm tra điều kiện Where có gán trùng với Param 11 hay không
        verify(mockPreparedStatement).setInt(11, 10);
    }

    @Test
    @DisplayName("update() - Cập nhật thất bại hoặc lỗi kết nối")
    void update_Exception() throws SQLException {
        HocVien hv = new HocVien();
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Mất mạng"));

        assertFalse(hocVienDao.update(hv));
    }

    // ==========================================
    // NHÓM 5: TEST delete()
    // ==========================================
    @Test
    @DisplayName("delete() - Xoá học viên thành công")
    void delete_Success() throws SQLException {
        int idXoa = 99;
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertTrue(hocVienDao.delete(idXoa));
        verify(mockPreparedStatement).setInt(1, idXoa);
    }

    @Test
    @DisplayName("delete() - Bị chặn xoá do ràng buộc (Exception)")
    void delete_Exception() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Lỗi cấp quyền xóa khoá ngoại"));

        assertFalse(hocVienDao.delete(99));
    }
}