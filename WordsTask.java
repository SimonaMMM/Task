import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WordsTask {

    private final Set<String> allWords;

    public WordsTask() throws IOException {
        allWords = loadAllWords();
    }

    private boolean isValidAfterCharRemoval(String word) {
        if (word.equals("A") || word.equals("I")) {
            return true;
        }

        if (!allWords.contains(word)) {
            return false;
        }

        for (int i = 0 ; i < word.length() ; i++) {
            boolean result = isValidAfterCharRemoval(word.substring(0, i) + word.substring(i + 1));

            if (result) {
                return true;
            }
        }

        return false;
    }

    private static Set<String> loadAllWords() throws IOException {
        URL wordsUrl = new URL("https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(wordsUrl.openConnection().getInputStream())))  {
            Predicate<String> nineLettersWords = s -> (s.length() <= 9 && (s.contains("I") || (s.contains("A"))));

            return br.lines().skip(2).filter(nineLettersWords).collect(Collectors.toSet());
        }
    }

    public List<String> getAllNineLetterWords() {
        List<String> result = new ArrayList<>();

        for (String word : allWords) {
            if (word.length() == 9 && isValidAfterCharRemoval(word)) {
                result.add(word);
            }
        }

        return result;
    }


    public static void main(String[] args) throws IOException {
        WordsTask task = new WordsTask();
        List<String> allNineLetterWords = task.getAllNineLetterWords();
        
        for (String word : allNineLetterWords) {
            System.out.println(word);
        }
    }
}
