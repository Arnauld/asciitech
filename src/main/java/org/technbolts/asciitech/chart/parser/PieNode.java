package org.technbolts.asciitech.chart.parser;

import org.parboiled.common.ImmutableList;
import org.technbolts.asciitech.chart.ChartType;
import org.technbolts.asciitech.parser.ast.Node;
import org.technbolts.asciitech.parser.ast.Values;
import org.technbolts.asciitech.parser.ast.Visitor;

import java.util.List;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class PieNode extends ChartNode {

    private Values data;
    private String radius;
    private String innerRadius;
    private String gap;

    public ChartType chartType() {
        return ChartType.Pie;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        return ImmutableList.of();
    }

    public boolean data(Values data) {
        this.data = data;
        return false;
    }

    public boolean isDataDefined() {
        return data != null;
    }

    public double[] dataAsDoubles() {
        return data.toDoubles();
    }

    public double radius() {
        return Double.parseDouble(radius);
    }

    public boolean isRadiusDefined() {
        return radius != null;
    }

    public double innerRadius() {
        return Double.parseDouble(innerRadius);
    }

    public boolean isInnerRadiusDefined() {
        return innerRadius != null;
    }

    public double gap() {
        return Double.parseDouble(gap);
    }

    public boolean isGapDefined() {
        return gap != null;
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

}
