package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void waitForVisibility(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        waitForVisibility(locator);
        driver.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        waitForVisibility(locator);
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }

    protected String getText(By locator) {
        waitForVisibility(locator);
        return driver.findElement(locator).getText();
    }

    protected boolean isElementVisible(By locator) {
        try {
            waitForVisibility(locator);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Thêm phương thức để kiểm tra phần tử tồn tại không cần đợi hiển thị
    protected boolean isElementPresent(By locator) {
        try {
            return driver.findElements(locator).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Thêm phương thức sleep để đợi cố định
    protected void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // Đợi cho JavaScript tải xong
    protected void waitForJStoLoad() {
        try {
            wait.until(driver -> {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                return (Boolean) jsExecutor.executeScript("return document.readyState === 'complete'");
            });
        } catch (Exception e) {
            System.out.println("Lỗi khi đợi JavaScript load: " + e.getMessage());
        }
    }

    protected void navigateTo(String url) {
        driver.get(url);
    }

    protected void uploadFile(By locator, String filePath) {
        driver.findElement(locator).sendKeys(filePath);
    }
} 