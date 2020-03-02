package be.etnic.qa.tools.accessibility;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AxeRulesToIgnore {

    public static final Set<String> rules = new HashSet<>(Arrays.asList(
            "avoid-inline-spacing",
            "bypass",
            "focus-order-semantics",
            "form-field-multiple-labels",
            "hidden-content",
            "html-xml-lang-mismatch",
            "landmark-banner-is-top-level",
            "landmark-complementary-is-top-level",
            "landmark-contentinfo-is-top-level",
            "landmark-main-is-top-level",
            "landmark-no-duplicate-contentinfo",
            "landmark-no-duplicate-banner",
            "landmark-one-main",
            "meta-viewport-large",
            "region",
            "server-side-image-map skip-link",            
            "table-duplicate-name"));
    
    public AxeRulesToIgnore() {
        // 
    }

}
