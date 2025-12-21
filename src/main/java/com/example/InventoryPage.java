package com.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
        driver.findElement(backPackItem)
            .click();
    }
    public String getBackpackPrice() {
        return driver.findElement(priceItem)
            .getText();
    }
    public void clickAddToCart() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        driver.findElement(addToCartButton)
            .click();

            try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public String getCartBadgeCount(WebDriverWait wait) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartBadge));
        return driver.findElement(shoppingCartBadge)
            .getText();
    }
}
