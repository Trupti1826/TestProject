package com.cb.sau;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SauceDemoLoginTest {
	WebDriver driver;
    SoftAssert softAssert = new SoftAssert();

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/index.html");
    }

    @Test(priority = 1)
    public void testSuccessfulLogin() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector(".btn_action")).click();
        String currentPageUrl = driver.getCurrentUrl();
        softAssert.assertEquals(currentPageUrl, "https://www.saucedemo.com/v1/inventory.html");
        softAssert.assertAll();
    }

    @Test(priority = 2)
    public void testLoginWithIncorrectPassword() {
        driver.get("https://www.saucedemo.com/v1/index.html");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.cssSelector(".btn_action")).click();
        WebElement errorMessage = driver.findElement(By.cssSelector(".error-message-container"));
        softAssert.assertEquals(errorMessage.getText(), "Username and password do not match any user in this service.");
        softAssert.assertAll();
        captureScreenshot("loginWithIncorrectPassword");
    }

    @Test(priority = 3)
    public void testLoginWithEmptyUsername() {
        driver.get("https://www.saucedemo.com/v1/index.html");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector(".btn_action")).click();
        WebElement errorMessage = driver.findElement(By.cssSelector(".error-message-container"));
        softAssert.assertEquals(errorMessage.getText(), "Username is required");
        softAssert.assertAll();
        captureScreenshot("loginWithEmptyUsername");
    }

    @Test(priority = 4)
    public void testLoginWithEmptyPassword() {
        driver.get("https://www.saucedemo.com/v1/index.html");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.cssSelector(".btn_action")).click();
        WebElement errorMessage = driver.findElement(By.cssSelector(".error-message-container"));
        softAssert.assertEquals(errorMessage.getText(), "Password is required");
        softAssert.assertAll();
        captureScreenshot("loginWithEmptyPassword");
    }

    public void captureScreenshot(String screenshotss) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            screenshot.renameTo(new File("E:\\screenshotss\\screenshotss.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }


}
