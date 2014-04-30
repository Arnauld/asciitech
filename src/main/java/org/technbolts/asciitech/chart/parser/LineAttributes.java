package org.technbolts.asciitech.chart.parser;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class LineAttributes extends ColorAttributes implements WidthAware, PatternAware, UnknownValueAware {

    @Override
    public boolean width(String match) {
        System.out.println("LineAttributes.width(" + match + ")");
        return true;
    }

    @Override
    public boolean pattern(String match) {
        System.out.println("LineAttributes.pattern(" + match + ")");
        return true;
    }

    @Override
    public boolean unknown(String unknown) {
        System.out.println("LineAttributes.unknown(" + unknown + ")");
        return true;
    }
}
