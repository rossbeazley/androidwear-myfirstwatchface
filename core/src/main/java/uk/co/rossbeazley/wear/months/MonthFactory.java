package uk.co.rossbeazley.wear.months;

/**
 * Created by beazlr02 on 10/09/15.
 */
public class MonthFactory {
    private static String[] monthStrings;

    static Month fromBaseTen(int i) {
        monthStrings = new String[]{
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
        return new Month(monthStrings[i]);
    }
}
