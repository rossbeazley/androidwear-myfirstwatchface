package uk.co.rossbeazley.wear.days;

import java.util.HashMap;

/**
* Created by beazlr02 on 18/11/2014.
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
        return value + ordinalForValue(value);
    }

    private String ordinalForValue(int value) {
        HashMap<Integer, String> lookupTable;
        lookupTable = new HashMap<Integer, String>() {{
            put(1,"st");
            put(2,"nd");
            put(3,"rd");
        }};

        String DEFAULT = "th";
        return lookupTable.containsKey(value)? lookupTable.get(value) : DEFAULT;
    }

}
