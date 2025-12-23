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
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import org.testng.annotations.DataProvider;

public class LoginTestPom {
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
        inventoryPage = new InventoryPage(driver);}
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
    //Login with DataProvider
    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
            {"standard_user", "secret_sauce", true},
            {"locked_out_user", "secret_sauce", false},
            {"problem_user", "secret_sauce", true},
            {"performance_glitch_user", "secret_sauce", true},
            {"standard_user", "wrong_password", false}  

        };
    }
    @Test(retryAnalyzer = Retry.class, dataProvider = "loginData")
    public void loginWithDataProvider(String username, String password, boolean isSuccessExpected) {
        loginPage.login(username, password);
        SoftAssert softAssert = new SoftAssert();

        if (isSuccessExpected) {
            softAssert.assertTrue(
                inventoryPage.isInventoryPageDisplayed(),
                "Inventory page should be displayed for user: " + username
            );
        } else {
            softAssert.assertTrue(
                loginPage.isErrorDisplayed(),
                "Error message should be displayed for user: " + username
            );
        }
        softAssert.assertAll();
        System.out.println("Login test completed for user: " + username);
    }
    @Test(
        dataProvider = "loginData",
        groups = "smoke"
    )
    public void loginWithSmoke(
            String username, 
            String password, 
            boolean isSuccessExpected) {

        if (!isSuccessExpected) {
            return; 
        }
        loginPage.login(username, password);
        SoftAssert softAssert = new SoftAssert();

        
        softAssert.assertTrue(
            inventoryPage.isInventoryPageDisplayed(),
            "Inventory page should be displayed for user: " + username
        );
        
        softAssert.assertAll();
    }
    
}
