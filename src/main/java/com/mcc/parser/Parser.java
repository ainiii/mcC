package parser;

import ast.*;
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

        if (stream.eof()) {
            return null;
        }

        String nextChar = stream.peek();

        if (ParserLib.isDigit.test(nextChar)) {
            return parseBinaryOperation(stream, null);
        }

        String nextKeyword = stream.peekKeyword();

        if (ParserLib.isType.test(nextKeyword)) {
            return parseTypeDeclaration(stream);
        }

        if (ParserLib.isFunction.test(nextKeyword)) {
            return parseFunction(stream);
        }

        return null;
    }

    // read left, token -> recur
    private AstNode parseBinaryOperation(ParserStream stream, @Nullable AstNode parent) {
        ParserLib.readWhitespace(stream);
        String number = ParserLib.readNumber(stream);

        // error
        if (!ParserLib.isValid(number)) {
            return null;
        }

        ParserLib.readWhitespace(stream);
        String token = ParserLib.readUntil(stream, ParserLib.isOperand);

        if (!token.equals("")) {
            AstNode astNode = mcCSyntax.getObjectFromField(token);

            if (astNode == null) {
                return null; // error
            }

            // first time call
            if (parent == null) {
                astNode.left = new ValueNode(number);
                parseBinaryOperation(stream, astNode);
                return astNode;
            } else { // make place for token
                parent.right = astNode;
                astNode.left = new ValueNode(number);
                parseBinaryOperation(stream, astNode);
            }
        } else {
            // return node value only
            if (parent == null) {
                return new ValueNode(number);
            }

            parent.right = new ValueNode(number);
        }

        return null; // not error
    }

    private AstNode parseTypeDeclaration(ParserStream stream) {
        String nextKeyword = stream.peekKeyword();
        stream.skipKeyword();
        mcCSyntax.Syntax type = mcCSyntax.getEnumFromField(nextKeyword);

        // error, should never happen
        if (type == null) {
            return null;
        }

        ParserLib.readWhitespace(stream);
        String variableName = stream.peekKeyword();
        stream.skipKeyword();

        // error, missing variable name
        if (variableName.equals("")) {
            return null;
        }

        // get token, should be ASSIGN
        ParserLib.readWhitespace(stream);
        String token = ParserLib.readUntil(stream, ParserLib.isOperand);

        // error
        if (!token.equals(mcCSyntax.getField(mcCSyntax.Syntax.ASSIGN))) {
            return null;
        }

        ParserLib.readWhitespace(stream);
        AstNode ret;

        switch (type) {
            case INT:
                ret = new DeclareIntNode();
                ret.right = parseBinaryOperation(stream, null);
                break;
            case DOUBLE:
                ret = new DeclareDoubleNode();
                ret.right = parseBinaryOperation(stream, null);
                break;
            case STRING:
                ret = new DeclareStringNode();
                ret.right = new ValueNode(ParserLib.readUntilEnd(stream));
                break;
            case BOOL:
                ret = new DeclareBoolNode();
                String varAssign = stream.peekKeyword();

                if (mcCSyntax.isTypeOf(varAssign, mcCSyntax.EnumType.STATE)) {
                    ret.right = new ValueNode(varAssign);
                    stream.skipKeyword();
                } else {
                    return null; // error, not a type keyword
                }
                break;
            default:
                return null; // error, should never happen
        }

        ret.left = new ValueNode(variableName);
        return ret;
    }

    private AstNode parseFunction(ParserStream stream) {
        String nextKeyword = stream.peekKeyword();
        stream.skipKeyword();
        mcCSyntax.Syntax type = mcCSyntax.getEnumFromField(nextKeyword);

        // error, should never happen
        if (type == null) {
            return null;
        }

        ParserLib.readWhitespace(stream);
        AstNode ret;

        switch (type) {
            case PRINT:
                ret = new FunctionPrintNode();
                ret.right = new ValueNode(ParserLib.readUntilEnd(stream));
                break;
            default:
                return null; // error, should never happen
        }

        return ret;
    }
}
