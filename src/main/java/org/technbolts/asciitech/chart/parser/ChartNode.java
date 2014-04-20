package org.technbolts.asciitech.chart.parser;

import org.technbolts.asciitech.parser.ast.AbstractNode;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public abstract class ChartNode extends AbstractNode implements IgnoredLineAware {
    private String displayLegend;
    private StringBuilder ignoredContent = new StringBuilder();

    public boolean displayLegend(String displayLegend) {
        this.displayLegend = displayLegend.trim();
        return true;
    }

    public boolean appendIgnoredLine(String value) {
        ignoredContent.append(value).append('\n');
        return true;
    }

    public boolean displayLegend() {
        return displayLegend == null ? true : Boolean.parseBoolean(displayLegend);
    }
}
