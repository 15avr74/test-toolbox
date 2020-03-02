package be.etnic.qa.tools.accessibility;

import java.util.HashMap;
import java.util.Map;

public enum AxeImpactEnum {

    ALL(0,"all"),MINOR(10, "minor"), MODERATE(20, "moderate"), SERIOUS(30, "serious"), CRITICAL(40, "critical");

    private final int level;
    private final String label;

    private static final Map<String, AxeImpactEnum> BY_LABEL = new HashMap<>();
    private static final Map<Integer, AxeImpactEnum> BY_LEVEL = new HashMap<>();

    static {
        for (AxeImpactEnum impact : values()) {
            BY_LABEL.put(impact.label, impact);
            BY_LEVEL.put(impact.level, impact);
        }
    }

    private AxeImpactEnum(int value, String label) {
        this.level = value;
        this.label = label;
        
    }

    
    public static AxeImpactEnum valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
    
    public static AxeImpactEnum valueOfLevel(int level) {
        return BY_LEVEL.get(level);
    }        
    
    public int getLevel() {
        return level;
    }

    public String getLabel() {
        return label;
    }

}
