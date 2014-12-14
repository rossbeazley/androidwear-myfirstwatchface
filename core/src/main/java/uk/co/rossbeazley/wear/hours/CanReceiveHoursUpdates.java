package uk.co.rossbeazley.wear.hours;

import uk.co.rossbeazley.wear.HourBase24;

public interface CanReceiveHoursUpdates {
    void hoursUpdate(HourBase24 hourBase24);
}
