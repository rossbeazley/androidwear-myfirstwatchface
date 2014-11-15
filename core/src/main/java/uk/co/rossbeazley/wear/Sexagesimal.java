package uk.co.rossbeazley.wear;

import java.text.DecimalFormat;

import uk.co.rossbeazley.wear.seconds.CanBeObservedForChangesToSeconds;

/**
* Created by rdlb on 11/11/14.
*/
public class Sexagesimal {
    private final int value;

    public Sexagesimal(int base10) {
        value = base10;
    }

    public static Sexagesimal fromBase10(int base10) {
        return new Sexagesimal(base10 % 60);
    }

    public String base10String() {
        DecimalFormat numberFormat = new DecimalFormat("00");
        return numberFormat.format(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && ((Sexagesimal) obj).value == value;
    }

    public Sexagesimal increment() {
        return Sexagesimal.fromBase10( value+1 );
    }

    public boolean isLessThan(Sexagesimal other) {
        return value < other.value;
    }
}
