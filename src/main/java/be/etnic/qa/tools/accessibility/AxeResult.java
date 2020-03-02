package be.etnic.qa.tools.accessibility;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class AxeResult {

    private String description;
    private String helpUrl;
    private String idRule;
    private AxeImpactEnum impact;
    private String help;
    private List<AxeResultNode> resultNodes = new ArrayList<>();
    private List<String> resultTags = new ArrayList<>();
    private int type;

    public static final  int TYPE_VIOLATION =  1;
    public static final  int TYPE_INCOMPLETE = 2;
    public static final  int TYPE_PASSES =  3;
    
    
    public AxeResult(JSONObject json, int type) {
        this.description = json.getString("description");
        this.helpUrl = json.getString("helpUrl");
        this.help = json.getString("help");
        this.idRule = json.getString("id");
        this.impact =  AxeImpactEnum.valueOfLabel(json.getString("impact"));       
        this.type = type;
                
        JSONArray nodes = json.getJSONArray("nodes");

        for (int i = 0; i < nodes.length(); i++) {
            JSONObject node = nodes.getJSONObject(i);
            resultNodes.add(new AxeResultNode(node));
        }

        JSONArray tags = json.getJSONArray("tags");

        for (int j = 0; j < tags.length(); j++) {
            resultTags.add(tags.getString(j));
        }

    }

    public String getDescription() {
        return description;
    }

    public String getHelpUrl() {
        return helpUrl;
    }

    public String getIdRule() {
        return idRule;
    }

    public AxeImpactEnum getImpact() {
        return impact;
    }

    public String getImpactLabel() {
        return impact.getLabel();
    }

    public String getHelp() {
        return help;
    }

    public int getType() {
        return type;
    }

    public List<String> getResultTags() {
        return resultTags;
    }

    public List<AxeResultNode> getResultNodes() {
        return resultNodes;
    }

    @Override
    public String toString() {
        return type + " - " + impact.getLabel() + " - " + idRule + " - " + resultNodes.size() +" nodes" + " - " + resultTags.size() +" tags"; 
    }
}
