package org.technbolts.asciitech.chart.parser;

import org.junit.Before;
import org.junit.Test;
import org.technbolts.asciitech.chart.ChartDescriptor;
import org.technbolts.asciitech.chart.ChartPieDescriptor;
import org.technbolts.asciitech.parser.ast.Values;

import static org.fest.assertions.Assertions.assertThat;

public class NodeConverterTest {

    private NodeConverter nodeConverter;

    @Before
    public void setUp() {
        nodeConverter = new NodeConverter();
    }

    @Test
    public void should_convert_a_pie() {
        Values values = new Values();
        values.append("1.0");
        values.append("23.5");
        values.append("19");
        values.append("11");
        values.append("9.4");

        PieNode node = new PieNode();
        node.data(values);
        node.radius("0.9");
        node.innerRadius("0.4");
        node.gap("0.2");
        node.displayLegend("false");

        ChartDescriptor descriptor = nodeConverter.convert(node);
        assertThat(descriptor).isInstanceOf(ChartPieDescriptor.class);

        ChartPieDescriptor pie = (ChartPieDescriptor) descriptor;
        assertThat(pie.isLegendVisible()).isFalse();
        assertThat(pie.getRadius()).isEqualTo(0.9);
        assertThat(pie.getInnerRadius()).isEqualTo(0.4);
        assertThat(pie.getGap()).isEqualTo(0.2);
        assertThat(pie.getValues()).isEqualTo(new double[]{1.0, 23.5, 19, 11, 9.4});
    }

}