package com.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPagePom {
    private WebDriver driver;

    //LOCATORS
    private By usernameInput = By.id("user-name");
    private By passwordInput = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    //CONSTRUCTOR
    public LoginPagePom(WebDriver driver) {
        this.driver = driver;
    }
    //ACTIONS
    public void enterUsername(String username) {
        driver.findElement(usernameInput)
            .sendKeys(username);
    }
    public void enterPassword(String password) {
        driver.findElement(passwordInput)
            .sendKeys(password);
    }
    public void clickLogin() {
        driver.findElement(loginButton)
            .click();
    }
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
    public boolean isErrorDisplayed() {
        return driver.findElement(errorMessage)
            .isDisplayed();
    }

}
