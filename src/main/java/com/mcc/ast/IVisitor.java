package ast;

public interface IVisitor {
    // lists all visit methods
    public void visit(DeclareBoolNode node);
    public void visit(DeclareDoubleNode node);
    public void visit(DeclareIntNode node);
    public void visit(DeclareStringNode node);

    public void visit(FunctionPrintNode node);

    public void visit(OperatorAssignNode node);
    public void visit(OperatorDoubleDivisionNode node);
    public void visit(OperatorEqualNode node);
    public void visit(OperatorGreaterThanNode node);
    public void visit(OperatorIntDivisionNode node);
    public void visit(OperatorLessThanNode node);
    public void visit(OperatorMinusNode node);
    public void visit(OperatorMultiplicationNode node);

    public void visit(StructureForNode node);
    public void visit(StructureIfNode node);
}
