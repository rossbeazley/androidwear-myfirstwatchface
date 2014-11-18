package uk.co.rossbeazley.wear.days;

import java.util.HashMap;

import static java.lang.String.*;

/**
* The problem with this class, maybe it should be the month part also
 * There are rules about days in a month....
*/
class Day {
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

        final String ST = "st", ND = "nd", RD = "rd", TH = "th";
        final String DEFAULT = TH;

        private final int value;
        private HashMap<Integer, String> lookupTable;

        private Ordinal(int value) {
            this.value = value;
            lookupTable = new HashMap<Integer, String>() {{
                put(1, ST);
                put(2, ND);
                put(3, RD);
                put(21, ST);
                put(22, ND);
                put(23, RD);
                put(31, ST);
            }};
        }

        @Override
        public String toString() {
            return lookupTable.containsKey(value)? lookupTable.get(value) : DEFAULT;
        }

        private static Ordinal forValue(int value) {
            return new Ordinal(value);
        }
    }



}
