package com.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InventoryPage {
    private WebDriver driver;

    // LOCATORS
    private By backPackItem = By.id("item_4_img_link");
    private By priceItem = By.className("inventory_details_price");
    private By addToCartButton = By.id("add-to-cart");
    private By shoppingCartBadge = By.className("shopping_cart_badge");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickBackpack() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(backPackItem)).click();
    }

    public String getBackpackPrice() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(priceItem)).getText();
    }

    public void clickAddToCart() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        
        try {
            button.click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", button);
        }
    }

    public String getCartBadgeCount(WebDriverWait wait) {
        WebElement badge = wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartBadge));
        wait.until(driver -> !badge.getText().trim().isEmpty());
        String badgeText = badge.getText().trim();
        System.out.println("DEBUG: Badge text retrieved: '" + badgeText + "' (length: " + badgeText.length() + ")");
        return badgeText;
    }
}