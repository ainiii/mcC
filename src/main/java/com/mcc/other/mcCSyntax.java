package other;

import ast.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class mcCSyntax {
    public static String getField(mcCSyntax.Syntax s) {
        return s.field;
    }

    public static boolean isTypeOf(String str, EnumType type) {
        Syntax s = getEnumFromField(str);

        if (s == null) {
            return false;
        }

        return s.type == type;
    }

    @Nullable
    public static Syntax getEnumFromField(String str) {
        for (Syntax s : Syntax.values()) {
            if (s.field.equals(str)) {
                return s;
            }
        }

        return null;
    }

    @Nullable
    public static AstNode getObjectFromField(String str) {
        for (Syntax s : Syntax.values()) {
            if (s.field.equals(str)) {
                try {
                    return (AstNode)s.nodeClass.newInstance();
                } catch (Exception e) {
                    return null;
                }
            }
        }

        return null;
    }

    public static List<String> getByType(EnumType type) {
        ArrayList<String> result = new ArrayList<>();

        for (Syntax s : Syntax.values()) {
            if (s.type == type) {
                result.add(s.field);
            }
        }

        return result;
    }

    public enum Syntax {
        // store language syntax for parser

        // types
        INT("int", DeclareIntNode.class, EnumType.TYPE),
        DOUBLE("double", DeclareDoubleNode.class, EnumType.TYPE),
        STRING("string", DeclareStringNode.class, EnumType.TYPE),
        BOOL("bool", DeclareBoolNode.class, EnumType.TYPE),

        // bool states
        TRUE("true", null, EnumType.STATE),
        FALSE("false", null, EnumType.STATE),

        // operators
        PLUS("+", OperatorPlusNode.class, EnumType.OPERATOR),
        MINUS("-", OperatorMinusNode.class, EnumType.OPERATOR),
        MULTIPLICATION("*", OperatorMultiplicationNode.class, EnumType.OPERATOR),
        INTDIVISION("/", OperatorIntDivisionNode.class, EnumType.OPERATOR),
        DOUBLEDIVISION("\\", OperatorDoubleDivisionNode.class, EnumType.OPERATOR),
        GREATHERTHAN(">", OperatorGreaterThanNode.class, EnumType.OPERATOR),
        LESSTHAN("<", OperatorLessThanNode.class, EnumType.OPERATOR),
        ASSIGN("=", OperatorAssignNode.class, EnumType.OPERATOR),
        EQUAL("==", OperatorEqualNode.class, EnumType.OPERATOR),

        // additional
        COMMENT("#", null, null);

        private final String field;
        private final Class nodeClass;
        private final EnumType type;

        Syntax(String field, Class nodeClass, EnumType type) {
            this.field = field;
            this.nodeClass = nodeClass;
            this.type = type;
        }
    }

    public enum EnumType {
        OPERATOR,
        TYPE,
        STATE
    }
}
