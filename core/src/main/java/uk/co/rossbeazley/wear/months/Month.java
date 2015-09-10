package uk.co.rossbeazley.wear.months;

/**
 * Created by beazlr02 on 20/11/2014.
 */
public class Month {
    private final String monthString;

    public Month(String s) {
        this.monthString = s;
    }

    @Override
    public boolean equals(Object obj) {
        return obj!=null && ((Month)obj).monthString.equals(monthString);
    }

    @Override
    public String toString() {
        return this.monthString;
    }

    public static Month fromBaseTen(int i) {
        return MonthFactory.fromBaseTen(i);
    }
}
