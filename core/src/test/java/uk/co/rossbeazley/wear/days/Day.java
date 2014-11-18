package uk.co.rossbeazley.wear.days;

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
        String ordinal;
        switch (value) {
            case 3:
                ordinal = "rd";
                break;
            case 2:
                ordinal = "nd";
                break;
            default:
                ordinal = "st";
                break;
        }
        return ordinal;
    }

}
