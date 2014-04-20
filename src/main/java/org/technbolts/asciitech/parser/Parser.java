package org.technbolts.asciitech.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.MemoMismatches;
import org.parboiled.annotations.SuppressSubnodes;
import org.technbolts.asciitech.parser.ast.AbstractNode;
import org.technbolts.asciitech.parser.ast.TextNode;

import static org.parboiled.common.StringUtils.repeat;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class Parser extends BaseParser<Object> {
    public Rule NOrMore(char c, int n) {
        return Sequence(repeat(c, n), ZeroOrMore(c));
    }

    public Rule NodeSequence(Object... nodeRules) {
        return Sequence(
                push(getContext().getCurrentIndex()),
                Sequence(nodeRules),
                setIndices()
        );
    }

    public boolean setIndices() {
        AbstractNode node = (AbstractNode) peek();
        node.setStartIndex((Integer) pop(1));
        node.setEndIndex(currentIndex());
        return true;
    }

    public Rule Space() {
        return NodeSequence(OneOrMore(Spacechar()), push(new TextNode(" ")));
    }

    public Rule Spn1() {
        return Sequence(Sp(), Optional(Newline(), Sp()));
    }

    public Rule Sp() {
        return ZeroOrMore(Spacechar());
    }

    public Rule Spacechar() {
        return AnyOf(" \t");
    }

    public Rule Nonspacechar() {
        return Sequence(TestNot(Spacechar()), NotNewline(), ANY);
    }

    public Rule NotNewline() {
        return TestNot(AnyOf("\n\r"));
    }

    public Rule Newline() {
        return FirstOf('\n', Sequence('\r', Optional('\n')));
    }

    public Rule NonindentSpace() {
        return FirstOf("   ", "  ", " ", EMPTY);
    }

    public Rule Indent() {
        return FirstOf('\t', "    ");
    }

    public Rule Alphanumeric() {
        return FirstOf(Letter(), Digit());
    }

    public Rule Letter() {
        return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'));
    }

    public Rule Digit() {
        return CharRange('0', '9');
    }

    public Rule Literal() {
        return Sequence(
                FirstOf(
                        FloatLiteral(),
                        IntegerLiteral(),
                        CharLiteral(),
                        StringLiteral()
                ),
                Sp()
        );
    }

    @SuppressSubnodes
    public Rule IntegerLiteral() {
        return Sequence(FirstOf(HexNumeral(), OctalNumeral(), DecimalNumeral()), Optional(AnyOf("lL")));
    }

    @SuppressSubnodes
    public Rule DecimalNumeral() {
        return FirstOf('0', Sequence(CharRange('1', '9'), ZeroOrMore(Digit())));
    }

    @SuppressSubnodes
    @MemoMismatches
    public Rule HexNumeral() {
        return Sequence('0', IgnoreCase('x'), OneOrMore(HexDigit()));
    }

    public Rule HexDigit() {
        return FirstOf(CharRange('a', 'f'), CharRange('A', 'F'), CharRange('0', '9'));
    }

    @SuppressSubnodes
    public Rule OctalNumeral() {
        return Sequence('0', OneOrMore(CharRange('0', '7')));
    }

    public Rule FloatLiteral() {
        return FirstOf(HexFloat(), DecimalFloat());
    }

    @SuppressSubnodes
    public Rule DecimalFloat() {
        return FirstOf(
                Sequence(OneOrMore(Digit()), '.', ZeroOrMore(Digit()), Optional(Exponent()), Optional(AnyOf("fFdD"))),
                Sequence('.', OneOrMore(Digit()), Optional(Exponent()), Optional(AnyOf("fFdD"))),
                Sequence(OneOrMore(Digit()), Exponent(), Optional(AnyOf("fFdD"))),
                Sequence(OneOrMore(Digit()), Optional(Exponent()), AnyOf("fFdD"))
        );
    }

    public Rule Exponent() {
        return Sequence(AnyOf("eE"), Optional(AnyOf("+-")), OneOrMore(Digit()));
    }

    @SuppressSubnodes
    public Rule HexFloat() {
        return Sequence(HexSignificant(), BinaryExponent(), Optional(AnyOf("fFdD")));
    }

    public Rule HexSignificant() {
        return FirstOf(
                Sequence(FirstOf("0x", "0X"), ZeroOrMore(HexDigit()), '.', OneOrMore(HexDigit())),
                Sequence(HexNumeral(), Optional('.'))
        );
    }

    public Rule BinaryExponent() {
        return Sequence(AnyOf("pP"), Optional(AnyOf("+-")), OneOrMore(Digit()));
    }

    public Rule CharLiteral() {
        return Sequence(
                '\'',
                FirstOf(Escape(), Sequence(TestNot(AnyOf("'\\")), ANY)).suppressSubnodes(),
                '\''
        );
    }

    public Rule StringLiteral() {
        return Sequence(
                '"',
                ZeroOrMore(
                        FirstOf(
                                Escape(),
                                Sequence(TestNot(AnyOf("\r\n\"\\")), ANY)
                        )
                ).suppressSubnodes(),
                '"'
        );
    }

    public Rule Escape() {
        return Sequence('\\', FirstOf(AnyOf("btnfr\"\'\\"), OctalEscape(), UnicodeEscape()));
    }

    public Rule OctalEscape() {
        return FirstOf(
                Sequence(CharRange('0', '3'), CharRange('0', '7'), CharRange('0', '7')),
                Sequence(CharRange('0', '7'), CharRange('0', '7')),
                CharRange('0', '7')
        );
    }

    public Rule UnicodeEscape() {
        return Sequence(OneOrMore('u'), HexDigit(), HexDigit(), HexDigit(), HexDigit());
    }

}
