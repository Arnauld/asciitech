package org.technbolts.asciitech.chart.gral;

import org.junit.Before;
import org.junit.Test;
import org.technbolts.asciitech.TestSettings;
import org.technbolts.asciitech.chart.ChartPieDescriptor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class GralRendererTest {

    private static AtomicInteger idGen = new AtomicInteger();

    private int width = 800;
    private int height = 600;
    private File outputDir;

    @Before
    public void setUp() {
        TestSettings settings = new TestSettings();
        String buildDir = settings.getBuildDir();
        outputDir = new File(buildDir, getClass().getName());
        outputDir.mkdirs();
    }

    @Test
    public void should_render_a_pie() throws IOException {
        ChartPieDescriptor descriptor = new ChartPieDescriptor();
        descriptor.setWidth(width);
        descriptor.setHeight(height);
        descriptor.setValues(new double[]{1.0, 2.0, 3.0, 4.0});

        render(descriptor, "should_render_a_pie-" + ts() + ".png");
    }

    private void render(ChartPieDescriptor descriptor, String filename) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        GralRenderer renderer = new GralRenderer();
        renderer.render(graphics, descriptor);

        ImageIO.write(image, "png", new File(outputDir, filename));
    }

    private static String ts() {
        return new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
    }
}