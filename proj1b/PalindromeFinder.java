
/** This class outputs all palindromes in the words file in the current directory.*/
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        int maxlengthsofar = 1;
        String maxlengthword = "";
        Palindrome palindrome = new Palindrome();
        CharacterComparator ofo = new OffByOne();
        CharacterComparator[] ofarray = new OffByN[27];
        for (int i = 0; i < 27; i++) {
            ofarray[i] = new OffByN(i);
        }
        for (int j = 0; j < 27; j++) {
            In in = new In("../library-sp18/data/words.txt");
            int count = 0;
            while (!in.isEmpty()) {
                String word = in.readString();
                if (word.length() >= minLength && palindrome.isPalindrome(word, ofarray[j])) {
                    //System.out.println(word);
                    if (word.length() > maxlengthsofar) {
                        maxlengthsofar = word.length();
                        maxlengthword = word;
                    }
                    count++;
                }
            }
            System.out.println("the count of offby"+j+" Palindrome is"+count);
        }
        System.out.print("the max length of any N Palindrome is "+maxlengthsofar);
        System.out.println("and the word is "+maxlengthword);
    }
}
