package org.technbolts.asciitech.chart.parser;

import org.parboiled.BaseParser;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.SuppressNode;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;
import org.parboiled.support.StringBuilderVar;
import org.parboiled.support.StringVar;
import org.technbolts.asciitech.parser.Parser;
import org.technbolts.asciitech.parser.ast.AbstractNode;
import org.technbolts.asciitech.parser.ast.Values;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
@BuildParseTree
public class ChartDescriptorParboiledParser extends Parser {

    public static AbstractNode parse(String input) {
        ChartDescriptorParboiledParser parser = Parboiled.createParser(ChartDescriptorParboiledParser.class);
        ParsingResult<Object> result;

        boolean trace = false;
        if (trace)
            result = new TracingParseRunner<Object>(parser.descriptors()).run(input);
        else
            result = new ReportingParseRunner<Object>(parser.descriptors()).run(input);

        if (result.hasErrors()) {
            System.out.println("ChartDescriptorParser.parse(" + result.parseErrors + ")");
        }
        System.out.println("ChartDescriptorParboiledParser.parse(" + ParseTreeUtils.printNodeTree(result) + ")");
        return (AbstractNode) result.resultValue;
    }

    Rule descriptors() {
        return FirstOf(pieDescriptor(), xyDescriptor());
    }

    // ------------------------------------------------------------------------
    //
    //  PIE
    //
    // ------------------------------------------------------------------------

    Rule pieDescriptor() {
        return NodeSequence(
                pieType(),
                push(new PieNode()),
                ZeroOrMore(Sequence(pieElement(), Newline())),
                ANY);
    }

    @SuppressSubnodes
    Rule pieType() {
        return Sequence(Sp(), TYPE, Sp(), COLON, Sp(), PIE, Sp(), Newline());
    }

    Rule pieElement() {
        return FirstOf(pieData(), pieRadius(), pieInnerRadius(), pieGap(), legend(), CaptureLineIgnored());
    }

    @SuppressSubnodes
    Rule pieData() {
        return Sequence(Sp(), "data", Sp(), COLON, Sp(),
                push(((PieNode) peek()).data()),
                LBRK, Sp(), CommaOrSpaceSeparatedValues(), Sp(), RBRK, Sp(),
                ((Values) pop()).ok());
    }

    @SuppressSubnodes
    Rule pieInnerRadius() {
        return Sequence(Sp(), "inner-radius", Sp(), COLON, Sp(),
                FirstOf(DecimalFloat(), IntegerLiteral()),
                ((PieNode) peek()).innerRadius(match()));
    }

    @SuppressSubnodes
    Rule pieGap() {
        return Sequence(Sp(), "gap", Sp(), COLON, Sp(),
                FirstOf(DecimalFloat(), IntegerLiteral()),
                ((PieNode) peek()).gap(match()));
    }

    @SuppressSubnodes
    Rule pieRadius() {
        return Sequence(Sp(), "radius", Sp(), COLON, Sp(),
                FirstOf(DecimalFloat(), IntegerLiteral()),
                ((PieNode) peek()).radius(match()));
    }

    // ------------------------------------------------------------------------
    //
    //  XY
    //
    // ------------------------------------------------------------------------

    Rule xyDescriptor() {
        return NodeSequence(
                xyType(),
                push(new XYNode()),
                ZeroOrMore(Sequence(xyElement(), Newline()))
        );
    }

    @SuppressSubnodes
    Rule xyType() {
        return Sequence(Sp(), TYPE, Sp(), COLON, Sp(), XY, Sp(), Newline());
    }

    Rule xyElement() {
        return FirstOf(xyXs(), xyYBlock(), legend(), CaptureLineIgnored());
    }

    @SuppressSubnodes
    Rule xyXs() {
        return Sequence(Sp(), "x", Sp(), COLON, Sp(),
                push(new Values()),
                LBRK, Sp(), CommaOrSpaceSeparatedValues(), Sp(), RBRK, Sp(),
                ((XYNode) peek(1)).xs((Values) pop()));
    }

    Rule xyYBlock() {
        return Sequence(
                push(new XYNode.YBlock()),
                xyYs(),
                ZeroOrMore(Sequence(
                                Newline(),
                                FirstOf(xyColorAttributes(),
                                        xyLineAttributes(),
                                        xyAreaAttributes(),
                                        xyPointAttributes())
                        )
                ),
                ((XYNode) peek(1)).yBlock((XYNode.YBlock) pop())
        );
    }

    @SuppressSubnodes
    Rule xyColorAttributes() {
        return Sequence(Sp(), MINUS, Sp(), "color", COLON, Sp(),
                push(new ColorAttributes()),
                Optional(
                        colorAttribute(),
                        ZeroOrMore(Sp(), COMMA, Sp(), colorAttribute())
                ),
                unknownValue(),
                ((XYNode.YBlock) peek(1)).colorAttributes((ColorAttributes) pop())
        );
    }

    Rule xyLineAttributes() {
        return Sequence(Sp(), MINUS, Sp(), "line", COLON, Sp(),
                push(new LineAttributes()),
                Optional(
                        lineAttribute(),
                        ZeroOrMore(Sp(), COMMA, Sp(), lineAttribute())
                ),
                ZeroOrMore(TestNot(Newline()), BaseParser.ANY),
                ((XYNode.YBlock) peek(1)).lineAttributes((LineAttributes) pop())
        );
    }

    @SuppressSubnodes
    Rule xyAreaAttributes() {
        StringBuilderVar text = new StringBuilderVar();
        return Sequence(Sp(), MINUS, Sp(), "area", COLON, Sp(),
                ZeroOrMore(TestNot(Newline()), BaseParser.ANY),
                ((XYNode.YBlock) peek()).areaAttributes(text.getString()));
    }

    @SuppressSubnodes
    Rule xyPointAttributes() {
        StringBuilderVar text = new StringBuilderVar();
        return Sequence(Sp(), MINUS, Sp(), "point", COLON, Sp(),
                push(new PointAttributes()),
                Optional(pointAttribute(),
                        ZeroOrMore(Sp(), COMMA, Sp(), pointAttribute())
                ),
                ZeroOrMore(TestNot(Newline()), BaseParser.ANY),
                ((XYNode.YBlock) peek(1)).pointAttributes((PointAttributes) pop())
        );
    }

    @SuppressSubnodes
    Rule xyYs() {
        return Sequence(Sp(), "y", Sp(), COLON, Sp(),
                push(new Values()),
                LBRK, Sp(), CommaOrSpaceSeparatedValues(), Sp(), RBRK, Sp(),
                ((XYNode.YBlock) peek(1)).values((Values) pop()));
    }

    // ------------------------------------------------------------------------
    //
    //  Shared Attributes
    //
    // ------------------------------------------------------------------------

    Rule pointAttribute() {
        return FirstOf(
                sizeAttribute(),
                pointPattern(),
                colorValue()

        );
    }


    Rule sizeAttribute() {
        return Sequence(
                Optional(Sequence("size", Sp(), COLON, Sp())),
                FirstOf(
                        DecimalFloat(),
                        IntegerLiteral()),
                ((SizeAware) peek()).size(match())
        );
    }

    Rule pointPattern() {
        return Sequence(asciiOrDashedString(),
                ((PatternAware) peek()).pattern(match())
        );
    }

    Rule lineAttribute() {
        return FirstOf(
                widthAttribute(),
                linePattern(),
                colorValue()
        );
    }

    Rule colorAttribute() {
        return colorValue();
    }

    Rule widthAttribute() {
        return Sequence(
                Optional(Sequence("width", Sp(), COLON, Sp())),
                FirstOf(
                        DecimalFloat(),
                        IntegerLiteral()),
                ((WidthAware) peek()).width(match())
        );
    }

    Rule linePattern() {
        return Sequence(
                asciiOrDashedString(),
                ((PatternAware) peek()).pattern(match())
        );
    }

    Rule colorValue() {
        StringVar r = new StringVar();
        StringVar g = new StringVar();
        StringVar b = new StringVar();

        return FirstOf(
                Sequence("rgb(", Sp(),
                        Sequence(FirstOf(DecimalFloat(), IntegerLiteral()), r.set(match()), Sp(), COMMA, Sp()),
                        Sequence(FirstOf(DecimalFloat(), IntegerLiteral()), g.set(match()), Sp(), COMMA, Sp()),
                        Sequence(FirstOf(DecimalFloat(), IntegerLiteral()), b.set(match()), Sp(), ")"),
                        ((ColorAware) peek()).rgb(r.get(), g.get(), b.get())),
                Sequence("#", OneOrMore(HexDigit()),
                        ((ColorAware) peek()).hex(match())),
                unknownValue()
        );
    }

    Rule unknownValue() {
        return Sequence(ZeroOrMore(
                        TestNot(Newline()),
                        TestNot(COLON), BaseParser.ANY),
                ((UnknownValueAware) peek()).unknown(match())
        );
    }

    // ------------------------------------------------------------------------
    //
    //  Shared
    //
    // ------------------------------------------------------------------------

    Rule legend() {
        StringBuilderVar text = new StringBuilderVar();
        return Sequence(Sp(), "legend", Sp(), ":",
                OneOrMore(TestNot(Newline()), BaseParser.ANY, text.append(matchedChar())),
                Newline(),
                ((ChartNode) peek()).displayLegend(text.getString()));
    }

    // ------------------------------------------------------------------------
    //
    //  Helpers
    //
    // ------------------------------------------------------------------------

    boolean debug(String s) {
        System.out.println(s); // set breakpoint here if required
        return true;
    }

    @SuppressSubnodes
    Rule asciiOrDashedString() {
        return OneOrMore(
                FirstOf(Letter(), Ch('-')));
    }

    @SuppressSubnodes
    Rule CommaOrSpaceSeparatedValues() {
        return Sequence(
                CapturedLiteral(),
                ZeroOrMore(Sp(), FirstOf(COMMA, Spacechar()), Sp(), CapturedLiteral()));
    }

    @SuppressSubnodes
    Rule CapturedLiteral() {
        return Sequence(Literal(), ((Values) peek()).append(match()));
    }

    @SuppressNode
    Rule CapturedNumber() {
        return Sequence(
                FirstOf(DecimalFloat(), IntegerLiteral()),
                ((Values) peek()).append(match()));
    }

    @SuppressSubnodes
    Rule CaptureLineIgnored() {
        StringBuilderVar text = new StringBuilderVar();
        return Sequence(
                ZeroOrMore(TestNot(Newline()), BaseParser.ANY),
                ((IgnoredLineAware) peek()).appendIgnoredLine(text.getString()));
    }


    final Rule COMMA = Terminal(",");
    final Rule COLON = Terminal(":");
    final Rule LBRK = Terminal("[");
    final Rule RBRK = Terminal("]");
    final Rule MINUS = Terminal("-");
    //
    final Rule TYPE = Terminal("type");
    final Rule PIE = Terminal("pie");
    final Rule XY = Terminal("xy");

    Rule Terminal(String value) {
        return String(value);
    }
}
