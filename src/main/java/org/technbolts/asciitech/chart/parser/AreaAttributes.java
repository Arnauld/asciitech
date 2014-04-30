package org.technbolts.asciitech.chart.parser;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class AreaAttributes extends ColorAttributes implements PatternAware, GapAware {

    private String pattern;
    private String gapDef;

    @Override
    public boolean pattern(String pattern) {
        this.pattern = pattern;
        return true;
    }

    public String pattern() {
        return pattern;
    }

    @Override
    public boolean gap(String gapDef) {
        this.gapDef = gapDef;
        return true;
    }

    public boolean isGapDefined() {
        return gapDef != null;
    }

    public float gap() {
        return Float.parseFloat(gapDef);
    }
}
