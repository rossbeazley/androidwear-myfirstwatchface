package uk.co.rossbeazley.wear;

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
        String format = numberFormat.format(value % 12);
        return format.equals("00") ? "12" : format;
    }

    @Override
    public boolean equals(Object obj) {
        return obj !=null && ((HourBase24)obj).value == value;
    }
}
