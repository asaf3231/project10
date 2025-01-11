import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JackTokenizer {
    private static BufferedReader reader;
    private static String currLine;
    private static String nextLine;
    public static String currToken;
    private static String nextToken;
    private static ArrayList<String> tokens ;
    private static int counter;

    private static HashMap<String,String> map ; 

    public JackTokenizer(File file) throws IOException {
        reader = new BufferedReader(new FileReader(file));
        currLine = null;
        nextLine = reader.readLine();
        tokens =  new ArrayList<>();
        map = new HashMap<>();
        counter = 0 ;
        cleanFile();
        buildMap();
    }


    public static void cleanFile() throws IOException {
        String[] dividers = {
            "\\{", "\\}", "\\(", "\\)", "\\[", "\\]", "\\.", ",", ";", "\\+", "-", "\\*", "/", "&", "\\|",
            "<", ">", "=", "~" , "class", "constructor", "function", "method", "field", "static", "var",
            "char", "boolean", "void", "true", "false", "null", "this", "let",
            "do", "if", "else", "while", "return"
        };
    
        // Create a regex pattern to include dividers as separate tokens
        String regex = "(?<=(" + String.join("|", dividers) + "))|(?=(" + String.join("|", dividers) + "))";
    
        while (hasMorelines()) {
            int commentIndex = currLine.indexOf("//");
            if (commentIndex != -1) {
                currLine = currLine.substring(0, commentIndex).trim();
            }
            // Split the current line by the regex
            String[] words = currLine.split(regex);
    
            for (String token : words) {
                token = token.trim(); // Trim whitespace around tokens
            
                if (!token.isEmpty()) { // Skip empty tokens
                    tokens.add(token); // Add every token directly to the list
                }
            }
            advanceline(); // Move to the next line
        }
    }

    
    public static void buildMap() {
        // Keywords
        String[] keywords = {
            "class", "constructor", "function", "method", "field", "static", "var",
            "int", "char", "boolean", "void", "true", "false", "null", "this", "let",
            "do", "if", "else", "while", "return"
        };

        // Symbols
        String[] symbols = {
            "{", "}", "(", ")", "[", "]", ".", ",", ";", "+", "-", "*", "/", "&", "|",
            "<", ">", "=", "~"
        };

        // Add keywords to the map
        for (String keyword : keywords) {
            map.put(keyword, "KEYWORD");
        }

        // Add symbols to the map
        for (String symbol : symbols) {
            map.put(symbol, "SYMBOL");
        }
    }

    public static boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true; // Parsing was successful, so it's a valid integer
        } catch (NumberFormatException e) {
            return false; // Parsing failed, so it's not a valid integer
        }
    }

    public static boolean hasMorelines() throws IOException{
        while(nextLine != null && (nextLine.trim().isEmpty() || nextLine.startsWith("//"))){
            currLine =nextLine.trim();
            nextLine= reader.readLine();
        }    
        return (nextLine != null);
    }

    public static boolean hasMoreTokens() throws IOException{
        return counter != tokens.size();
    }

    public static void advanceline() throws IOException {
        currLine = nextLine;      
        nextLine= reader.readLine();
    }

    public static void advance() throws IOException {

        if (counter < tokens.size() - 1) { // Check if there are more tokens
            currToken = tokens.get(counter);
            counter++;
            nextToken = tokens.get(counter);
        } else if (counter < tokens.size()) { // Handle the last token
            currToken = tokens.get(counter);
            nextToken = null; // No next token available
            counter++;
        } else {
            throw new IndexOutOfBoundsException("No more tokens to advance.");
        }
    }


    public static  String tokenType () throws IOException {

        if (map.containsKey(currToken)){
            return map.get(currToken);
        }
        if (isValidInteger(currToken)){
            return "INT_CONST";
        }   

        if( (currToken.charAt(0) == '\"' ) && (currToken.charAt(currToken.length() - 1) == '\"' )){
            return "STRING_CONST";
        }

        else{
            return "IDENTIFIER";
        }
    } 

    public static String keyWord() throws IOException {
        return  currToken.toUpperCase(); 
    }

    public static char symbol() throws IOException {
        return currToken.charAt(0);
    }

    public static String identifier() throws IOException {
        return currToken;
    }

    public static int intVal() throws IOException {
        return Integer.parseInt(currToken);
    }

    public static String stringVal() throws IOException {
        return currToken;
    }

}
