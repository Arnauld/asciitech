package org.technbolts.asciitech.parser.ast;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
import org.parboiled.trees.GraphNode;

public interface Node extends GraphNode<Node> {

    /**
     * @return the index of the first character in the underlying buffer that is covered by this node
     */
    int getStartIndex();

    /**
     * @return the index of the character after the last one in the underlying buffer that is covered by this node
     */
    int getEndIndex();

    void accept(Visitor visitor);
}