package uk.co.rossbeazley.wear.seconds;

import java.util.Calendar;

/**
* Created by rdlb on 11/11/14.
*/
public class Seconds {

    public Seconds(Calendar aTimeWithZeroSeconds) {

    }

    public void tick(Calendar instance) {

    }

    public void observe(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {

    }

    public static interface CanReceiveSecondsUpdates {
        void secondsUpdate(SecondsChange.Sexagesimal to);
    }
}
