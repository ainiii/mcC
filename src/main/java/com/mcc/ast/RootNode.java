package ast;

import java.util.ArrayList;
import java.util.List;

public class RootNode {
    // stores every parsed node
    public List<AstNode> nodes = new ArrayList<>();

    public RootNode() { }

    public void addNode(AstNode node) {
        this.nodes.add(node);
    }

    // function to generate bytecode
}
