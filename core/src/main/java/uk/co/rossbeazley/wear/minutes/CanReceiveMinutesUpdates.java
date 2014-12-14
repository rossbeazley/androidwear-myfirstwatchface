package uk.co.rossbeazley.wear.minutes;

import uk.co.rossbeazley.wear.Sexagesimal;

public interface CanReceiveMinutesUpdates {
    void minutesUpdate(Sexagesimal to);
}
