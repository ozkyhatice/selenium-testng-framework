package com.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class LoginTest {
    @Test
    public void loginWithValidCredentials() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name"))
            .sendKeys("standard_user");;
        driver.findElement(By.id("password"))
            .sendKeys("secret_sauce");
        driver.findElement(By.id("login-button"))
            .click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement inventoryList = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.className("inventory_list"))
        );
        Assert.assertTrue(inventoryList.isDisplayed(),
            "Inventory page is not displayed after login.");
        // boolean inventoryVisible = driver.findElement(By.className("inventory_list"))
        //     .isDisplayed();
        // Assert.assertTrue(inventoryVisible, "Inventory page is not displayed after login.");
        driver.quit();
    }
    @Test
    public void loginWithInvalidCredentials() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name"))
            .sendKeys("standard_user");
        driver.findElement(By.id("password"))
            .sendKeys("wrong_password");
        driver.findElement(By.id("login-button"))
            .click();
        boolean errorVisible = driver.findElement(By.cssSelector("h3[data-test='error']"))
            .isDisplayed();
        Assert.assertTrue(errorVisible, "Error message is not displayed for invalid login.");
        driver.quit();
    }
}
