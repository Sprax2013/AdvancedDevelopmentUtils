package de.sprax2013.advanced_dev_utils.misc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StringUtils {
    @Deprecated
    public static String trimToMaxChars(String s, int maxChars) {
        return trimStringToMaxLength(s, maxChars);
    }

    public static String trimStringToMaxLength(String s, int maxLength) {
        if (s.length() > maxLength) {
            return s.substring(0, maxLength);
        }

        return new String(s);
    }

    /**
     * Get all values of a String array which start with a given String
     *
     * @param value String to search for
     * @param list  The array that should be searched in
     *
     * @return A list with all matches
     */
    public static List<String> getMatches(String value, String[] list, boolean caseInsetive) {
        List<String> result = new LinkedList<>();

        for (String str : list) {
            if (str.startsWith(value.toLowerCase())
                    || (caseInsetive && str.toLowerCase().startsWith(value.toLowerCase()))) {
                result.add(str);
            }
        }

        return result;
    }

    @Deprecated
    public static String getStartingWith(String[] stringArray, boolean caseInsensitive) {
        return getStartingWith(Arrays.asList(stringArray), caseInsensitive);
    }

    /**
     * Returns a String that every String from the list is starting with.<br>
     * Example:<br>
     * <br>
     * <code>List: [saveAll, save-all, save-on, save-off]<br>
     * Return: save</code>
     *
     * @param stringList      List of strings to compare
     * @param caseInsensitive true if loWeR-/UppErCaSE should be ignored
     *
     * @return the starting with
     */
    @Deprecated
    public static String getStartingWith(List<String> stringList, boolean caseInsensitive) {
        String result = "";

        if (!stringList.isEmpty()) {
            if (stringList.size() == 1) {
                result += stringList.get(0);
            } else {
                char[] chars = caseInsensitive ? stringList.get(0).toLowerCase().toCharArray()
                        : stringList.get(0).toCharArray();
                for (int charIndex = 0; charIndex < stringList.size(); charIndex++) {
                    int matchCount = 0;

                    for (int listIndex = 1; listIndex < stringList.size(); listIndex++) {
                        try {
                            if (chars[charIndex] == stringList.get(listIndex).charAt(charIndex) || (caseInsensitive
                                    && chars[charIndex] == stringList.get(listIndex).toLowerCase().charAt(charIndex))) {
                                matchCount++;
                            } else {
                                break;
                            }
                        } catch (@SuppressWarnings("unused") ArrayIndexOutOfBoundsException indexOutOfBounds) {
                            break;
                        }
                    }

                    if (matchCount == (stringList.size() - 1)) {
                        result += chars[charIndex];
                    } else {
                        break;
                    }
                }
            }
        }

        return result;
    }
}