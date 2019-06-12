import compiler.Compiler;
import compiler.ConstantPool;
import other.mcCGraph;
import parser.Parser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class mcC {
    public static void main(String[] args) {
        if (args.length == 0) {
            // error
            return;
        }
        
        String fileName = args[0];

        Parser parser = new Parser(fileName);
        parser.parse(parser.readFile());

        if (args.length > 1) {
            mcCGraph graph = new mcCGraph(parser.tree);
            graph.draw();
        }

        ConstantPool.initPool();

        Compiler compiler = new Compiler();
        String result = compiler.compile(parser.tree);

        result = result.replaceAll("\\s+", ""); // remove whitespaces
        result = result.replaceAll("(.{32})", "$1\n"); // insert new lines
        result = result.replaceAll("(.{4})", "$1 "); // insert spaces
        result = result.replaceAll("[\\n\\s]+\\n", "\n"); // remove spaces after new lines
        result = result.toLowerCase();
        result = result.trim();

        try {
            Files.write(Paths.get("./out.class"), result.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {

        }
    }
}
