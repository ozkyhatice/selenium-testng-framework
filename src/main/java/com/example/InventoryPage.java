package com.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class InventoryPage {
    private WebDriver driver;

    //LOCATORS
    private By backPackItem = By.id("item_4_img_link");
    private By priceItem = By.className("inventory_details_price");
    private By addToCartButton = By.id("add-to-cart");
    private By shoppingCartBadge = By.className("shopping_cart_badge");

    //CONSTRUCTOR
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }
    //ACTIONS
    public void clickBackpack() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(backPackItem));
        driver.findElement(backPackItem)
            .click();
    }
    public String getBackpackPrice() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(priceItem));
        return driver.findElement(priceItem)
            .getText();
    }
    public void clickAddToCart() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        driver.findElement(addToCartButton).click();
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
        
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public String getCartBadgeCount(WebDriverWait wait) {
        WebDriverWait extendedWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(30));
        
        extendedWait.until(ExpectedConditions.presenceOfElementLocated(shoppingCartBadge));
        
        extendedWait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartBadge));
        
        extendedWait.until(driver -> {
            String text = driver.findElement(shoppingCartBadge).getText();
            return text != null && !text.isEmpty();
        });
        
        return driver.findElement(shoppingCartBadge).getText();
    }
}
