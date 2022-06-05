public class OffByOne implements CharacterComparator {
    /** return if the difference between ascii code of x and y is 1.*/
    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return (diff == 1 || diff == -1);
    }
}
