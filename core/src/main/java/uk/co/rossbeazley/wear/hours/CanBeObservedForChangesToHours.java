package uk.co.rossbeazley.wear.hours;

import uk.co.rossbeazley.wear.HourBase24;

interface CanBeObservedForChangesToHours {
    public void observe(CanReceiveHoursUpdates canReceiveHoursUpdates);

    interface CanReceiveHoursUpdates {
        void hoursUpdate(HourBase24 hourBase24);
    }
}
