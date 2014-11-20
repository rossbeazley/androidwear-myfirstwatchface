package uk.co.rossbeazley.wear.months;

/**
* Created by beazlr02 on 20/11/2014.
*/
class Month {
    private final String monthString;

    Month(int i) {

        this.monthString = i==1 ? "January":"March";
    }

    @Override
    public String toString() {
        return this.monthString;
    }

    public static Month fromBaseTen(int i) {
        return new Month(i);
    }
}
