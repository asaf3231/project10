import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class JackTokenizer {
    private static BufferedReader reader;
    private static String currLine;
    private static String nextLine;
    private static String currToken;
    private static String nextToken;
    private static ArrayList<String>words ;
    private static ArrayList<String> tokens ;

    private static HashMap<String,String> map ; 



        public JackTokenizer(File file) throws IOException {
            reader = new BufferedReader(new FileReader(file));
            currLine = null;
            nextLine = reader.readLine();
            words =  new ArrayList<>();
            map = new HashMap<>();
            cleanFile();
            buildMap();
        }

        public static void cleanFile() throws IOException {
            String[] dividers = {
                "\\{", "\\}", "\\(", "\\)", "\\[", "\\]", "\\.", ",", ";", "\\+", "-", "\\*", "/", "&", "\\|",
                "<", ">", "=", "~"
            };
        
            // Create a regex pattern to include dividers as separate tokens
            String regex = "(?<=(" + String.join("|", dividers) + "))|(?=(" + String.join("|", dividers) + "))";
        
            while (hasMorelines()) {
                // Split the current line by the regex
                String[] tokens = currLine.split(regex);
        
                for (String token : tokens) {
                    token = token.trim(); // Trim whitespace around tokens
                
                    if (!token.isEmpty()) { // Skip empty tokens
                        words.add(token); // Add every token directly to the list
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
                map.put(keyword, "keyword");
            }
    
            // Add symbols to the map
            for (String symbol : symbols) {
                map.put(symbol, "symbol");
            }
        }

        public static void makeTokens(){
            boolean psicuda = false;

            for(String word : words){
                if (map.containsKey(word)){
                    tokens.add(word);
                     continue;
                }

                else{
                    if (word.contains(";")){
                        word = word.substring(0,  word.indexOf(';')-1);
                        psicuda = true;
                    }   

                    if(word.charAt(0 ) == '\"'){
                        tokens.add(word.substring(1, word.length() -1 ));
                        continue;
                    }

                    if(word.charAt(0 ) == '('){
                        tokens.add(word.substring(0, 1));
                        tokens.add(word.substring(1));
                        continue;
                    }

                    if(word.charAt(word.length() -1 ) == ')'){
                        tokens.add(word.substring(0, word.length() -1 ));
                        tokens.add(word.substring(word.length() -1));
                        continue;
                    }

                    if(isValidInteger(word)){
                        tokens.add(word);
                        continue;
                    }
      
                }

                if (psicuda){
                    tokens.add(";");
                    psicuda = false; 
                } 
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
            while(nextLine != null && (nextLine.trim().isEmpty() || nextLine.charAt(0) == '/' )){
                currLine =nextLine.trim();
                nextLine= reader.readLine();
            }    
            return (nextLine != null);
        }

        public static boolean hasMoreTokens() throws IOException{

        }

        public static void advanceline() throws IOException {
            currLine = nextLine;      
            nextLine= reader.readLine();
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
