package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.ProfilePage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Kiểm thử chức năng đăng nhập")
public class LoginTest extends BaseTest {
    static LoginPage loginPage;
    static ProfilePage profilePage;
    static WebDriverWait wait;

    @BeforeAll
    static void initPage() {
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @Order(1)
    @DisplayName("Đăng nhập thành công với thông tin hợp lệ")
    void testLoginSuccess() {
        loginPage.navigate();
        loginPage.login("tr1@example.com", "123456");
        
        // In thêm thông tin để debug
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Source: " + driver.getPageSource().substring(0, 500)); // Lấy 500 ký tự đầu tiên
        
        // Thêm thời gian chờ
        try {
            Thread.sleep(2000); // Chờ 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Đợi và kiểm tra đã đăng nhập thành công
        assertTrue(loginPage.isLoginSuccessful(), "Đăng nhập không thành công");
        
        // Đăng xuất sau khi đã kiểm tra thành công
        loginPage.logout();
    }

    @Test
    @Order(2)
    @DisplayName("Đăng nhập thất bại với thông tin không hợp lệ")
    void testLoginFail() {
        loginPage.navigate();
        loginPage.login("invalid@email.com", "wrongpassword");
        
        // Đợi và kiểm tra thông báo lỗi
        assertTrue(loginPage.isErrorDisplayed(), "Không hiển thị thông báo lỗi");
        
        // In ra thông báo lỗi thực tế để debug
        String actualErrorMessage = loginPage.getErrorMessage();
        System.out.println("Thông báo lỗi thực tế: [" + actualErrorMessage + "]");
        
        // Kiểm tra thông báo lỗi chứa bất kỳ chuỗi nào trong danh sách các chuỗi mong đợi
        boolean containsExpectedText = actualErrorMessage.contains("không hợp lệ") || 
                                     actualErrorMessage.contains("invalid") ||
                                     actualErrorMessage.contains("incorrect") || 
                                     actualErrorMessage.contains("sai") ||
                                     actualErrorMessage.contains("không đúng") ||
                                     actualErrorMessage.toLowerCase().contains("email") ||
                                     actualErrorMessage.toLowerCase().contains("password");
        
        assertTrue(containsExpectedText, 
                "Thông báo lỗi không chứa bất kỳ nội dung mong đợi nào. Thông báo thực tế: " + actualErrorMessage);
    }

    @ParameterizedTest(name = "Đăng nhập với: {0} / {1}")
    @Order(3)
    @CsvFileSource(resources = "/login-data.csv", numLinesToSkip = 1)
    @DisplayName("Kiểm thử đăng nhập với nhiều bộ dữ liệu")
    void testLoginWithCSV(String email, String password, String expected) {
        loginPage.navigate();
        email = (email == null) ? "" : email.trim();
        password = (password == null) ? "" : password.trim();

        loginPage.login(email, password);

        // Thêm thời gian chờ
        try {
            Thread.sleep(2000); // Chờ 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (expected.equals("success")) {
            assertTrue(loginPage.isLoginSuccessful(), 
                    "Đăng nhập không thành công với " + email + "/" + password);
                    
            // Đăng xuất sau khi đăng nhập thành công để test lần tiếp theo
            loginPage.logout();
        } else {
            assertTrue(loginPage.isErrorDisplayed(), 
                    "Không hiển thị lỗi khi đăng nhập với " + email + "/" + password);
        }
    }
    
    @Test
    @Order(4)
    @DisplayName("Kiểm tra truy cập trang được bảo vệ sau khi đăng xuất")
    void testAccessProtectedPageAfterLogout() {
        // Đầu tiên: đăng nhập
        loginPage.navigate();
        loginPage.login("tr1@example.com", "123456");
        
        try {
            Thread.sleep(2000); // Chờ 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Kiểm tra đăng nhập thành công
        assertTrue(loginPage.isLoginSuccessful(), "Đăng nhập không thành công");
        
        // Đăng xuất
        loginPage.logout();
        
        try {
            Thread.sleep(2000); // Chờ 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Cố gắng truy cập trang profile (một trang được bảo vệ)
        profilePage.navigate();
        
        try {
            Thread.sleep(2000); // Chờ 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Sau khi đăng xuất, truy cập trang được bảo vệ phải chuyển hướng đến trang đăng nhập
        assertTrue(profilePage.isRedirectedToLogin(), 
                "Không chuyển hướng đến trang đăng nhập khi truy cập trang được bảo vệ");
                
        System.out.println("URL sau khi cố gắng truy cập trang được bảo vệ: " + driver.getCurrentUrl());
    }
    
    @AfterEach
    void cleanupAfterTest() {
        // Đảm bảo đăng xuất sau mỗi test case
        if (driver.getCurrentUrl() != null && !driver.getCurrentUrl().contains("/login")) {
            loginPage.logout();
        }
    }
} 