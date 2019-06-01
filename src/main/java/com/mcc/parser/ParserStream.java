package parser;

import java.util.List;

public class ParserStream {
    private List<String> input;
    private int pos = 0; // character in string
    private int line = 0; // line number

    public ParserStream(List<String> lines) {
        this.input = lines;
    }

    public String next() {
        String str = String.valueOf(input.get(line).charAt(pos++));

        // next entry
        if (pos == input.get(line).length()) {
            line++;
            pos = 0;
        }

        return str;
    }

    public String peek() {
        return String.valueOf(input.get(line).charAt(pos));
    }

    public boolean eof() {
        return this.input.size() == line;
    }
}
