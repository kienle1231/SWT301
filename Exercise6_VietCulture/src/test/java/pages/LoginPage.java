package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    // URL của trang login
    private final String LOGIN_URL = "http://localhost:8080/Travel/login";
    
    // Locators
    private final By emailField = By.id("email");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By errorMessage = By.cssSelector(".alert-danger");
    private final By successMessage = By.cssSelector(".alert-success");
    // Các locator có thể xuất hiện sau khi đăng nhập thành công
    private final By logoutLink = By.linkText("Đăng Xuất");
    private final By userMenuLink = By.linkText("Tài khoản");
    private final By profileLink = By.linkText("Thông tin cá nhân");
    private final By userDropdownToggle = By.cssSelector(".dropdown-toggle");
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    // Điều hướng đến trang đăng nhập
    public void navigate() {
        navigateTo(LOGIN_URL);
    }
    
    // Đăng nhập với email và password
    public void login(String email, String password) {
        type(emailField, email);
        type(passwordField, password);
        click(loginButton);
    }
    
    // Kiểm tra thông báo lỗi
    public boolean isErrorDisplayed() {
        try {
            // Kiểm tra thông báo lỗi chuẩn (alert-danger)
            if (isElementVisible(errorMessage)) {
                return true;
            }
            
            // Kiểm tra lỗi HTML5 validation trên trường email
            try {
                WebElement email = driver.findElement(emailField);
                String validity = email.getAttribute("validity");
                boolean emailInvalid = email.getAttribute("required") != null && email.getAttribute("value").isEmpty();
                System.out.println("Email field required: " + (email.getAttribute("required") != null));
                System.out.println("Email field empty: " + email.getAttribute("value").isEmpty());
                System.out.println("Email validity: " + validity);
                
                if (emailInvalid) {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Không thể kiểm tra validation của trường email: " + e.getMessage());
            }
            
            // Kiểm tra lỗi HTML5 validation trên trường password
            try {
                WebElement password = driver.findElement(passwordField);
                boolean passwordInvalid = password.getAttribute("required") != null && password.getAttribute("value").isEmpty();
                System.out.println("Password field required: " + (password.getAttribute("required") != null));
                System.out.println("Password field empty: " + password.getAttribute("value").isEmpty());
                
                if (passwordInvalid) {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Không thể kiểm tra validation của trường password: " + e.getMessage());
            }
            
            // Nếu form không submit khi có trường trống, coi là có lỗi
            if (driver.getCurrentUrl().contains("/login")) {
                WebElement emailInput = driver.findElement(emailField);
                WebElement passwordInput = driver.findElement(passwordField);
                
                if (emailInput.getAttribute("value").isEmpty() || passwordInput.getAttribute("value").isEmpty()) {
                    System.out.println("Form không submit vì có trường trống -> coi là có lỗi");
                    return true;
                }
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra hiển thị lỗi: " + e.getMessage());
            return false;
        }
    }
    
    // Lấy nội dung thông báo lỗi
    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    // Đăng xuất
    public void logout() {
        try {
            // Mở dropdown menu người dùng
            if (isElementVisible(userDropdownToggle)) {
                click(userDropdownToggle);
                // Chờ một chút để dropdown hiển thị
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Click vào link đăng xuất
                if (isElementVisible(logoutLink)) {
                    click(logoutLink);
                    
                    // Chờ đăng xuất hoàn tất và chuyển hướng đến trang đăng nhập
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            // Nếu không tìm thấy nút đăng xuất, điều hướng trực tiếp đến URL đăng xuất
            if (!driver.getCurrentUrl().contains("/login")) {
                navigateTo("http://localhost:8080/Travel/logout");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đăng xuất: " + e.getMessage());
        }
    }
    
    // Kiểm tra đăng nhập thành công
    public boolean isLoginSuccessful() {
        try {
            // In ra URL hiện tại để debug
            System.out.println("Checking login success. Current URL: " + driver.getCurrentUrl());
            
            // Kiểm tra nhiều dấu hiệu của đăng nhập thành công
            
            // 1. URL chuyển hướng
            boolean urlChanged = !driver.getCurrentUrl().contains("/login");
            System.out.println("URL changed from login page: " + urlChanged);
            
            // 2. Kiểm tra các phần tử xuất hiện khi đăng nhập thành công
            boolean hasLogout = false;
            boolean hasUserMenu = false;
            boolean hasProfile = false;
            boolean hasDropdownToggle = false;
            
            try {
                hasLogout = driver.findElement(logoutLink).isDisplayed();
                System.out.println("Logout link found: " + hasLogout);
            } catch (NoSuchElementException e) {
                // Không tìm thấy phần tử
            }
            
            try {
                hasUserMenu = driver.findElement(userMenuLink).isDisplayed();
                System.out.println("User menu found: " + hasUserMenu);
            } catch (NoSuchElementException e) {
                // Không tìm thấy phần tử
            }
            
            try {
                hasProfile = driver.findElement(profileLink).isDisplayed();
                System.out.println("Profile link found: " + hasProfile);
            } catch (NoSuchElementException e) {
                // Không tìm thấy phần tử
            }
            
            try {
                hasDropdownToggle = driver.findElement(userDropdownToggle).isDisplayed();
                System.out.println("User dropdown toggle found: " + hasDropdownToggle);
            } catch (NoSuchElementException e) {
                // Không tìm thấy phần tử
            }
            
            // 3. Kiểm tra không còn form đăng nhập
            boolean loginFormGone = false;
            try {
                loginFormGone = !driver.findElement(loginButton).isDisplayed();
            } catch (NoSuchElementException e) {
                loginFormGone = true;
            }
            System.out.println("Login form gone: " + loginFormGone);
            
            // Kết quả: đăng nhập thành công nếu URL thay đổi và có ít nhất một dấu hiệu khác
            return urlChanged && (hasLogout || hasUserMenu || hasProfile || hasDropdownToggle || loginFormGone);
            
        } catch (Exception e) {
            System.out.println("Exception while checking login success: " + e.getMessage());
            return false;
        }
    }
} 