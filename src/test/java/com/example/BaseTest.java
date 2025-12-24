package com.example;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;

public class BaseTest {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected WebDriver getDriver() {
        return driver.get();
    }
    @BeforeMethod
    public void setUp() {
        driver.set(DriverFactory.createDriver());
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();
        getDriver().get("https://www.saucedemo.com");
    }
    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}
