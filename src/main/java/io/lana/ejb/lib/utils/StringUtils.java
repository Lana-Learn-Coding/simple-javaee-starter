package io.lana.ejb.lib.utils;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean startsWith(final CharSequence str, final CharSequence prefix) {
        if (str == null || prefix == null) {
            return str == prefix;
        }
        // Get length once instead of twice in the unlikely case that it changes.
        final int preLen = prefix.length();
        if (preLen > str.length()) {
            return false;
        }

        return regionMatches(str, prefix, preLen);
    }

    public static boolean startsWithAny(final CharSequence sequence, final CharSequence... searchStrings) {
        if (isEmpty(sequence) || searchStrings == null || searchStrings.length == 0) {
            return false;
        }
        for (final CharSequence searchString : searchStrings) {
            if (startsWith(sequence, searchString)) {
                return true;
            }
        }
        return false;
    }

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    private static boolean regionMatches(final CharSequence cs, final CharSequence substring, final int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(0, (String) substring, 0, length);
        }
        int index1 = 0;
        int index2 = 0;
        int tmpLen = length;

        // Extract these first so we detect NPEs the same as the java.lang.String version
        final int srcLen = cs.length();
        final int otherLen = substring.length();

        // Check for invalid parameters
        if (length < 0) {
            return false;
        }

        // Check that the regions are long enough
        if (srcLen < length || otherLen < length) {
            return false;
        }

        while (tmpLen-- > 0) {
            final char c1 = cs.charAt(index1++);
            final char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            return false;
        }
        return true;
    }
}
