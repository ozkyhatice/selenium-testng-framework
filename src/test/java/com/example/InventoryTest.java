package com.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class InventoryTest {
    WebDriver driver;
    WebDriverWait wait;
    LoginPagePom loginPage;
    InventoryPage inventoryPage;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); 
        options.addArguments("--disable-gpu");     
        options.addArguments("--window-size=1920,1080"); 
        options.addArguments("--no-sandbox");      
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--proxy-server='direct://'");
        options.addArguments("--proxy-bypass-list=*");
        options.addArguments("--blink-settings=imagesEnabled=false"); // Hız için resimleri devre dışı bırakabilir

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); 
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPagePom(driver);
        inventoryPage = new InventoryPage(driver);
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "userData")
    public Object[][] userData() {
        return new Object[][] {
            {"standard_user", "secret_sauce", "$29.99"},
            // problem_user is intentionally buggy and cart functionality doesn't work properly
            // {"problem_user", "secret_sauce", "$49.99"}, 
        };
    }
    @Test(retryAnalyzer = Retry.class)
    public void addBackpackToCart() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.clickBackpack();
        String price = inventoryPage.getBackpackPrice();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(price, "$29.99", "Backpack price does not match.");
        inventoryPage.clickAddToCart();
        String badgeCount = inventoryPage.getCartBadgeCount(wait);
        softAssert.assertEquals(badgeCount, "1", "Cart badge count should be 1 after adding an item.");
        softAssert.assertAll();
    }

    // @Test(dataProvider = "userData")
    // public void addBackpackToCart(String username, String password, String expectedPrice) {
    //     loginPage.login(username, password);
    //     inventoryPage.clickBackpack();
    //     String price = inventoryPage.getBackpackPrice();
    //     Assert.assertEquals(price, expectedPrice, "Backpack price does not match for user: " + username);
        
    //     inventoryPage.clickAddToCart();
    //     String badgeCount = inventoryPage.getCartBadgeCount(wait);
    //     System.out.println("Badge count for user " + username + ": " + badgeCount);
    //     // Assert.assertEquals(badgeCount, "1", "Cart badge count should be 1 after adding an item.");
    // }

    @DataProvider(name = "problemUserData")
    public Object[][] problemUserData() {
        return new Object[][] {
            {"problem_user", "secret_sauce", "$49.99"},
        };
    }
    @Test
    public void verifyProblemUserPriceIssue() {
        loginPage.login("problem_user", "secret_sauce");
        inventoryPage.clickBackpack();
        String price = inventoryPage.getBackpackPrice();
        Assert.assertEquals(price, "$49.99", "Problem user should show incorrect price $49.99");
        }

    @Test
    public void validateInventoryPageUI() {
        loginPage.login("standard_user", "secret_sauce");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(
            inventoryPage.isInventoryPageDisplayed(), 
            "Inventory page is not displayed."
        );
        int itemCount = inventoryPage.getInventoryItemCount();
        softAssert.assertEquals(
            itemCount, 
            6, 
            "Inventory item count mismatch."
        );
        softAssert.assertTrue(
            inventoryPage.isCartIconDisplayed(),    
            "Shopping cart icon is not displayed."
        );
        softAssert.assertAll();
    }
    
}
