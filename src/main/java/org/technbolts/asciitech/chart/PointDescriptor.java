package org.technbolts.asciitech.chart;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class PointDescriptor {


    public Shape createAWTShape() {
        return new Ellipse2D.Double(-3,-3,6,6);
    }
}
