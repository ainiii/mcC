package parser;

import ast.RootNode;
import ast.AstNode;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Parser {
    public RootNode tree = new RootNode();
    private String fileName;

    public Parser(String file) {
        this.fileName = file;
    }

    @Nullable
    public void parse() {
        // parse line by line
        try {
            ParserStream stream = new ParserStream(Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));

            // process each line and add to nodes list
            while (!stream.eof()) {
                this.tree.addNode(this.process(stream));
            }
        } catch(IOException err) {
            // error
            return;
        }
    }

    private AstNode process(ParserStream stream) {
        // check for language keywords
        // parse to AstNode
        // throw error if needed
        return null;
    }
}
