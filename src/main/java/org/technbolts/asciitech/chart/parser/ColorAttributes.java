package org.technbolts.asciitech.chart.parser;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class ColorAttributes implements ColorAware, UnknownValueAware {

    private String hexDef;
    private String r;
    private String g;
    private String b;

    @Override
    public boolean hex(String hexDef) {
        this.hexDef = hexDef;
        return true;
    }

    @Override
    public boolean rgb(String r, String g, String b) {
        this.r = r;
        this.g = g;
        this.b = b;
        return true;
    }

    @Override
    public boolean unknown(String unknown) {
        return true;
    }

    public float[] rgb() {
        if (hexDef != null) {
            return parseHex();
        }
        return parseRGB();
    }

    private float[] parseRGB() {
        return new float[]{Float.parseFloat(r), Float.parseFloat(g), Float.parseFloat(b)};
    }

    private float[] parseHex() {
        if (hexDef.length() == 3) {
            return new float[]{
                    hex2float(hexDef.charAt(0)),
                    hex2float(hexDef.charAt(1)),
                    hex2float(hexDef.charAt(2))};
        } else if (hexDef.length() == 6) {
            return new float[]{
                    hex2float(hexDef.substring(0, 2)),
                    hex2float(hexDef.substring(2, 4)),
                    hex2float(hexDef.substring(4, 6))};
        } else
            throw new InvalidFormatException("Invalid hex color definition: '" + hexDef + "'");
    }

    private static float hex2float(String s) {
        return Integer.parseInt(s, 16);

    }

    private static float hex2float(char c) {
        return hex2float(String.valueOf(c) + String.valueOf(c));
    }
}
