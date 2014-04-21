package org.technbolts.asciitech.chart.parser;

import org.junit.Test;
import org.technbolts.asciitech.parser.ast.AbstractNode;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Delta.delta;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class ChartDescriptorParboiledParserTest {


    @Test
    public void parse_pie_chart() {
        String input = "" +
                "type: pie\n" +
                "data: [ 1.0, 23.5, 19, 11, 9.4 ]\n" +
                "radius: 0.9\n" +
                "inner-radius : 0.4\n" +
                "gap: 0.2\n" +
                "legend: true\n" +
                "";
        AbstractNode node = ChartDescriptorParboiledParser.parse(input);
        assertThat(node).isNotNull();
        assertThat(node).isInstanceOf(PieNode.class);

        PieNode pieNode = (PieNode) node;
        assertThat(pieNode.dataAsDoubles()).isEqualTo(new double[]{1.0, 23.5, 19, 11, 9.4}, delta(1e-6));
        assertThat(pieNode.getRadius()).isEqualTo(0.9);
        assertThat(pieNode.getInnerRadius()).isEqualTo(0.4);
        assertThat(pieNode.getGap()).isEqualTo(0.2);

    }

    @Test
    public void parse_xy_chart() {
        String input = "" +
                "type: xy\n" +
                "x: [1.0, 2.0, 3.0, 6.4, 8]\n" +
                "y: [1.0, 23.5, 10, 11, 9.4]\n" +
                " - color: rgb(55, 170, 200)\n" +
                " - point: circle, size: 3.0\n" +
                " - line: solid, width: 2.0\n" +
                " - area: fill\n" +
                "y: [3.0, 5.0, 7.0, 9.0, 11.0]\n" +
                " - color: rgb(200, 80, 75)\n" +
                " - point: square\n" +
                " - line: none\n" +
                " - area: line, gap: 0.3\n" +
                "legend: true\n" +
                "";
        AbstractNode node = ChartDescriptorParboiledParser.parse(input);
        assertThat(node).isNotNull();
        assertThat(node).isInstanceOf(XYNode.class);

        XYNode xyNode = (XYNode) node;
        assertThat(xyNode.displayLegend()).isTrue();
        assertThat(xyNode.xAsDoubles()).isEqualTo(new double[]{1.0, 2.0, 3.0, 6.4, 8});

        assertThat(xyNode.getYBlocks()).hasSize(2);
        assertThat(xyNode.getYBlocks().get(0).valuesAsDoubles()).isEqualTo(new double[]{1.0, 23.5, 10, 11, 9.4});
        assertThat(xyNode.getYBlocks().get(1).valuesAsDoubles()).isEqualTo(new double[]{3.0, 5.0, 7.0, 9.0, 11.0});

    }
}
