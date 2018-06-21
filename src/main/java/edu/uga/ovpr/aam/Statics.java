package edu.uga.ovpr.aam;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Glenn Owens
 */
public class Statics {

    public static String fmtBlntoTinyint(boolean input) {
        return input ? "1" : "0";
    }//The reverse of this method is Boolean.valueOf()

    public static String fmtYNtoTinyint(String input) {
        return input.equals("Yes") ? "1" : "0";
    }

    public static String fmtTinyinttoYN(String input) {
        return input.equals("true") ? "Yes" : "No";
    }

    public static <E> ArrayList<E> deNullList(ArrayList<E> input) {
        final ArrayList<E> output = new ArrayList<E>(input.size());
        for (E o : input) {
            if (o != null) {
                output.add(o);
            }
        }
        return output;
    }

    public static void padList(List<?> list, int index) {
        while (index >= list.size()) {
            list.add(null);
        }
    }

    public static String addCurrencyCommas(String input) {
        String output = "";

        if (input.isEmpty()) {
            output = input;
            return output;
        }

        if (input.matches("^[^.]*[.][^.]*[.][^.]*$")) {
            output = input;
            return output;//Input has multiple decimal places
        }

        final boolean hasMinusSign = input.charAt(0) == '-';
        if (hasMinusSign) {
            input = input.substring(1);
        }

        final int indexOfDecimalPoint = input.indexOf(".");
        final boolean hasDecimalPoint = indexOfDecimalPoint > -1;

        String leftOfDecimal;
        if (!hasDecimalPoint) {
            leftOfDecimal = input;
        } else {
            leftOfDecimal = input.substring(0, indexOfDecimalPoint);
        }

        if (leftOfDecimal.length() <= 3) {
            output = input;
            if (hasMinusSign) {
                output = "-" + output;
            }
            return output;//No commas needed
        }

        //Left-pad with hashes to multiple of 3 for easier comma insertion
        while (leftOfDecimal.length() % 3 != 0) {
            leftOfDecimal = '#' + leftOfDecimal;
        }

        for (int i = 0; i < leftOfDecimal.length(); i++) {
            if (i != 0 && i % 3 == 0) {
                output += ",";
            }
            output += leftOfDecimal.charAt(i);
        }

        //Remove the hash padding
        final int lastIndexOfHash = output.lastIndexOf("#");
        if (lastIndexOfHash > -1) {
            output = output.substring(lastIndexOfHash + 1);
        }

        if (!hasDecimalPoint) {
            if (hasMinusSign) {
                output = "-" + output;
            }
            return output;
        }
        output += input.substring(indexOfDecimalPoint);

        if (hasMinusSign) {
            output = "-" + output;
        }
        return output;
    }
}