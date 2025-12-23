package com.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class InventoryPage {
    private WebDriver driver;

    // LOCATORS
    private By backPackItem = By.id("item_4_img_link");
    private By priceItem = By.className("inventory_details_price");
    private By addToCartButton = By.id("add-to-cart");
    private By shoppingCartBadge = By.className("shopping_cart_badge");
    
    private By inventoryContainer = By.id("inventory_container");
    // private By inventoryList = By.className("inventory_list");
    private By inventoryItem = By.className("inventory_item");
    private By shoppingCart = By.className("shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }
    public String getItemPriceMainPage(By itemLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement item = wait.until(ExpectedConditions.presenceOfElementLocated(itemLocator));
        WebElement container = item.findElement(By.xpath("./ancestor::div[@class='inventory_item']"));
        return container.findElement(By.className("inventory_item_price")).getText();
    }
    
    public String getItemPriceDetailPage(By itemLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(itemLocator)).click();
        String price = wait.until(ExpectedConditions.visibilityOfElementLocated(priceItem)).getText();
        driver.navigate().back();
        wait.until(ExpectedConditions.presenceOfElementLocated(inventoryContainer));
        return price;
    }

    
    public boolean isInventoryPageDisplayed() {
        return driver.findElement(inventoryContainer).isDisplayed();
    }
    public int getInventoryItemCount() {
        return driver.findElements(inventoryItem).size();
    }
    public boolean isCartIconDisplayed() {
        return driver.findElement(shoppingCart).isDisplayed();
    }

    public void clickBackpack() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(backPackItem)).click();
    }

    public String getBackpackPrice() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(priceItem)).getText();
    }

    
    public void clickAddToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public String getCartBadgeCount(WebDriverWait wait) {
        System.out.println("DEBUG: Waiting for badge to appear after adding item to cart...");
        
        // Use FluentWait with longer timeout and ignore NoSuchElement exceptions
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(45))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .withMessage("Shopping cart badge did not appear within 45 seconds");
        
        // Wait for badge to appear and become visible
        WebElement badge = fluentWait.until(driver -> {
            try {
                WebElement element = driver.findElement(shoppingCartBadge);
                if (element.isDisplayed()) {
                    String text = element.getText();
                    System.out.println("DEBUG: Badge found with text: '" + text + "'");
                    if (text != null && !text.trim().isEmpty()) {
                        return element;
                    }
                }
            } catch (Exception e) {
                System.out.println("DEBUG: Badge not yet available, retrying...");
            }
            return null;
        });
        
        String badgeText = badge.getText().trim();
        System.out.println("DEBUG: Final badge text: '" + badgeText + "'");
        return badgeText;
    }
}