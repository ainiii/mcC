import compiler.Compiler;
import compiler.ConstantPool;
import other.mcCGraph;
import parser.Parser;

import java.util.Arrays;

public class mcC {
    public static void main(String[] args) {
        /*if (args.length == 0) {
            // error
            return;
        }
        
        String fileName = args[0];*/

        Parser parser = new Parser("");
        parser.parse(Arrays.asList("bool a = true", "int b = 1", "double c = 3.14 + 4.15 - 2", "string d = twoja stara", "print asdasd 1212"));

        //mcCGraph graph = new mcCGraph(parser.tree);
        //graph.draw();

        ConstantPool.initPool();
        Compiler compiler = new Compiler();
        String result = compiler.compile(parser.tree);
        System.out.println(result);
    }
}
