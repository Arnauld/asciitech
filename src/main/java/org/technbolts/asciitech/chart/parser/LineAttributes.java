package org.technbolts.asciitech.chart.parser;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class LineAttributes extends ColorAttributes implements WidthAware, PatternAware, UnknownValueAware {

    private String pattern;
    private String widthDef;

    @Override
    public boolean width(String widthDef) {
        this.widthDef = widthDef;
        return true;
    }

    public float width() {
        return Float.parseFloat(widthDef);
    }

    public boolean isWidthDefined() {
        return widthDef != null;
    }

    @Override
    public boolean pattern(String pattern) {
        this.pattern = pattern;
        return true;
    }

    public String pattern() {
        return pattern;
    }

    @Override
    public boolean unknown(String unknown) {
        System.out.println("LineAttributes.unknown(" + unknown + ")");
        return true;
    }

}
