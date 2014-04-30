package org.technbolts.asciitech.chart.parser;

import org.parboiled.common.ImmutableList;
import org.technbolts.asciitech.parser.ast.Node;
import org.technbolts.asciitech.parser.ast.Values;
import org.technbolts.asciitech.parser.ast.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class XYNode extends ChartNode {

    private Values xs = new Values();
    private List<YBlock> yBlocks = new ArrayList<YBlock>();

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<Node> getChildren() {
        return ImmutableList.of();
    }

    public double[] xAsDoubles() {
        return xs.toDoubles();
    }

    public boolean xs(Values xs) {
        this.xs = xs;
        return true;
    }

    public boolean yBlock(YBlock yBlock) {
        yBlocks.add(yBlock);
        return true;
    }

    public List<YBlock> getYBlocks() {
        return yBlocks;
    }

    public static class YBlock implements IgnoredLineAware {
        private StringBuilder ignoredContent = new StringBuilder();
        private Values values;
        private LineAttributes lineAttributes;
        private ColorAttributes colorAttributes;
        private PointAttributes pointAttributes;
        private AreaAttributes areaAttributes;

        public boolean ok() {
            return true;
        }

        public boolean appendIgnoredLine(String value) {
            System.out.println("YBlock.appendIgnoredLine('" + value + "')");
            ignoredContent.append(value).append('\n');
            return true;
        }

        public boolean values(Values values) {
            this.values = values;
            return true;
        }

        public double[] valuesAsDoubles() {
            return values == null ? null : values.toDoubles();
        }

        @Override
        public String toString() {
            return "YBlock{" +
                    "ignoredContent='" + ignoredContent + "'" +
                    ", values=" + values +
                    '}';
        }

        public boolean lineAttributes(LineAttributes lineAttributes) {
            this.lineAttributes = lineAttributes;
            return true;
        }

        public boolean colorAttributes(ColorAttributes colorAttributes) {
            this.colorAttributes = colorAttributes;
            return true;
        }

        public boolean pointAttributes(PointAttributes pointAttributes) {
            this.pointAttributes = pointAttributes;
            return true;
        }

        public boolean areaAttributes(AreaAttributes areaAttributes) {
            this.areaAttributes = areaAttributes;
            return true;
        }

        public ColorAttributes colorAttributes() {
            return colorAttributes;
        }

        public PointAttributes pointAttributes() {
            return pointAttributes;
        }

        public LineAttributes lineAttributes() {
            return lineAttributes;
        }

        public AreaAttributes areaAttributes() {
            return areaAttributes;
        }
    }
}
