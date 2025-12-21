package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.chrome.ChromeOptions;

public class InventoryTest {
    WebDriver driver;
    WebDriverWait wait;
    LoginPagePom loginPage;
    InventoryPage inventoryPage;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");        
        options.addArguments("--disable-gpu");     
        options.addArguments("--window-size=1920,1080"); 
        options.addArguments("--no-sandbox");      
        options.addArguments("--disable-dev-shm-usage"); 

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPagePom(driver);
        inventoryPage = new InventoryPage(driver);
        loginPage.login("standard_user", "secret_sauce");
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void addBackpackToCart() {
        inventoryPage.clickBackpack();
        String price = inventoryPage.getBackpackPrice();
        Assert.assertEquals(price, "$29.99", "Backpack price does not match.");
        
        inventoryPage.clickAddToCart();
        String badgeCount = inventoryPage.getCartBadgeCount(wait);
        Assert.assertEquals(badgeCount, "1", "Cart badge count should be 1 after adding an item.");
    }
}
