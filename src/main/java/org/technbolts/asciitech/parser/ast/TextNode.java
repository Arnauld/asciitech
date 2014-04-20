package org.technbolts.asciitech.parser.ast;

import org.parboiled.common.ImmutableList;
import org.parboiled.common.StringUtils;

import java.util.List;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class TextNode extends AbstractNode {
    private final StringBuilder sb;

    public TextNode(String text) {
        this.sb = new StringBuilder(text);
    }

    public String getText() {
        return sb.toString();
    }

    public void append(String text) {
        sb.append(text);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + StringUtils.escape(getText()) + '\'';
    }

    public List<Node> getChildren() {
        return ImmutableList.of();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}