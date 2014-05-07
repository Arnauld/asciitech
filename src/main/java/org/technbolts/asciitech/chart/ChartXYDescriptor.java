package org.technbolts.asciitech.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class ChartXYDescriptor extends ChartDescriptor {
    private double[] xs;
    private List<YBlock> yblocks = new ArrayList<YBlock>();

    @Override
    public ChartType getType() {
        return ChartType.XY;
    }

    public double[] getXs() {
        return xs;
    }

    public void setXs(double... xs) {
        this.xs = xs;
    }

    public int getSeriesCount() {
        return yblocks.size();
    }

    public void addBlock(YBlock yblock) {
        yblocks.add(yblock);
    }

    public void traverseValues(RowVisitor visitor) {
        int nb = yblocks.size();

        for (int i = 0; i < xs.length; i++) {
            double x = xs[i];
            Double[] row = new Double[nb + 1];
            row[0] = x;
            for (int j = 0; j < nb; j++) {
                row[j + 1] = yblocks.get(j).valueAt(i);
            }

            visitor.visit(row);
        }
    }

    public PointDescriptor getPointDescriptor(int index) {
        return yblocks.get(index).getPointDescriptor();
    }

    public interface RowVisitor {
        void visit(Double... values);
    }

    public static class YBlock {
        private final double[] values;
        private PointDescriptor pointDescriptor;

        public YBlock(double[] values) {
            this.values = values;
        }

        public PointDescriptor getPointDescriptor() {
            return pointDescriptor;
        }

        public void setPointDescriptor(PointDescriptor pointDescriptor) {
            this.pointDescriptor = pointDescriptor;
        }

        public Double valueAt(int i) {
            return values[i];
        }
    }
}
