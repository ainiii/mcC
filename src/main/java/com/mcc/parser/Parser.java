package parser;

import ast.AstNode;
import ast.RootNode;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Parser {
    private String fileName;
    private RootNode nodes = new RootNode();

    public Parser(String file) {
        this.fileName = file;
    }

    @Nullable
    public RootNode parse() {
        // parse line by line
        try {
            ParserStream stream = new ParserStream(Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));

            // process each line and add to nodes list
            while (!stream.eof()) {
                this.nodes.addNode(this.process(stream));
            }
        } catch(IOException err) {
            // error
            return null;
        }

        return this.nodes;
    }

    private AstNode process(ParserStream stream) {
        // check for language keywords
        // parse to AstNode
        // throw error if needed
        return null;
    }
}
