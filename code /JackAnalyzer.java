import java.io.File;
import java.io.IOException;


public class JackAnalyzer {

    public static void main(String[] args) throws IOException {
            CompilationEngine ce = new CompilationEngine(new File(args[0]), new File(args[1]));
        }
}
