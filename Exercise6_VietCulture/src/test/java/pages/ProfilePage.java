package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProfilePage extends BasePage {
    // URL của trang profile
    private final String PROFILE_URL = "http://localhost:8080/Travel/profile";
    
    // Locators - cập nhật dựa trên HTML chính xác từ profile.jsp
    // Form cập nhật thông tin cá nhân
    private final By fullNameField = By.id("fullName");
    private final By phoneField = By.id("phone");
    private final By dateOfBirthField = By.id("dateOfBirth");
    private final By genderSelect = By.id("gender");
    private final By bioField = By.id("bio");
    private final By avatarUpload = By.id("avatarFile");
    
    // Thay đổi selector để tìm nút lưu thay đổi chính xác hơn
    private final By saveButton = By.xpath("//form[contains(@action, '/profile') or contains(@action, '/update')]//button[@type='submit']");
    
    // Locators cho các trường form của HOST
    private final By businessNameField = By.id("businessName");
    private final By businessAddressField = By.id("businessAddress");
    private final By businessDescriptionField = By.id("businessDescription");
    private final By regionSelect = By.id("region");
    private final By skillsField = By.id("skills");
    
    // Thông báo
    private final By successMessage = By.cssSelector(".alert-success");
    private final By errorMessage = By.cssSelector(".alert-danger");
    
    // Tabs - cập nhật dựa trên HTML chính xác
    private final By editProfileTab = By.cssSelector("button#edit-tab");
    private final By passwordTab = By.cssSelector("button#password-tab");
    
    // Profile header và avatar
    private final By profileHeader = By.xpath("//div[contains(@class,'profile-header')]");
    private final By profileAvatar = By.id("profileAvatar");
    
    // Form
    private final By profileForm = By.xpath("//form[contains(@action, '/profile/update')]");
    
    // Kiểm tra chuyển hướng đến trang đăng nhập
    private final By loginForm = By.id("loginForm");
    
    public ProfilePage(WebDriver driver) {
        super(driver);
    }
    
    // Điều hướng đến trang profile
    public void navigate() {
        navigateTo(PROFILE_URL);
        waitForPageLoad();
    }
    
    // Kiểm tra đã load trang profile thành công
    public boolean isProfilePageLoaded() {
        try {
            // Đợi 5 giây để trang load
            sleep(5000);
            
            // In ra URL và title để debug
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page title: " + driver.getTitle());
            
            // Kiểm tra URL có chứa "profile"
            boolean urlContainsProfile = driver.getCurrentUrl().contains("profile");
            
            // Kiểm tra title có chứa "Hồ Sơ" hoặc "Profile"
            boolean titleContainsProfile = driver.getTitle().contains("Hồ Sơ") || 
                                         driver.getTitle().contains("Profile");
            
            // Kiểm tra phần header của profile có xuất hiện
            boolean hasProfileHeader = isElementPresent(profileHeader);
            
            // In kết quả kiểm tra
            System.out.println("URL contains 'profile': " + urlContainsProfile);
            System.out.println("Title contains 'Hồ Sơ' or 'Profile': " + titleContainsProfile);
            System.out.println("Has profile header: " + hasProfileHeader);
            
            return urlContainsProfile && (titleContainsProfile || hasProfileHeader);
        } catch (Exception e) {
            System.out.println("Error checking if profile page is loaded: " + e.getMessage());
            return false;
        }
    }
    
    // Helper method để chờ trang load
    private void waitForPageLoad() {
        try {
            // Đợi 5 giây cho trang load
            sleep(5000);
            System.out.println("Waiting for page to load...");
            System.out.println("Current URL: " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("Error waiting for page to load: " + e.getMessage());
        }
    }
    
    // Kiểm tra có form cập nhật thông tin không
    public boolean hasProfileForm() {
        try {
            // Đợi form hiển thị
            sleep(2000);
            boolean hasForm = isElementPresent(profileForm);
            boolean hasFullNameField = isElementPresent(fullNameField);
            boolean hasPhoneField = isElementPresent(phoneField);
            
            System.out.println("Has form: " + hasForm);
            System.out.println("Has fullName field: " + hasFullNameField);
            System.out.println("Has phone field: " + hasPhoneField);
            
            return hasFullNameField && hasPhoneField;
        } catch (Exception e) {
            System.out.println("Error checking profile form: " + e.getMessage());
            return false;
        }
    }
    
    // Kiểm tra người dùng có bị chuyển hướng đến trang đăng nhập
    public boolean isRedirectedToLogin() {
        try {
            // Kiểm tra URL hiện tại
            boolean urlContainsLogin = driver.getCurrentUrl().contains("/login");
            
            // Kiểm tra có form đăng nhập không
            boolean hasLoginForm = false;
            try {
                hasLoginForm = driver.findElement(loginForm).isDisplayed();
            } catch (Exception e) {
                // Form không tồn tại
            }
            
            System.out.println("URL hiện tại: " + driver.getCurrentUrl());
            System.out.println("URL chứa /login: " + urlContainsLogin);
            System.out.println("Có form đăng nhập: " + hasLoginForm);
            
            return urlContainsLogin || hasLoginForm;
            
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra chuyển hướng: " + e.getMessage());
            return false;
        }
    }
    
    // Cập nhật thông tin profile cơ bản
    public void updateBasicInfo(String fullName, String phone, LocalDate dob, String gender, String bio) {
        // Đảm bảo trang đã load
        waitForPageLoad();
        
        // Kiểm tra có form không trước khi cập nhật
        if (!hasProfileForm()) {
            System.out.println("Không tìm thấy form cập nhật thông tin!");
            // In ra source HTML để debug
            System.out.println("Page source: " + driver.getPageSource().substring(0, 500) + "...");
            return;
        }
        
        // Thử click vào tab nếu có thể thấy
        try {
            if (isElementVisible(editProfileTab)) {
                click(editProfileTab);
            } else {
                System.out.println("Không tìm thấy tab chỉnh sửa, tiếp tục với form hiện tại");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi click vào tab chỉnh sửa: " + e.getMessage());
            // Tiếp tục với form hiện tại
        }
        
        // Cập nhật từng trường
        if (fullName != null) {
            type(fullNameField, fullName);
        }
        
        if (phone != null) {
            type(phoneField, phone);
        }
        
        if (dob != null) {
            String formattedDate = dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            type(dateOfBirthField, formattedDate);
        }
        
        if (gender != null && !gender.isEmpty()) {
            try {
                if (isElementVisible(genderSelect)) {
                    Select genderDropdown = new Select(driver.findElement(genderSelect));
                    genderDropdown.selectByVisibleText(gender);
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi chọn giới tính: " + e.getMessage());
            }
        }
        
        if (bio != null) {
            try {
                if (isElementVisible(bioField)) {
                    type(bioField, bio);
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi cập nhật bio: " + e.getMessage());
            }
        }
        
        // Lưu thay đổi - sử dụng nhiều cách khác nhau để đảm bảo click thành công
        try {
            // Tìm nút lưu
            By saveButtonLocators[] = {
                saveButton,
                By.xpath("//button[contains(text(), 'Lưu Thay Đổi')]"),
                By.xpath("//button[@type='submit']"),
                By.cssSelector("button.btn-primary"),
                By.xpath("//button[contains(@class, 'btn-primary')]")
            };
            
            boolean clickSuccess = false;
            
            for (By locator : saveButtonLocators) {
                if (isElementPresent(locator)) {
                    try {
                        System.out.println("Thử click nút Lưu với selector: " + locator);
                        
                        // Cuộn đến phần tử
                        try {
                            scrollToElement(locator);
                            sleep(1000);
                        } catch (Exception e) {
                            System.out.println("Lỗi khi cuộn đến nút: " + e.getMessage());
                        }
                        
                        // Thử click bình thường
                        try {
                            driver.findElement(locator).click();
                            clickSuccess = true;
                            System.out.println("Click thành công với click thường");
                            break;
                        } catch (Exception e) {
                            System.out.println("Không thể click thường: " + e.getMessage());
                        }
                        
                        // Thử click bằng JavaScript
                        try {
                            clickWithJavaScript(locator);
                            clickSuccess = true;
                            System.out.println("Click thành công với JavaScript");
                            break;
                        } catch (Exception e) {
                            System.out.println("Không thể click bằng JavaScript: " + e.getMessage());
                        }
                        
                        // Thử click bằng Actions
                        try {
                            clickWithActions(locator);
                            clickSuccess = true;
                            System.out.println("Click thành công với Actions");
                            break;
                        } catch (Exception e) {
                            System.out.println("Không thể click bằng Actions: " + e.getMessage());
                        }
                    } catch (Exception e) {
                        System.out.println("Lỗi khi thử click nút với selector " + locator + ": " + e.getMessage());
                    }
                }
            }
            
            if (!clickSuccess) {
                System.out.println("Không thể click vào nút Lưu Thay Đổi bằng mọi cách!");
                
                // Thử submit form trực tiếp
                try {
                    By formLocator = By.xpath("//form[contains(@action, '/profile')]");
                    if (isElementPresent(formLocator)) {
                        System.out.println("Thử submit form trực tiếp...");
                        driver.findElement(formLocator).submit();
                        System.out.println("Submit form thành công");
                    }
                } catch (Exception e) {
                    System.out.println("Không thể submit form: " + e.getMessage());
                }
            }
            
            // Đợi sau khi submit để trang xử lý
            sleep(3000);
            
            // Kiểm tra đã chuyển trang hoặc có thông báo thành công
            if (isSuccessMessageDisplayed()) {
                System.out.println("Thông báo thành công: " + getSuccessMessage());
            } else {
                System.out.println("Không tìm thấy thông báo thành công. URL hiện tại: " + driver.getCurrentUrl());
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi click nút lưu: " + e.getMessage());
        }
    }
    
    // Upload avatar
    public void uploadAvatar(String filePath) {
        try {
            if (isElementPresent(avatarUpload)) {
                uploadFile(avatarUpload, filePath);
            } else {
                System.out.println("Không tìm thấy trường upload avatar!");
                
                // Thử tìm bằng các selector khác
                By alternativeUpload = By.xpath("//input[@type='file' and (contains(@id, 'avatar') or contains(@name, 'avatarFile'))]");
                if (isElementPresent(alternativeUpload)) {
                    System.out.println("Tìm thấy trường upload thay thế");
                    uploadFile(alternativeUpload, filePath);
                } else {
                    System.out.println("Không tìm thấy trường upload nào!");
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi upload avatar: " + e.getMessage());
        }
    }
    
    // Cập nhật thông tin doanh nghiệp (cho HOST)
    public void updateBusinessInfo(String businessName, String businessAddress, 
                                   String businessDesc, String region, String skills) {
        // Đảm bảo trang đã load
        waitForPageLoad();
        
        // Thử click vào tab nếu có thể thấy
        try {
            if (isElementVisible(editProfileTab)) {
                click(editProfileTab);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi click vào tab chỉnh sửa: " + e.getMessage());
            // Tiếp tục với form hiện tại
        }
        
        // Cập nhật từng trường
        if (businessName != null) {
            try {
                if (isElementPresent(businessNameField)) {
                    type(businessNameField, businessName);
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi cập nhật tên doanh nghiệp: " + e.getMessage());
            }
        }
        
        if (businessAddress != null) {
            try {
                if (isElementPresent(businessAddressField)) {
                    type(businessAddressField, businessAddress);
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi cập nhật địa chỉ doanh nghiệp: " + e.getMessage());
            }
        }
        
        if (businessDesc != null) {
            try {
                if (isElementPresent(businessDescriptionField)) {
                    type(businessDescriptionField, businessDesc);
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi cập nhật mô tả doanh nghiệp: " + e.getMessage());
            }
        }
        
        if (region != null && !region.isEmpty()) {
            try {
                if (isElementPresent(regionSelect)) {
                    Select regionDropdown = new Select(driver.findElement(regionSelect));
                    regionDropdown.selectByValue(region);
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi chọn khu vực: " + e.getMessage());
            }
        }
        
        if (skills != null) {
            try {
                if (isElementPresent(skillsField)) {
                    type(skillsField, skills);
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi cập nhật kỹ năng: " + e.getMessage());
            }
        }
        
        // Lưu thay đổi
        try {
            if (isElementVisible(saveButton)) {
                click(saveButton);
            } else {
                // Thử tìm nút lưu bằng XPath khác
                By alternativeSaveButton = By.xpath("//button[contains(text(), 'Lưu') or contains(@class, 'btn-primary')]");
                if (isElementVisible(alternativeSaveButton)) {
                    click(alternativeSaveButton);
                } else {
                    System.out.println("Không tìm thấy nút lưu thay đổi!");
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi click nút lưu: " + e.getMessage());
        }
    }
    
    // Kiểm tra thông báo thành công
    public boolean isSuccessMessageDisplayed() {
        return isElementVisible(successMessage);
    }
    
    // Lấy thông báo thành công
    public String getSuccessMessage() {
        return getText(successMessage);
    }
    
    // Kiểm tra thông báo lỗi
    public boolean isErrorMessageDisplayed() {
        return isElementVisible(errorMessage);
    }
    
    // Lấy thông báo lỗi
    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    // Kiểm tra thông tin hiển thị sau khi cập nhật
    public boolean validateProfileInfo(String expectedName, String expectedPhone) {
        try {
            // Làm mới trang để đảm bảo thông tin được cập nhật
            driver.navigate().refresh();
            waitForPageLoad();
            
            // Nhận giá trị hiện tại của các trường
            String actualName = "";
            String actualPhone = "";
            
            if (isElementPresent(fullNameField)) {
                actualName = driver.findElement(fullNameField).getAttribute("value");
            }
            
            if (isElementPresent(phoneField)) {
                actualPhone = driver.findElement(phoneField).getAttribute("value");
            }
            
            System.out.println("Tên hiện tại: " + actualName);
            System.out.println("SĐT hiện tại: " + actualPhone);
            System.out.println("Tên mong đợi: " + expectedName);
            System.out.println("SĐT mong đợi: " + expectedPhone);
            
            // Kiểm tra giá trị
            boolean nameMatches = (expectedName == null) || expectedName.equals(actualName);
            boolean phoneMatches = (expectedPhone == null) || expectedPhone.equals(actualPhone);
            
            return nameMatches && phoneMatches;
        } catch (Exception e) {
            System.out.println("Lỗi khi xác thực thông tin: " + e.getMessage());
            return false;
        }
    }

    // Thêm các phương thức trợ giúp để click nút
    private void scrollToElement(By locator) {
        try {
            System.out.println("Cuộn đến phần tử: " + locator);
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
                driver.findElement(locator)
            );
        } catch (Exception e) {
            System.out.println("Lỗi khi cuộn đến phần tử: " + e.getMessage());
        }
    }
    
    private void clickWithJavaScript(By locator) {
        System.out.println("Click bằng JavaScript: " + locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(locator));
    }
    
    private void clickWithActions(By locator) {
        System.out.println("Click bằng Actions: " + locator);
        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
        actions.moveToElement(driver.findElement(locator)).click().build().perform();
    }

    // Getters cho các fields để truy cập từ lớp test
    public By getFullNameField() {
        return fullNameField;
    }
    
    public By getPhoneField() {
        return phoneField;
    }
    
    public By getDateOfBirthField() {
        return dateOfBirthField;
    }
    
    public By getGenderSelect() {
        return genderSelect;
    }
    
    public By getBioField() {
        return bioField;
    }
} 