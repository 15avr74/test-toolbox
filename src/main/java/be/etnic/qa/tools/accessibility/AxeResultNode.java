package be.etnic.qa.tools.accessibility;

import org.json.JSONObject;

public class AxeResultNode {

    private String summary;
    private String position;
    private String source;

    public AxeResultNode(JSONObject node) {

        this.summary = node.getString("failureSummary");
        this.position = node.getJSONArray("target").getString(0);
        this.source = node.getString("html");

    }

    public String getSummary() {
        return summary;
    }

    public String getPosition() {
        return position;
    }

    public String getSource() {
        return source;
    }
}
