package org.technbolts.asciitech.chart.parser;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import org.technbolts.asciitech.chart.ChartDescriptor;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class ChartDescriptorParser {

    public ChartDescriptor parse(String input) {
        ChartDescriptorParboiledParser parser = Parboiled.createParser(ChartDescriptorParboiledParser.class);
        ParsingResult<Object> result = new ReportingParseRunner<Object>(parser.descriptors()).run(input);
        if(result.hasErrors()) {
            System.out.println("ChartDescriptorParser.parse(" + result.parseErrors + ")");
        }
        return (ChartDescriptor) result.resultValue;
    }
}
