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

public class LoginTest2 {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test 
    public void loginWithValidCredentials() {
        driver.findElement(By.id("user-name"))
            .sendKeys("standard_user");
        driver.findElement(By.id("password"))
            .sendKeys("secret_sauce");
        driver.findElement(By.id("login-button"))
            .click();
        WebElement inventoryList = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.className("inventory_list")
            )
        );
        Assert.assertTrue(inventoryList.isDisplayed(),
            "Inventory page is not displayed after login with invalid credentials.");
    }
    @Test
    public void loginWithInvalidCredentials() {

        driver.findElement(By.id("user-name"))
                .sendKeys("standard_user");

        driver.findElement(By.id("password"))
                .sendKeys("wrong_password");

        driver.findElement(By.id("login-button"))
                .click();

        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("h3[data-test='error']")
                )
        );

        Assert.assertTrue(
                errorMessage.isDisplayed(),
                "Error message should be visible for invalid login"
        );
    }
}
