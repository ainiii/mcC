package parser;

import java.util.List;

public class ParserStream {
    private List<String> lines;
    private int pos = 0; // character in string
    private int index = 0; // line number

    public ParserStream(List<String> lines) {
        this.lines = lines;
    }

    public char next() {
        char ch = lines.get(index).charAt(pos++);

        // next entry
        if (pos == lines.get(index).length()) {
            index++;
            pos = 0;
        }

        return ch;
    }

    public char peek() {
        return lines.get(index).charAt(pos);
    }

    public boolean eof() {
        return this.lines.size() == index;
    }
}
