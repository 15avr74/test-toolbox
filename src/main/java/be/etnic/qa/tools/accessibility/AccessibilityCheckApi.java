package be.etnic.qa.tools.accessibility;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Base64;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.deque.axe.AXE;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;

public class AccessibilityCheckApi {

    private WebDriver driver;
    private AxeApi axe;
    private StringBuilder summaryReport;

    private int nbrOfAxeResults = 0;
    private int nbrOfAxeResultsElements = 0;

    private static final String OUTPUT_DIRECTORY = "target/";
    private AxeImpactEnum impactLevel;

    private BrowserMobProxy proxy;
    private ChromeOptions cOptions;

    
    
    public AccessibilityCheckApi(Map<String, String> headers, AxeImpactEnum level) {

        System.setProperty("webdriver.chrome.driver", "C:\\Dev-Tools\\selenium\\webdrivers\\bin\\chromedriver.exe");

        setHeader(headers);

        cOptions = new ChromeOptions();
        // cOptions.addArguments("headless");
        cOptions.addArguments("--window-size=1920,1080");
        cOptions.setProxy(ClientUtil.createSeleniumProxy(proxy));

        summaryReport = new StringBuilder();
        this.impactLevel = level;
    }

    public void startDriver() {
        driver = new ChromeDriver(cOptions);
    }

    public void stopDriver() {
        driver.close();
    }

    public void openPage(String urlPage) {
        driver.get(urlPage);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void analyseAccessibilityForCurrentPage() {
        analyseAccessibilityForCurrentPage(impactLevel);
    }

    public void analyseAccessibilityForCurrentPage(AxeImpactEnum level) {

        try {
        
            JSONObject responseJSON = new AXE.Builder(driver, AxeApi.AXE_SCRIPT_URL).skipFrames().analyze();

            axe = new AxeApi(responseJSON);
            axe.filterByLevelAndRulesToIgnore(level);

        } catch (Exception e) {
              axe.setAnalysisFailed(true);              
        }finally {
            addAxeAnalysisPageReport();
        }
            

    }

    private void addAxeAnalysisPageReport() {

        
        
        if (!axe.getAxeResult().getFilteredResults().isEmpty() || axe.isAnalysisFailed()) {

            writeResultsPage(axe.getReportFileUrl(), axe.reportHtml());

            summaryReport.append("<li><a href='" + axe.getReportFileUrl() + "' target='blank' >" + axe.getReportFileName() + "</a><span> : " +  axe.getAxeResult().getFilteredResultsCount() +" violation(s)/incomplete(s)</span></li>");

            nbrOfAxeResultsElements += axe.getAxeResult().getFilteredResultElementsCount();
            nbrOfAxeResults += axe.getAxeResult().getFilteredResultsCount();

        }

    }

    /*
     * This assertion checks if no violation/incomplete has been detected during the
     * AXE analysis
     */
    public void assertGlobalAnalysis() {

        if (nbrOfAxeResults > 0) {
            fail(nbrOfAxeResults + " violation(s)/incomplete(s) found");
        } else {
            assertTrue(true, "No violation found");
        }

    }

    public void assertPageAnalysis() {

        if (axe.getAxeResult().getFilteredResultsCount() > 0) {
            fail(axe.getAxeResult().getFilteredResultsCount() + " violation(s)/incomplete(s) found");
        } else {
            assertTrue(true, "No violation found");
        }

    }

    /*
     * This method generate a global report containing links to detailed AXE
     * analysis by page
     */
    public void generateGlobalReport() {

        String header = "<html><head></head><body><h1>Global Report</h1><h3>" + nbrOfAxeResults + " violation(s)/incomplete(s) on " + nbrOfAxeResultsElements
                + " element(s)</h3><ol>";

        summaryReport.insert(0, header);
        summaryReport.append("</ol></body></html>");

        summaryReport.toString();

        writeResultsPage("index.html", summaryReport.toString());
    }

    private void setHeader(Map<String, String> headers) {

        proxy = new BrowserMobProxyServer();

        proxy.addHeaders(headers);

        String authHeader = "";

        try {
            authHeader = "Basic " + Base64.getEncoder().encodeToString("webelement:click".getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        proxy.addHeader("Authorization", authHeader);

        proxy.start(0);
    }

    /*
     * @param name Desired filename, sans extension *
     * 
     * @param output Object to write. Most useful if you pass in either the
     * Builder.analyze() response or the violations array it contains.
     */
    public void writeResultsPage(String fileUrl, String output) {

        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT_DIRECTORY + fileUrl), "utf-8"));
            writer.write("<html><body><div>" + output + "</div></body></html>");

        } catch (IOException ignored) {
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception ignored) {
            }
        }
    }

}
