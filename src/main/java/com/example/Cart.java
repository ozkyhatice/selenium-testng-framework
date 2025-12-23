package com.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



public class Cart {
    private WebDriver driver;

    //LOCATORS
    private By cartIcon = By.className("shopping_cart_link");
    private By checkout = By.id("checkout");
    private By firsName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By postalCode = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By itemTotal = By.className("summary_subtotal_label");
    private By finishButton = By.id("finish");
    private By completeHeader = By.className("complete-header");
    public Cart(WebDriver driver) {
        this.driver = driver;
    }
    public void clickCartIcon() {
        driver.findElement(cartIcon).click();
    }
    public void clickCheckout() {
        driver.findElement(checkout).click();
    }
    public void enterCheckoutInformation(String firstName, String lastName, String postalCode) {
        driver.findElement(this.firsName).sendKeys(firstName);
        driver.findElement(this.lastName).sendKeys(lastName);
        driver.findElement(this.postalCode).sendKeys(postalCode);
    }
    public void clickContinue() {
        driver.findElement(continueButton).click();
    }
    public String getItemTotal() {
        return driver.findElement(itemTotal).getText();
    }
    public void clickFinish() {
        driver.findElement(finishButton).click();
    }
    public String getCompleteHeader() {
        return driver.findElement(completeHeader).getText();
    }
}
