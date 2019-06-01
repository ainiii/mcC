package parser;

import ast.*;
import other.mcCSyntax;

import java.util.function.Predicate;

import static other.mcCSyntax.Syntax.COMMENT;

public class ParserLib {
    public static Predicate<String> isWhitespace = (str) -> str.equals(" ");
    public static Predicate<String> isOperand = (str) -> mcCSyntax.getByType(mcCSyntax.EnumType.OPERATOR).indexOf(str) != -1;
    public static Predicate<String> isType = (str) -> mcCSyntax.getByType(mcCSyntax.EnumType.TYPE).indexOf(str) != -1;
    public static Predicate<String> isState = (str) -> mcCSyntax.getByType(mcCSyntax.EnumType.STATE).indexOf(str) != -1;
    public static Predicate<String> isDigit = (str) -> str.matches("-?\\d+(\\.\\d+)?");


    public static String readUntil(ParserStream stream, Predicate<String> predicate) {
        StringBuilder str = new StringBuilder();
        String current = stream.peek();

        while (predicate.test(current)) {
            str.append(current);
            stream.next();

            if (stream.eol()) {
                break;
            }

            current = stream.peek();
        }

        return str.toString();
    }

    public static void readWhitespace(ParserStream stream) {
        while (!stream.eof()) {
            String current = stream.peek();

            if (isWhitespace.test(current)) {
                stream.next();
                current = stream.peek();
            } else {
                return;
            }
        }
    }

    public static void readComment(ParserStream stream) {
        if (stream.peek().equals(mcCSyntax.getField(COMMENT))) {
            stream.skipLine();
        }
    }

    public static String readNumber(ParserStream stream) {
        String current = stream.peek();
        StringBuilder str = new StringBuilder();

        while (isDigit.test(current) || current.equals(".")) {
            str.append(current);
            stream.next();

            if (stream.eol()) {
                break;
            }

            current = stream.peek();
        }

        return str.toString();
    }

    public static String readUntilEnd(ParserStream stream) {
        StringBuilder str = new StringBuilder();

        while (!stream.eol()) {
            str.append(stream.next());
        }

        stream.next();

        return str.toString();
    }

    public static boolean isValid(String str) {
        return !str.equals("");
    }

    public static boolean isInvalidOperator(AstNode node) {
        return node instanceof OperatorAssignNode ||
                node instanceof OperatorEqualNode ||
                node instanceof OperatorLessThanNode ||
                node instanceof OperatorGreaterThanNode;
    }

    // add more helper methods
}
