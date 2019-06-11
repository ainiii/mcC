import ast.*;
import org.junit.jupiter.api.Test;
import parser.Parser;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {
    @Test
    void declareBoolNodeTest() {
        Parser parser = new Parser("");
        parser.parse(Arrays.asList("bool a = true"));

        assertEquals(1, parser.tree.nodes.size());
        assertTrue(parser.tree.nodes.get(0) instanceof DeclareBoolNode);
    }

    @Test
    void declareDoubleNodeTest() {
        Parser parser = new Parser("");
        parser.parse(Arrays.asList("double a = 1.0"));

        assertEquals(1, parser.tree.nodes.size());
        assertTrue(parser.tree.nodes.get(0) instanceof DeclareDoubleNode);
    }

    @Test
    void declareIntNodeTest() {
        Parser parser = new Parser("");
        parser.parse(Arrays.asList("int a = 1"));

        assertEquals(1, parser.tree.nodes.size());
        assertTrue(parser.tree.nodes.get(0) instanceof DeclareIntNode);
    }

    @Test
    void declareStringNodeTest() {
        Parser parser = new Parser("");
        parser.parse(Arrays.asList("string a = test"));

        assertEquals(1, parser.tree.nodes.size());
        assertTrue(parser.tree.nodes.get(0) instanceof DeclareStringNode);
    }

    @Test
    void functionPrintTest() {
        Parser parser = new Parser("");
        parser.parse(Arrays.asList("print test"));

        assertEquals(1, parser.tree.nodes.size());
        assertTrue(parser.tree.nodes.get(0) instanceof FunctionPrintNode);
    }

    @Test
    void assignValuesTest() {
        Parser parser = new Parser("");
        parser.parse(Arrays.asList("bool a = true", "double a = 1.0", "string a = test", "print test"));

        assertEquals(4, parser.tree.nodes.size());

        for (AstNode node : parser.tree.nodes) {
            assertTrue(node.left instanceof ValueNode || node.right instanceof ValueNode);
        }
    }
}