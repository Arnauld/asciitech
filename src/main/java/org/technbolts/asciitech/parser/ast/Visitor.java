package org.technbolts.asciitech.parser.ast;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */

public interface Visitor {
    void visit(TextNode node);
    void visit(Node node); // general catch all for custom Node implementations
}
