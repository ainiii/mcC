package parser;

import java.util.List;

public class ParserStream {
    private List<String> input;
    private static int pos = 0; // character in string
    private static int line = 0; // line number

    public ParserStream(List<String> lines) {
        this.input = lines;
    }

    public String next() {
        if (eof()) {
            return "";
        }

        // next entry
        if (pos == input.get(line).length()) {
            line++;
            pos = 0;
        }

        return String.valueOf(input.get(line).charAt(pos++));
    }

    public String peek() {
        if (eof()) {
            return "";
        }

        if (eol()) {
            line++;
            pos = 0;
            return "";
        }

        return String.valueOf(input.get(line).charAt(pos));
    }

    public String peekKeyword() {
        StringBuilder result = new StringBuilder();

        for (int i = pos; i < input.get(line).length(); i++) {
            String str = String.valueOf(input.get(line).charAt(i));

            if (ParserLib.isWhitespace.test(str)) {
                break;
            }

            result.append(str);
        }

        return result.toString();
    }

    public void skipKeyword() {
        pos += peekKeyword().length();
    }

    public void skipLine() {
        pos = 0;
        line += 1;
    }

    public boolean eof() {
        return line >= this.input.size();
    }

    public boolean eol() {
        return eof() || pos >= input.get(line).length();
    }
}
