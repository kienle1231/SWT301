package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private By firstNameField = By.id("firstName");
    private By lastNameField = By.id("lastName");
    private By genderMaleRadio = By.xpath("//label[text()='Male']");
    private By userNumberField = By.id("userNumber");
    private By uploadPicture = By.id("uploadPicture");
    private By addressField = By.id("currentAddress");
    private By submitButton = By.id("submit");
    private By successPopup = By.id("example-modal-sizes-title-lg");

    // Actions
    public void navigate() {
        navigateTo("https://demoqa.com/automation-practice-form");
    }

    public void enterFirstName(String name) {
        type(firstNameField, name);
    }

    public void enterLastName(String name) {
        type(lastNameField, name);
    }

    public void selectGenderMale() {
        click(genderMaleRadio);
    }

    public void enterMobile(String phone) {
        type(userNumberField, phone);
    }

    public void uploadFile(String path) {
        waitForVisibility(uploadPicture).sendKeys(path);
    }

    public void enterAddress(String addr) {
        type(addressField, addr);
    }

    public void submit() {
        // Sử dụng JavaScript để click tránh lỗi quảng cáo che
        jsClick(submitButton);
    }

    public String getPopupText() {
        return getText(successPopup);
    }
}
