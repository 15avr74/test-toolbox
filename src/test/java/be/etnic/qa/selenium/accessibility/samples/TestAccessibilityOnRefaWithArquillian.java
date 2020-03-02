package be.etnic.qa.selenium.accessibility.samples;

import java.util.HashMap;
import java.util.Map;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;

import be.etnic.qa.selenium.pages.refa.HomePage;
import be.etnic.qa.selenium.pages.refa.MenuPage;
import be.etnic.qa.selenium.pages.refa.PorteFeuilleListePage;
import be.etnic.qa.tools.accessibility.AccessibilityCheckApi;
import be.etnic.qa.tools.accessibility.AxeImpactEnum;

@RunWith(Arquillian.class)
public class TestAccessibilityOnRefaWithArquillian {

    private static AccessibilityCheckApi api;
    private static final String BASE_URL = "http://172.24.244.208/refa/";
    // private static final String BASE_URL =
    // "file:///C:/Dev/workspace-eclipse/test-utils/target/test/";

    

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Deployments.createDeployment();
    }

    
    @BeforeAll
    public static void globalSetup() {
        api = new AccessibilityCheckApi(getCredentialsHeader(), AxeImpactEnum.MODERATE);
    }

    @BeforeEach
    public void setUp() {
        api.startDriver();
    }

    @AfterEach
    public void finalisation() {
        api.stopDriver();
    }

    @AfterAll
    private static void generateReport() {
        api.generateGlobalReport();
    }

    @Test
    public void analyseAccessibilityOnRefa() {

        System.out.println("Alex");
        
    /*
        HomePage home = new HomePage(api.getDriver()).navigateTo();
        api.analyseAccessibilityForCurrentPage();

        MenuPage menu = home.goToMenuPage();
        PorteFeuilleListePage liste = menu.openPorteFeuilleListe();
        api.analyseAccessibilityForCurrentPage();

        liste.openPorteFeuilleDetailByCode("AGE");
        api.analyseAccessibilityForCurrentPage();

        api.assertGlobalAnalysis();
*/
        
    }

    private static Map<String, String> getCredentialsHeader() {

        HashMap<String, String> credentials = new HashMap<>();

        credentials.put("employeeNumber", "ETN34");
        credentials.put("memberOf", "etnic.transversal.refa.viewall()");
        credentials.put("givenName", "Anne");
        credentials.put("sn", "Noseda");
        credentials.put("cn", "anne.noseda@etnic.be");
        credentials.put("uid", "anne.noseda@etnic.be");
        credentials.put("mail", "anne.noseda@etnic.be");

        return credentials;

    }

}
