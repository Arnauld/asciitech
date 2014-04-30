package org.technbolts.asciitech.chart.parser;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public interface ColorAware {
    boolean rgb(String r, String g, String b);

    boolean hex(String hexDef);
}
