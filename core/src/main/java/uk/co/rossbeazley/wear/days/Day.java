package uk.co.rossbeazley.wear.days;

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

    @Override
    public boolean equals(Object obj) {
        return obj!=null && ((Day)obj).value == value;
    }

    private static class Ordinal {

        private final String ordinal;

        private static final String ST = "st", ND = "nd", RD = "rd", TH = "th";
        private static final String DEFAULT = TH;
        private static final DefaultMap<Integer, String> lookupTable = new DefaultMap<Integer, String>(DEFAULT) {{
            put(1, ST);  put(2, ND);  put(3, RD);
            put(21, ST); put(22, ND); put(23, RD);
            put(31, ST);
        }};

        private Ordinal(int value) {
            ordinal = lookupTable.get(value);
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
