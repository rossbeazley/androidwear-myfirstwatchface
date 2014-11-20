package uk.co.rossbeazley.wear.months;

import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.wear.days.DefaultMap;

/**
 * Created by beazlr02 on 20/11/2014.
 */
public class Month {
    private final String monthString;

    Month(int i) {

        String[] lookup = new String[]{
                "None-ary",
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"};

        this.monthString = lookup[i];
    }

    @Override
    public String toString() {
        return this.monthString;
    }

    public static Month fromBaseTen(int i) {
        return new Month(i);
    }
}
