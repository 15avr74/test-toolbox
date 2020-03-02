package be.etnic.qa.selenium.pages.refa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import be.etnic.qa.selenium.pages.BasePage;

public class MenuPage extends BasePage {

    
    By byPorteFeuilleMenu = By.xpath("//a[contains(text(),'Portefeuille')]");
    By byPorteFeuilleListe = By.id("listePortefeuilles");
    
    By bySolutionMenu = By.xpath("//a[contains(text(),'Solution')]");
    By bySolutionListe = By.id("listePortefeuilles");
    
    
    public MenuPage(WebDriver driver) {
        super(driver);
    }

    public PorteFeuilleListePage openPorteFeuilleListe() {
        
        click(byPorteFeuilleMenu);
        click(byPorteFeuilleListe);
        return new PorteFeuilleListePage(driver);
        
    }
    
    
}
