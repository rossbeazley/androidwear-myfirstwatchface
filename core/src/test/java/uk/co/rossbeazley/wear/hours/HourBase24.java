package uk.co.rossbeazley.wear.hours;

import java.text.DecimalFormat;

public class HourBase24 {
    private final int value;

    public HourBase24(int base10) {
        value = base10 % 24;
    }

    public static HourBase24 fromBase10(int base10) {
        return new HourBase24(base10);
    }

    public String toBase10TwelveHour() {
        DecimalFormat numberFormat = new DecimalFormat("00");
        return numberFormat.format(value%12);
    }
}
