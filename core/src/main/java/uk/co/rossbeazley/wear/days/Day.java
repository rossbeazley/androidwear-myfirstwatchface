package uk.co.rossbeazley.wear.days;

import java.util.HashMap;

import static java.lang.String.*;

/**
* The problem with this class, maybe it should be the month part also
 * There are rules about days in a month....
*/
public class Day {
    private final int value;

    private Day(int i) {
        this.value = i;
    }

    public static Day fromBase10(int i) {
        return new Day(i);
    }

    public String toOrdinalString() {
        return format("%d%s", value, Ordinal.forValue(value));
    }



    private static class Ordinal {

        private final String ordinal;

        private final String ST = "st", ND = "nd", RD = "rd", TH = "th";
        private final String DEFAULT = TH;

        private Ordinal(int value) {
            HashMap<Integer, String> lookupTable = new HashMap<Integer, String>() {{
                put(1, ST);  put(2, ND);  put(3, RD);
                put(21, ST); put(22, ND); put(23, RD);
                put(31, ST);
            }};
            ordinal = lookupTable.containsKey(value)? lookupTable.get(value) : DEFAULT;
        }

        @Override
        public String toString() {
            return ordinal;
        }

        private static Ordinal forValue(int value) {
            return new Ordinal(value);
        }
    }



}
