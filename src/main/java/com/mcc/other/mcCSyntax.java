package other;

import ast.*;

import java.util.ArrayList;
import java.util.List;

public class mcCSyntax {
    public static List<String> getOperators() {
        ArrayList<String> operators = new ArrayList<>();

        for (Syntax s : Syntax.values()) {
            if (s.type == EnumType.OPERATOR) {
                operators.add(s.field);
            }
        }

        return operators;
    }

    public enum Syntax {
        // store language syntax for parser

        // operators
        PLUS("+", OperatorPlusNode.class, EnumType.OPERATOR),
        MINUS("-", OperatorMinusNode.class, EnumType.OPERATOR),
        MULTIPLICATION("*", OperatorMultiplicationNode.class, EnumType.OPERATOR),
        INTDIVISION("/", OperatorIntDivisionNode.class, EnumType.OPERATOR),
        DOUBLEDIVISION("\\", OperatorDoubleDivisionNode.class, EnumType.OPERATOR),
        GREATHERTHAN(">", OperatorGreaterThanNode.class, EnumType.OPERATOR),
        LESSTHAN("<", OperatorLessThanNode.class, EnumType.OPERATOR),
        ASSIGN("=", OperatorAssignNode.class, EnumType.OPERATOR),
        EQUAL("==", OperatorEqualNode.class, EnumType.OPERATOR);

        private final String field;
        private final Class nodeClass;
        private final EnumType type;

        Syntax(String field, Class nodeClass, EnumType type) {
            this.field = field;
            this.nodeClass = nodeClass;
            this.type = type;
        }
    }

    private enum EnumType {
        OPERATOR
    }
}
