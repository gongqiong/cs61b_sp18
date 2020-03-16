import java.util.*;

public class DemoCollection {
    public static String cleanString(String s){
        return s.toLowerCase().replaceAll("[^a-z]", "");
    }
    
    public static List<String> getWords(String inputFile){
        List<String> words = new ArrayList<>();
        In in = new In(inputFile);
        while (!in.isEmpty()){
            String nextWord = cleanString(in.readString());
            words.add(nextWord);
        }
        return words;
    }
    
    public static int countUniqueWords(List<String> words){
        /*
        Set<String> wordSet = new HashSet<>();
        for (String word: words){
            wordSet.add(word);
        }
         */
        Set<String> wordSet = new HashSet<>(words);
        return wordSet.size();
    }
    
    public static Map<String, Integer> collectWordCount(List<String> words, List<String> targets){
        Map<String, Integer> wordMap = new HashMap<>();
        return null;
    }
    
    public static void main(String[] args) {
        List<String> w = getWords("The prince.txt");
        
    }
}
