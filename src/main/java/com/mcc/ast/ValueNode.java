package ast;

public class ValueNode extends AstNode {
    // never has left/right nodes, only used to store simple values
    public String value;

    public ValueNode(String value) {
        this.value = value;
    }

}
