package org.technbolts.asciitech.parser.ast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Values {
    private List<String> values = new ArrayList<String>();

    public boolean append(String value) {
        values.add(value);
        return true;
    }

    public boolean ok() {
        return true;
    }

    public double[] toDoubles() {
        double[] doubles = new double[values.size()];
        for (int i = 0; i < values.size(); i++) {
            String value = values.get(i);
            doubles[i] = Double.parseDouble(value.trim());
        }
        return doubles;
    }

    @Override
    public String toString() {
        return "Values[" + values + ']';
    }
}
