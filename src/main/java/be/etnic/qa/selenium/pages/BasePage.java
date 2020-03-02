package be.etnic.qa.selenium.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    public final WebDriver driver;
    public final WebDriverWait driverWait;

    // Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        driverWait = new WebDriverWait(driver, 15);
    }

    // Wait Wrapper Method
    public void waitVisibility(By elementBy) {
        driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementBy));
    }

    // Click Method
    public void click(By elementBy) {
        waitVisibility(elementBy);
        driver.findElement(elementBy).click();
    }

    // Write Text
    public void writeText(By elementBy, String text) {
        waitVisibility(elementBy);
        driver.findElement(elementBy).sendKeys(text);
    }

    // Read Text
    public String readText(By elementBy) {
        waitVisibility(elementBy);
        return driver.findElement(elementBy).getText();
    }

    // Assert
    public void assertEquals(By elementBy, String expectedText) {
        waitVisibility(elementBy);
        Assertions.assertEquals(readText(elementBy), expectedText);
    }

    public void highLightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }
}
