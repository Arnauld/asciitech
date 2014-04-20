package org.technbolts.asciitech.chart.parser;

import org.parboiled.common.ImmutableList;
import org.technbolts.asciitech.parser.ast.Node;
import org.technbolts.asciitech.parser.ast.Values;
import org.technbolts.asciitech.parser.ast.Visitor;

import java.util.List;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class PieNode extends ChartNode {

    private Values data = new Values();
    private String radius;
    private String innerRadius;
    private String gap;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        return ImmutableList.of();
    }

    public Values data() {
        return data;
    }

    public double[] dataAsDoubles() {
        return data.toDoubles();
    }

    public Double getRadius() {
        return parseDouble(radius);
    }

    public Double getInnerRadius() {
        return parseDouble(innerRadius);
    }

    public Double getGap() {
        return parseDouble(gap);
    }

    public boolean radius(String radius) {
        this.radius = radius;
        return true;
    }

    public boolean innerRadius(String innerRadius) {
        this.innerRadius = innerRadius;
        return true;
    }

    public boolean gap(String gap) {
        this.gap = gap;
        return true;
    }

    private static Double parseDouble(String value) {
        return value == null ? null : Double.parseDouble(value);
    }

}
