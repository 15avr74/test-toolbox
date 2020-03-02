package be.etnic.qa.tools.accessibility;

import java.net.URL;
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;

import com.deque.axe.AXE;

public class AxeApi extends AXE {

    public static final URL AXE_SCRIPT_URL = AxeApi.class.getResource("/axe.min.3.5.0.js");
    private static final String RETURN_CARRIAGE = "<br/>";
    private static final String HTML_TAG_SPAN_CLOSE = "</span>";
    private static final String HTML_TAG_H3_CLOSE = "</h3>";
    private static final String HTML_TAG_DIV_CLOSE = "</div>";
    private static final String HTML_TAG_LI_CLOSE = "</li>";

    private AxeAnalysisResult axeResult;
    private JSONObject axeAnalysisJson;
    private boolean analysisFailed = false;

    public AxeApi(JSONObject axeAnalysisResponse) {
        this.axeAnalysisJson = axeAnalysisResponse;
        this.axeResult = new AxeAnalysisResult(axeAnalysisResponse);
    }

    public void writeResultsJson() {
        AXE.writeResults(axeResult.getName(), axeAnalysisJson);
        
    }

    
    protected String getReportFileUrl() {
        return "axe_analysis_on_" + getReportFileName() + ".html";
    }
    
    protected String getReportFileName() {
        return axeResult.getName();
    }
    
    
    /**
     * @param violations JSONArray of violations
     * @return readable report of accessibility violations found
     */
    public String reportHtml() {

        final StringBuilder sb = new StringBuilder();

        sb.append("<html><head></head><body>");

        
        sb.append(getEnvironmentInfoHtml());
        
        if(!isAnalysisFailed()) {
            sb.append(getResultHeaderHtml());
            sb.append(getResultDetailsHtml());
        }
        
        
        sb.append("</body></html>");

        return sb.toString();
    }


    private String getResultHeaderHtml() {
        
        final StringBuilder sb = new StringBuilder();

        sb.append("<div class='header'>");
        sb.append("<h3>");
        sb.append(axeResult.getFilteredResultsCount()).append(" accessibility violation(s) & incomplete(s) element(s) found for ");
        sb.append(axeResult.getFilteredResultElementsCount()).append(" element(s).");
        sb.append(HTML_TAG_H3_CLOSE).append(HTML_TAG_DIV_CLOSE);


        return sb.toString();
    }

    private String getResultDetailsHtml() {
        
        final StringBuilder sb = new StringBuilder();
        

        sb.append("<div class='violation'>");
        
        sb.append("<ol>");
        

        for (AxeResult violation : axeResult.getFilteredResults()) {
            
            sb.append("<li>");

            sb.append("Type : ");
            switch(violation.getType()) {
            case AxeResult.TYPE_INCOMPLETE:
                sb.append("incomplete");
                break;
            case AxeResult.TYPE_VIOLATION:
                sb.append("violation");
                break;
            default:
                sb.append("not defined");
            }            
            
            sb.append(RETURN_CARRIAGE);
            sb.append("Rule id : <a href='").append(violation.getHelpUrl()).append("' >").append(violation.getIdRule()).append("</a>");
            sb.append(RETURN_CARRIAGE).append("Impact : <span class:'impact-").append(violation.getImpactLabel()).append("'>").append(violation.getImpactLabel()).append(HTML_TAG_SPAN_CLOSE);

            sb.append(RETURN_CARRIAGE).append(StringEscapeUtils.escapeHtml4(violation.getHelp()));

            sb.append(RETURN_CARRIAGE).append("<ol class='violation-nodes'>");


            for (AxeResultNode node : violation.getResultNodes()) {
                
                sb.append("<li>");

                sb.append("<span class='failureSummary' >").append(StringEscapeUtils.escapeHtml4(node.getSummary())).append(HTML_TAG_SPAN_CLOSE);

                sb.append(RETURN_CARRIAGE).append("Position de l'élément : ").append("<span class='target'>").append("<pre>").append(
                        StringEscapeUtils.escapeHtml4(node.getPosition())).append("</pre>").append(HTML_TAG_SPAN_CLOSE);

                sb.append("Source de l'élément : ").append("<pre><code>").append(StringEscapeUtils.escapeHtml4(node.getSource())).append(
                        "</code></pre>");

                sb.append(HTML_TAG_LI_CLOSE);

            }

    
            sb.append("</ol>");
            sb.append(HTML_TAG_LI_CLOSE);

            sb.append("<span class='tags'>Tags</span>");
            sb.append("<ul class='violation-tags'>");

            for (String tag : violation.getResultTags()) {
                sb.append("<li>");
                sb.append("<span class='tag' >").append(tag).append(HTML_TAG_SPAN_CLOSE);
                sb.append(HTML_TAG_LI_CLOSE);
            }
            sb.append("</ul>").append(RETURN_CARRIAGE);
        

        }
        
        sb.append("</ol>");
        sb.append(HTML_TAG_DIV_CLOSE);
        
        return sb.toString();
    }
    private String getEnvironmentInfoHtml() {

        final StringBuilder sb = new StringBuilder();
        
        sb.append("<div class='Environnement'>");
        sb.append("<h3>Axe Engine version : ").append(axeResult.getEngineName()).append(" - ").append(axeResult.getEngineVersion()).append(HTML_TAG_H3_CLOSE);
        sb.append("<h3>Time : ").append(axeResult.getAnalysisTimestamp()).append(HTML_TAG_H3_CLOSE);
        sb.append("<h3>Url : ").append(axeResult.getUrlAnalysed()).append(HTML_TAG_H3_CLOSE);
        sb.append(HTML_TAG_DIV_CLOSE);

        return sb.toString();
    }
    
    public AxeAnalysisResult getAxeResult() {
        return axeResult;
    }

    public void setAnalysisFailed(boolean analysisFailed) {
        this.analysisFailed = analysisFailed;
    }

    public boolean isAnalysisFailed() {
        return analysisFailed;
    }

    /**
     * We filter the results on 
     * the level of This assertion checks if no violation/incomplete with the given level is detected during
     * the Axe analysis
     * 
     * @param level
     */
    public void filterByLevelAndRulesToIgnore(AxeImpactEnum level) {
        
        
        axeResult.setFilteredResults(axeResult.getResults()
                                                    .stream()
                                                    .filter(v -> v.getImpact().getLevel() >= level.getLevel())
                                                    .filter(v -> !AxeRulesToIgnore.rules.contains(v.getIdRule()))
                                                    .collect(Collectors.toList()));
        
        
        
        if(!axeResult.getFilteredResults().isEmpty()) {            
            writeResultsJson();            
        }
        
        
    }
}
