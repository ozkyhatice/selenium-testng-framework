package com.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    @DataProvider(name = "userData2")
    public Object[][] userData2() {
        return new Object[][] {
            {"standard_user", "secret_sauce"},
        };
    }
    @Test(retryAnalyzer = Retry.class, dataProvider = "userData2")
    public void checkItemPricesConsistency(String username, String password) {
        loginPage.login(username, password);
        By item1 = By.id("item_4_img_link"); // Backpack
        By item2 = By.id("item_0_title_link"); // Bike Light
        By item3 = By.id("item_1_title_link"); // Bolt T-Shirt

        // price controls
        String price1Main = inventoryPage.getItemPriceMainPage(item1);
        String price1Detail = inventoryPage.getItemPriceDetailPage(item1);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(
            price1Main, 
            price1Detail, 
            "Price mismatch for Backpack between main and detail page."
        );
        String price2Main = inventoryPage.getItemPriceMainPage(item2);
        String price2Detail = inventoryPage.getItemPriceDetailPage(item2);
        softAssert.assertEquals(
            price2Main, 
            price2Detail, 
            "Price mismatch for Bike Light between main and detail page."
        );
        String price3Main = inventoryPage.getItemPriceMainPage(item3);
        String price3Detail = inventoryPage.getItemPriceDetailPage(item3);
        softAssert.assertEquals(
            price3Main, 
            price3Detail, 
            "Price mismatch for Bolt T-Shirt between main and detail page."
        );
        softAssert.assertAll();
    }
    @DataProvider(name = "itemsAndUsers")
    public Object[][] itemsAndUsers() {
        return new Object[][] {
            {"item_4_title_link", "standard_user", "secret_sauce"},
            {"item_0_title_link", "standard_user", "secret_sauce"},
            {"item_1_title_link", "standard_user", "secret_sauce"}
        };
    }

    @DataProvider(name = "singleUser")
    public Object[][] singleUser() {
        return new Object[][] {
            {"standard_user", "secret_sauce"}
        };
    }

    @Test(retryAnalyzer = Retry.class, dataProvider = "itemsAndUsers")
    public void addCartItems(String itemId, String username, String password) {
        loginPage.login(username, password);
        driver.findElement(By.id(itemId)).click();
        inventoryPage.clickAddToCart();
        String badgeCount = inventoryPage.getCartBadgeCount(wait);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(badgeCount, "1", "Cart badge count should be 1 after adding an item.");
        softAssert.assertAll();
    }

    // add all item from data provider to cart and verify badge count
    @Test(retryAnalyzer = Retry.class, dataProvider = "singleUser")
    public void addMultipleItemsToCart(String username, String password) {
        loginPage.login(username, password);
        int itemCount = 0;
        String[] itemsToAdd = {"item_4_title_link", "item_0_title_link", "item_1_title_link"};
        SoftAssert softAssert = new SoftAssert();

        for (String itemId : itemsToAdd) {
            wait.until(driver -> inventoryPage.isInventoryPageDisplayed());
            
            wait.until(ExpectedConditions.elementToBeClickable(By.id(itemId))).click();
            
            inventoryPage.clickAddToCart();
            
            // Wait a moment for cart to update
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            driver.navigate().back();
            wait.until(driver -> inventoryPage.isInventoryPageDisplayed());

            itemCount++;
            String badgeCount = inventoryPage.getCartBadgeCount(wait);
            softAssert.assertEquals(badgeCount, String.valueOf(itemCount), "Cart badge count should be " + itemCount + " after adding an item.");
        }
        softAssert.assertAll();
    }
}
