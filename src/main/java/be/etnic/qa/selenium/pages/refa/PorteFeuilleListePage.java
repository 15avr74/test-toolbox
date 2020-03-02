package be.etnic.qa.selenium.pages.refa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import be.etnic.qa.selenium.pages.BasePage;
import be.etnic.qa.selenium.pages.components.TableComponentPage;

public class PorteFeuilleListePage extends BasePage {

    
    By byPorteFeuilleTableau = By.id("groLisPorPortefeuilles");
    TableComponentPage portefeuilleTableau;
    
    
    public PorteFeuilleListePage(WebDriver driver) {
        super(driver);     
    }

    public TableComponentPage getPorteFeuilleTable() {
        return new TableComponentPage(driver, byPorteFeuilleTableau);
    }
    
    public PorteFeuilleDetailPage openPorteFeuilleDetailByCode(String portefeuilleCode) {
        
        getPorteFeuilleTable().clickOnDefautActionOnLineWithText(portefeuilleCode);
        return new PorteFeuilleDetailPage(driver);
    }

    
    
}
