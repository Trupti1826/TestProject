package com.cb.sau;



	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.testng.annotations.AfterTest;
	import org.testng.annotations.BeforeTest;
	import org.testng.annotations.Test;
	import org.testng.asserts.SoftAssert;
	import java.util.List;

	public class ProductListingPageTest {
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
	    public void testProductCount() {
	        List<WebElement> products = driver.findElements(By.cssSelector(".inventory_item"));
	        softAssert.assertEquals(products.size(), 6, "Product count is incorrect");
	        softAssert.assertAll();
	    }

	    @Test(priority = 2)
	    public void testProductDetails() {
	        List<WebElement> products = driver.findElements(By.cssSelector(".inventory_item"));
	        for (WebElement product : products) {
	            softAssert.assertTrue(product.findElement(By.cssSelector(".inventory_item_name")).isDisplayed(), "Product name is not displayed");
	            softAssert.assertTrue(product.findElement(By.cssSelector(".inventory_item_price")).isDisplayed(), "Product price is not displayed");
	            softAssert.assertTrue(product.findElement(By.cssSelector(".inventory_item_img")).isDisplayed(), "Product image is not displayed");
	        }
	        softAssert.assertAll();
	    }

	    @Test(priority = 3)
	    public void testSortFeature() {
	        WebElement sortDropdown = driver.findElement(By.cssSelector(".product_sort_container"));
	        sortDropdown.click();
	        List<WebElement> options = driver.findElements(By.cssSelector(".product_sort_container option"));
	        for (WebElement option : options) {
	            option.click();
	            List<WebElement> products = driver.findElements(By.cssSelector(".inventory_item"));
	            // Add logic to verify sorting
	        }
	        softAssert.assertAll();
	    }

	    @Test(priority = 4)
	    public void testProductInfoConsistency() {
	        List<WebElement> products = driver.findElements(By.cssSelector(".inventory_item"));
	        for (int i = 0; i < products.size(); i++) {
	            String productName = products.get(i).findElement(By.cssSelector(".inventory_item_name")).getText();
	            String productPrice = products.get(i).findElement(By.cssSelector(".inventory_item_price")).getText();
	            products.get(i).findElement(By.cssSelector(".inventory_item_img")).click();
	            softAssert.assertEquals(driver.findElement(By.cssSelector(".inventory_details_name")).getText(), productName, "Product name is not consistent");
	            softAssert.assertEquals(driver.findElement(By.cssSelector(".inventory_details_price")).getText(), productPrice, "Product price is not consistent");
	            driver.navigate().back();
	            products = driver.findElements(By.cssSelector(".inventory_item"));
	        }
	        softAssert.assertAll();
	    }

	    @AfterTest
	    public void tearDown() {
	        driver.quit();
	    }
	

}
