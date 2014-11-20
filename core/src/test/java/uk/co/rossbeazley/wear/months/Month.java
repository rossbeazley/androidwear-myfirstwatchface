package uk.co.rossbeazley.wear.months;

import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.wear.days.DefaultMap;

/**
* Created by beazlr02 on 20/11/2014.
*/
class Month {
    private final String monthString;

    Month(int i) {

        Map<Integer, String> lookup = new HashMap<Integer, String>() {{
            put(1,"January");
            put(3,"March");
        }};

        this.monthString = lookup.get(i);
    }

    @Override
    public String toString() {
        return this.monthString;
    }

    public static Month fromBaseTen(int i) {
        return new Month(i);
    }
}