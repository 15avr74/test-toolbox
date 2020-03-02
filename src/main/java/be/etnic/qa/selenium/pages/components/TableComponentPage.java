package be.etnic.qa.selenium.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import be.etnic.qa.selenium.pages.BasePage;

public class TableComponentPage extends BasePage {

    By byTableId;

    public TableComponentPage(WebDriver driver, By table) {
        super(driver);
        this.byTableId = table;
    }

    public WebElement getTable() {
        return driver.findElement(byTableId);
    }

    public WebElement getLineByText(String textToFind) {
        WebElement line = getTable().findElement(By.xpath(".//tr[count(td[text()='" + textToFind + "']) > 0]"));
        highLightElement(line);
        return line;
    }

    public void clickOnDefautActionOnLineWithText(String textToFind) {
        WebElement defaultAction = getLineByText(textToFind).findElement(By.xpath(".//a"));
        highLightElement(defaultAction);
        defaultAction.click();
    }
    
    

}
