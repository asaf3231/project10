import java.io.File;
import java.io.IOException;


public class JackAnalyzer {

    public static void main(String[] args) throws IOException {
        File file= new File(args[0]);
        JackTokenizer jk = new JackTokenizer(file);

        while (jk.hasMoreTokens()){
            jk.advance();
            System.out.println(jk.currToken);
        }
    }
}
