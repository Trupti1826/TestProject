package com.cb.sau;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CartFunctionalityTest {
	 WebDriver driver;
	    SoftAssert softAssert = new SoftAssert();

	    @BeforeTest
	    public void setup() {
	        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
	        driver = new ChromeDriver();
	        driver.get("https://www.saucedemo.com/v1/index.html");
	        driver.findElement(By.id("user-name")).sendKeys("standard_user");
	        driver.findElement(By.id("password")).sendKeys("secret_sauce");
	        driver.findElement(By.cssSelector(".btn_action")).click();
	    }

	    @Test(priority = 1)
	    public void testAddProductToCart() {
	        driver.findElement(By.cssSelector("#item_4_title_link + .pricebar > .btn_primary")).click();
	        String cartBadge = driver.findElement(By.cssSelector(".shopping_cart_badge")).getText();
	        softAssert.assertEquals(cartBadge, "1", "Cart badge count is incorrect");
	        softAssert.assertAll();
	    }

	    @Test(priority = 2)
	    public void testAddMultipleProductsToCart() {
	        driver.findElement(By.cssSelector("#item_4_title_link + .pricebar > .btn_primary")).click();
	        driver.findElement(By.cssSelector("#item_0_title_link + .pricebar > .btn_primary")).click();
	        String cartBadge = driver.findElement(By.cssSelector(".shopping_cart_badge")).getText();
	        softAssert.assertEquals(cartBadge, "2", "Cart badge count is incorrect");
	        softAssert.assertAll();
	    }

	    @Test(priority = 3)
	    public void testRemoveProductFromCart() {
	        driver.findElement(By.cssSelector("#item_4_title_link + .pricebar > .btn_primary")).click();
	        driver.findElement(By.cssSelector("#item_0_title_link + .pricebar > .btn_primary")).click();
	        driver.findElement(By.cssSelector(".shopping_cart_link")).click();
	        driver.findElement(By.cssSelector(".cart_button")).click();
	        String cartBadge = driver.findElement(By.cssSelector(".shopping_cart_badge")).getText();
	        softAssert.assertEquals(cartBadge, "1", "Cart badge count is incorrect");
	        softAssert.assertAll();
	    }

	    @Test(priority = 4)
	    public void testCartPage() {
	        driver.findElement(By.cssSelector("#item_4_title_link + .pricebar > .btn_primary")).click();
	        driver.findElement(By.cssSelector(".shopping_cart_link")).click();
	        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart_item"));
	        softAssert.assertEquals(cartItems.size(), 1, "Cart item count is incorrect");
	        softAssert.assertTrue(driver.findElement(By.cssSelector(".inventory_item_name")).isDisplayed(), "Product name is not displayed");
	        softAssert.assertTrue(driver.findElement(By.cssSelector(".inventory_item_price")).isDisplayed(), "Product price is not displayed");
	        softAssert.assertAll();
	    }

	    @AfterTest
	    public void tearDown() {
	        driver.quit();
	    }
}
