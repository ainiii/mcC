package parser;

import other.mcCSyntax;

public class ParserLib {
    public static boolean isWhitespace(String str) {
        return " ".contains(str);
    }

    public static boolean isOperand(String str) {
        return mcCSyntax.getOperators().indexOf(str) != -1;
    }

    public static boolean isDigit(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    // add more helper methods
}
