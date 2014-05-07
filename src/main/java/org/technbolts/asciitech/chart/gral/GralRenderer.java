package org.technbolts.asciitech.chart.gral;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.DrawingContext;
import de.erichseifert.gral.plots.PiePlot;
import de.erichseifert.gral.plots.Plot;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.colors.LinearGradient;
import de.erichseifert.gral.util.Insets2D;
import org.technbolts.asciitech.chart.ChartDescriptor;
import org.technbolts.asciitech.chart.ChartPieDescriptor;
import org.technbolts.asciitech.chart.ChartXYDescriptor;

import java.awt.*;
import java.io.IOException;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class GralRenderer {

    /**
     * First corporate color used for normal coloring.
     */
    protected static final Color COLOR1 = new Color(55, 170, 200);
    /**
     * Second corporate color used as signal color
     */
    protected static final Color COLOR2 = new Color(200, 80, 75);

    public void render(Graphics2D out,
                       ChartDescriptor descriptor) throws IOException {

        Plot plot = createPlot(descriptor);

        DrawingContext ctx = new DrawingContext(out);
        plot.setBounds(0, 0, descriptor.getWidth(), descriptor.getHeight());
        plot.draw(ctx);
    }

    private void applyDefaultDescriptor(ChartDescriptor descriptor, Plot plot) {
        String title = descriptor.getTitle();
        if (title != null)
            plot.getTitle().setText(title);

        if (descriptor.isLegendVisible())
            plot.setLegendVisible(true);

        // Add some margin to the plot area
        Insets2D.Double insets = descriptor.getInsets();
        if (insets != null)
            plot.setInsets(insets);
    }

    private Plot createPlot(ChartDescriptor descriptor) {
        switch (descriptor.getType()) {
            case Pie:
                ChartPieDescriptor pieDescriptor = (ChartPieDescriptor) descriptor;
                DataSource data = createDataSource(pieDescriptor);

                PiePlot piePlot = new PiePlot(data);
                apply(pieDescriptor, piePlot, data);
                return piePlot;

            case XY:
                ChartXYDescriptor xyDescriptor = (ChartXYDescriptor) descriptor;
                DataSource[] dataSources = createDataSources(xyDescriptor);

                XYPlot xyPlot = new XYPlot(dataSources);
                apply(xyDescriptor, xyPlot, dataSources);
                return xyPlot;

        }
        throw new UnsupportedOperationException("Chart type not supported '" + descriptor.getType() + "'");
    }

    private void apply(ChartXYDescriptor descriptor, XYPlot plot, DataSource[] dataSources) {
        applyDefaultDescriptor(descriptor, plot);
        for (int i = 0; i < descriptor.getSeriesCount(); i++) {
            apply(i, descriptor, plot, dataSources[i]);
        }
    }

    private void apply(int index, ChartXYDescriptor descriptor, XYPlot plot, DataSource dataSource) {
        throw new RuntimeException("Not implemented");
//        if (descriptor.isPointDecorated(index)) {
//            PointRenderer point = new DefaultPointRenderer2D();
//            point.setShape(descriptor.getPointAWTShape(index));
//            point.setColor(descriptor.getPointColor(index));
//        }


    }

    private void apply(ChartPieDescriptor descriptor, PiePlot plot, DataSource data) {
        applyDefaultDescriptor(descriptor, plot);

        // Change relative size of pie
        if (descriptor.getRadius() != null)
            plot.setRadius(descriptor.getRadius());

        PiePlot.PieSliceRenderer pointRenderer =
                (PiePlot.PieSliceRenderer) plot.getPointRenderer(data);

        // Change relative size of inner region
        if (descriptor.getInnerRadius() != null)
            pointRenderer.setInnerRadius(descriptor.getInnerRadius());

        // Change the width of gaps between segments
        if (descriptor.getGap() != null)
            pointRenderer.setGap(descriptor.getGap());

        // Change the colors
        LinearGradient colors = new LinearGradient(COLOR1, COLOR2);
        pointRenderer.setColor(colors);

        // Show labels
        pointRenderer.setValueVisible(true);
        pointRenderer.setValueColor(Color.WHITE);
        pointRenderer.setValueFont(Font.decode(null).deriveFont(Font.BOLD));
    }

    private DataSource createDataSource(ChartPieDescriptor descriptor) {
        DataTable data = new DataTable(1, Double.class);
        for (double d : descriptor.getValues()) {
            data.add(d);
        }
        return data;
    }

    private DataSource[] createDataSources(ChartXYDescriptor descriptor) {
        int nb = descriptor.getSeriesCount();
        DataTable table = new DataTable(nb + 1, Double.class);
        descriptor.traverseValues(wrap(table));

        DataSource[] dataSources = new DataSource[nb];
        for (int i = 0; i < nb; i++) {
            dataSources[i] = new DataSeries(table, 0, i + 1);
        }
        return dataSources;
    }

    private static ChartXYDescriptor.RowVisitor wrap(final DataTable table) {
        return new ChartXYDescriptor.RowVisitor() {
            @Override
            public void visit(Double... values) {
                table.add(values);
            }
        };
    }


}
