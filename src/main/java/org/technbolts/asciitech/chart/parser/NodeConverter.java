package org.technbolts.asciitech.chart.parser;

import org.technbolts.asciitech.chart.ChartDescriptor;
import org.technbolts.asciitech.chart.ChartPieDescriptor;
import org.technbolts.asciitech.chart.ChartXYDescriptor;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class NodeConverter {

    public ChartDescriptor convert(ChartNode node) {
        switch (node.chartType()) {
            case Pie:
                return convertPie((PieNode)node);
            case XY:
                return convertXY((XYNode)node);
        }
        throw new UnsupportedOperationException("Unsupported type of node: " + node.chartType());
    }

    private ChartPieDescriptor convertPie(PieNode node) {
        ChartPieDescriptor descriptor = new ChartPieDescriptor();

        if(node.isGapDefined())
            descriptor.setGap(node.gap());
        if(node.isInnerRadiusDefined())
            descriptor.setInnerRadius(node.innerRadius());
        if(node.isRadiusDefined())
            descriptor.setRadius(node.radius());
        if(node.isDataDefined())
            descriptor.setValues(node.dataAsDoubles());

        return descriptor;
    }

    private ChartXYDescriptor convertXY(XYNode node) {
        return null;
    }
}
