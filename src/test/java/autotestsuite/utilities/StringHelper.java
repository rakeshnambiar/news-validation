package autotestsuite.utilities;

public class StringHelper {
    public static String removeSymbols(String input) throws Exception {
        return input.replaceAll("[^a-zA-Z0-9]"," ");
    }

    public static String removeMultipleBlankSpaces(String input) throws Exception {
        return input.trim().replaceAll("\\s+", " ");
    }
}
