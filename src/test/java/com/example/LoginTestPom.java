package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginTestPom {
    WebDriver driver;
    WebDriverWait wait;
    LoginPagePom loginPage;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");        // GUI olmadan çalıştır
        options.addArguments("--disable-gpu");     // GPU hatalarını önler
        options.addArguments("--window-size=1920,1080"); // Sayfa boyutu
        options.addArguments("--no-sandbox");      // Linux CI için gerekli
        options.addArguments("--disable-dev-shm-usage"); // Linux memory hatalarını önler

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPagePom(driver);
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void loginWithValidCredentials() {
        loginPage.login("standard_user", "secret_sauce");
        WebElement inventoryList = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.className("inventory_list")
            )
        );
        Assert.assertTrue(inventoryList.isDisplayed(),
            "Inventory page is not displayed after login.");
    }
    @Test
    public void loginWithInvalidCredentials() {

        loginPage.login("standard_user", "wrong_password");

        Assert.assertTrue(
                loginPage.isErrorDisplayed(),
                "Error message should be displayed"
        );
    }
}
