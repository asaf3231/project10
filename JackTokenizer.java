import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JackTokenizer {
    private static BufferedReader reader;
    private static String currLine;
    private static String nextLine;


    public JackTokenizer(File inputFile) throws  IOException{
        reader = new BufferedReader(new FileReader(inputFile));
        currLine = null;
        nextLine = reader.readLine();
    }

    public static boolean hasMoreTokens() throws  IOException {

        return false;
    }
    public static void advance() throws  IOException {

        return;
    }

    public static  String tokenType () throws  IOException {

        return "";
    } 

    public static String keyWord() throws  IOException {

        return "false";
    }

    public static char symbol() throws  IOException {

        return 'c';
    }

    public static String identifier() throws  IOException {

        return "false";
    }

    public static int intVal() throws  IOException {

        return 1;
    }

    public static String stringVal() throws  IOException {

        return "false";
    }
    
}
