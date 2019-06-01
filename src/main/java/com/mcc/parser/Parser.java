package parser;

import ast.RootNode;
import ast.AstNode;
import ast.ValueNode;
import org.jetbrains.annotations.Nullable;
import other.mcCSyntax;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Parser {
    public RootNode tree = new RootNode();
    private String fileName;

    public Parser(String file) {
        this.fileName = file;
    }

    public List<String> readFile() {
        try {
            return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch(IOException err) {
            // error
            return null;
        }
    }

    @Nullable
    public void parse(List<String> lines) {
        // parse line by line
        ParserStream stream = new ParserStream(lines);

        // process each line and add to nodes list
        while (!stream.eof()) {
            this.tree.addNode(this.process(stream));
        }
    }

    private AstNode process(ParserStream stream) {
        ParserLib.readWhitespace(stream);
        ParserLib.readComment(stream);

        // error
        if (stream.eof()) {
            return null;
        }

        String nextChar = stream.peek();

        if (ParserLib.isDigit.test(nextChar)) {
            return parseMathOperation(stream, null);
        }

        if (ParserLib.isOperand.test(nextChar)) {
            //return ParserLib.readUntil(stream, ParserLib.isWhitespace);
        }

        return null;
    }

    // read left, token -> recur
    private AstNode parseMathOperation(ParserStream stream, @Nullable AstNode parent) {
        ParserLib.readWhitespace(stream);
        String number = ParserLib.readNumber(stream);

        // error
        if (!ParserLib.isValid(number)) {
            return null;
        }

        ParserLib.readWhitespace(stream);
        String token = ParserLib.readUntil(stream, ParserLib.isOperand);

        if (ParserLib.isOperand.test(token)) {
            AstNode astNode = mcCSyntax.getObjectFromField(token);

            if (astNode == null) {
                return null; // error
            }

            // first time call
            if (parent == null) {
                astNode.left = new ValueNode(number);
                parseMathOperation(stream, astNode);
                return astNode;
            } else { // make place for token
                parent.right = astNode;
                astNode.left = new ValueNode(number);
                parseMathOperation(stream, astNode);
            }
        } else {
            if (parent == null) {
                return null; // error
            }

            parent.right = new ValueNode(number);
        }

        return null; // not error
    }
}
