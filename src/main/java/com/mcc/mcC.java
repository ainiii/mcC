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
        parser.parse(Arrays.asList("2+3-4/5\\6*7"));

        mcCGraph graph = new mcCGraph(parser.tree);
        graph.draw();
    }
}