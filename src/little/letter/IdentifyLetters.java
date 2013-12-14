package little.letter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class IdentifyLetters {

    private static final Character[] ALPHABETS_CAPS;
    private static final Character[] ALPHABETS_SMALL;
    private static final String[] NUMBERS;
    
    static {
        ALPHABETS_CAPS = new Character[26];
        ALPHABETS_SMALL = new Character[26];
        for (int i = 0; i < ALPHABETS_CAPS.length; i++) {
            ALPHABETS_CAPS[i] = (char)('A' + i);
            ALPHABETS_SMALL[i] = (char)('a' + i);
        }
        
        NUMBERS = new String[11];
        for (int i = 0; i < NUMBERS.length; i++) {
            NUMBERS[i] = String.valueOf(i);
        }
    }
    
    public static void main(String[] args) {
        //System.out.println(Arrays.toString(ALPHABETS_CAPS));
        //System.out.println(Arrays.toString(ALPHABETS_SMALL));
        //System.out.println(Arrays.toString(NUMBERS));
        
        generate(ALPHABETS_CAPS, 5, 6);

    }

    private static void generate(Character[] domainLetters, int rows, int columns) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        
        for(Character ch : domainLetters) {
            map.put(ch, Integer.valueOf(0));
        }
        
        Generator<Character> alphabetsCapsGenerator = new Generator<Character>(domainLetters);
        
        String SPACE = "   ";
        for (int i = 0 ; i < rows; i++) {
            for (int j = 0; j < columns; j++) { 
                Character next = alphabetsCapsGenerator.getNext();
                map.put(next, map.get(next) + 1);
                System.out.print(next + SPACE);
            }
            System.out.println();
            System.out.println();
            if (i % 2 == 0) {
                System.out.print(SPACE);
            }
        }
        
        Character topper = topper(map);
        System.out.println(SPACE + SPACE + "Circle " + topper);
    }

    private static <T> T topper(Map<T, Integer> map) {
        Integer max = 0;
        T maxItem = null;
        
        for(Entry<T, Integer> entry : map.entrySet()) {
            if (max < entry.getValue()) {
                maxItem = entry.getKey();
                max = entry.getValue();
            }
        }
        
        return maxItem;
    }
    
    static class Generator<T> {
        private final T[] domain;
        private Random rand = new Random();
        
        Generator(T[] _domain) {
            domain = _domain;
        }
        
        public T getNext() {            
            return domain[rand.nextInt(domain.length)];
        }
    }
}
