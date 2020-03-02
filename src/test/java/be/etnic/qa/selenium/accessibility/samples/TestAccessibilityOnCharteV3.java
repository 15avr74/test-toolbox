package be.etnic.qa.selenium.accessibility.samples;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.etnic.qa.tools.accessibility.AccessibilityCheckApi;
import be.etnic.qa.tools.accessibility.AxeImpactEnum;

public class TestAccessibilityOnCharteV3 {

    private static AccessibilityCheckApi api;
    private static final String BASE_URL = "C:/Dev/workspace-eclipse/charteV3/design-web/src/main/resources/";

    @BeforeAll
    public static void globalSetup() {
        Map<String, String> headers = new HashMap<String, String>();
        api = new AccessibilityCheckApi(headers, AxeImpactEnum.MODERATE);
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
    public void analyseAccessibility() {

        Stream.of(new File(BASE_URL)
                .listFiles())
                .filter(file -> !file.isDirectory())
                .filter(file -> !file.getName().startsWith("ZZZ"))
                .filter(file -> file.getName().startsWith("accueil"))
                .map(File::getName)
                .sorted()
                .forEach(file -> analyseFile(file));

        api.assertGlobalAnalysis();

    }

    private void analyseFile(String fileName) {

        System.out.println(fileName);
        
        api.openPage(BASE_URL + fileName);
        api.analyseAccessibilityForCurrentPage();
        
    }

}
