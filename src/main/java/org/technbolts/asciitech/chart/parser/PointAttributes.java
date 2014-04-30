package org.technbolts.asciitech.chart.parser;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class PointAttributes extends ColorAttributes implements PatternAware, SizeAware, UnknownValueAware {

    private String sizeDef;
    private String pattern;

    @Override
    public boolean pattern(String pattern) {
        System.out.println("PointAttributes.pattern(" + pattern + ")");
        this.pattern = pattern;
        return true;
    }

    public String pattern() {
        return pattern;
    }

    @Override
    public boolean size(String sizeDef) {
        this.sizeDef = sizeDef;
        return true;
    }

    @Override
    public boolean unknown(String unknown) {
        return true;
    }

    public float size() {
        return Float.parseFloat(sizeDef);
    }

    public boolean isSizeDefined() {
        return sizeDef != null;
    }
}
