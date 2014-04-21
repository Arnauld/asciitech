package org.technbolts.asciitech.chart.parser;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class LineAttributes implements ColorAware {
    public boolean width(String match) {
        System.out.println("LineAttributes.width(" + match + ")");
        return true;
    }

    public boolean pattern(String match) {
        System.out.println("LineAttributes.pattern(" + match + ")");
        return true;
    }

    @Override
    public boolean color(String colorDef) {
        System.out.println("LineAttributes.color(" + colorDef +")");
        return true;
    }
}
