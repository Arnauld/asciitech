package org.technbolts.asciitech.chart.gral.discovery;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Drawable;
import de.erichseifert.gral.graphics.DrawingContext;
import de.erichseifert.gral.plots.PiePlot;
import de.erichseifert.gral.plots.colors.LinearGradient;
import de.erichseifert.gral.util.Insets2D;
import org.junit.Before;
import org.junit.Test;
import org.technbolts.asciitech.TestSettings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class GralUsecaseTest {

    /**
     * First corporate color used for normal coloring.
     */
    protected static final Color COLOR1 = new Color(55, 170, 200);
    /**
     * Second corporate color used as signal color
     */
    protected static final Color COLOR2 = new Color(200, 80, 75);

    private static final int SAMPLE_COUNT = 10;

    private Random random;
    private File outputDir;

    @Before
    public void setUp() {
        random = new Random();

        String buildDir = new TestSettings().getBuildDir();
        outputDir = new File(buildDir, getClass().getName());
        outputDir.mkdirs();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void pie_usecase() throws IOException {
        //  Create data
        DataTable data = new DataTable(Integer.class);
        for (int i = 0; i < SAMPLE_COUNT; i++) {
            int val = random.nextInt(8) + 2;
            int sign = (random.nextDouble() <= 0.15) ? -1 : 1;
            data.add(val);
        }

        // Create new pie plot
        PiePlot plot = new PiePlot(data);

        // Format plot
        plot.getTitle().setText("Dooonut!");
        // Change relative size of pie
        plot.setRadius(0.9);
        // Display a legend
        plot.setLegendVisible(true);
        // Add some margin to the plot area
        plot.setInsets(new Insets2D.Double(20.0, 40.0, 40.0, 40.0));

        PiePlot.PieSliceRenderer pointRenderer =
                (PiePlot.PieSliceRenderer) plot.getPointRenderer(data);
        // Change relative size of inner region
        pointRenderer.setInnerRadius(0.4);
        // Change the width of gaps between segments
        pointRenderer.setGap(0.2);
        // Change the colors
        LinearGradient colors = new LinearGradient(COLOR1, COLOR2);
        pointRenderer.setColor(colors);
        // Show labels
        pointRenderer.setValueVisible(true);
        pointRenderer.setValueColor(Color.WHITE);
        pointRenderer.setValueFont(Font.decode(null).deriveFont(Font.BOLD, 24));

        writePNG(plot, outputDir, "pie_usecase-" + ts() + ".png");
    }

    private void writePNG(Drawable data, File outputDir, String file) throws IOException {
        int factor = 2;
        int width = factor * 800;
        int height = factor * 600;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setPaintMode();

        DrawingContext ctx = new DrawingContext(graphics);
        data.setBounds(0, 0, width, height);
        data.draw(ctx);

        ImageIO.write(image, "png", new File(outputDir, file));
    }

    private static String ts() {
        return new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
    }
}
