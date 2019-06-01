import parser.Parser;

public class mcC {
    public static void main(String[] args) {
        if (args.length == 0) {
            // error
            return;
        }
        
        String fileName = args[0];

        Parser parser = new Parser(fileName);
        parser.parse();

        // parse file
        // generate bytecode
    }
}