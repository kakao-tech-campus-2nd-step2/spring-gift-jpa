package gift.util;

import java.util.Comparator;

public class AlphanumericComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        if (o1 == null || o2 == null) {
            return o1 == null ? -1 : 1;
        }

        int i = 0, j = 0;
        while (i < o1.length() && j < o2.length()) {
            char c1 = o1.charAt(i);
            char c2 = o2.charAt(j);

            if (Character.isDigit(c1) && Character.isDigit(c2)) {
                int num1 = 0, num2 = 0;
                while (i < o1.length() && Character.isDigit(o1.charAt(i))) {
                    num1 = num1 * 10 + (o1.charAt(i) - '0');
                    i++;
                }
                while (j < o2.length() && Character.isDigit(o2.charAt(j))) {
                    num2 = num2 * 10 + (o2.charAt(j) - '0');
                    j++;
                }
                if (num1 != num2) {
                    return Integer.compare(num1, num2);
                }
            } else {
                if (c1 != c2) {
                    return Character.compare(c1, c2);
                }
                i++;
                j++;
            }
        }

        return Integer.compare(o1.length(), o2.length());
    }
}
