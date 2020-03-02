package be.etnic.qa.selenium.pages.refa;

import org.openqa.selenium.WebDriver;

import be.etnic.qa.selenium.pages.BasePage;

public class HomePage extends BasePage {

    String baseURL = "http://172.24.244.208/refa/accueil.xhtml";
    
    public HomePage(WebDriver driver) {
        super(driver);    
    }
    
    public HomePage navigateTo(){
        driver.get(baseURL);
        return this;
    }
 
    public MenuPage goToMenuPage (){        
        return new MenuPage(driver);
    }
    


}
