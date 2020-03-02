package be.etnic.qa.tools.accessibility;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class AxeAnalysisResult {

    private List<AxeResult> results = new ArrayList<>();
    private List<AxeResult> filteredResults = new ArrayList<>();

    private String name;
    private String engineName;
    private String engineVersion;
    private Timestamp analysisTimestamp;
    private String urlAnalysed;

    public AxeAnalysisResult(JSONObject analysisResponse) {

        JSONObject testEngine = analysisResponse.getJSONObject("testEngine");
        this.engineName = testEngine.getString("name");
        this.engineVersion = testEngine.getString("version");
        
        this.urlAnalysed = analysisResponse.getString("url");
        extractNameFromUrl();
        
        LocalDateTime ldt = LocalDateTime.parse(analysisResponse.getString("timestamp"),DateTimeFormatter.ISO_DATE_TIME);
        this.analysisTimestamp = Timestamp.valueOf(ldt);

        JSONArray jsonViolations = analysisResponse.getJSONArray("violations");

        jsonViolations.forEach(jsonViolation -> {
            results.add(new AxeResult(((JSONObject) jsonViolation),AxeResult.TYPE_VIOLATION));
        });
                
        
        JSONArray jsonIncompletes = analysisResponse.getJSONArray("incomplete");

        jsonIncompletes.forEach(jsonIncomplete -> {
            results.add(new AxeResult(((JSONObject) jsonIncomplete),AxeResult.TYPE_INCOMPLETE));
        });
        

        
        Comparator<AxeResult> impactResultComparator = (o1, o2)-> (o2.getImpact().getLevel() - o1.getImpact().getLevel());
        results.sort(impactResultComparator);
        
        
        Comparator<AxeResult> typeResultComparator = (o1, o2)-> ( o1.getType()- o2.getType());
        results.sort(typeResultComparator);
        
        
    }
    
    public List<AxeResult> getResults() {
        return results;
    }

    public int getFilteredResultsCount() {
        return filteredResults.size();               
    }
    
    public int getFilteredResultElementsCount() {
        int counter = 0;
        
        for (AxeResult result : filteredResults) {
            counter += result.getResultNodes().size();
        }
                
        return counter;               
    }

    
    public String getName() {
        return name;
    }

    public String getEngineName() {
        return engineName;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public Timestamp getAnalysisTimestamp() {
        return analysisTimestamp;
    }

    public String getUrlAnalysed() {
        return urlAnalysed;
    }

    public List<AxeResult> getFilteredResults() {
        return filteredResults;
    }

    public void setFilteredResults(List<AxeResult> results) {
        this.filteredResults = results;
    }

    
    private void extractNameFromUrl() {
        
        if (urlAnalysed !=null && !urlAnalysed.isEmpty()) {
            
            String[] temp = urlAnalysed.split("[/]");
            
            if(temp !=null && temp.length>0) {
                this.name = temp[temp.length-1];
            }
        }
        
        
        
    }
    
}
