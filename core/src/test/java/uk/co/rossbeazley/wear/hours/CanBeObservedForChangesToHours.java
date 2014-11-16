package uk.co.rossbeazley.wear.hours;

interface CanBeObservedForChangesToHours {
    public void observe(CanReceiveHoursUpdates canReceiveHoursUpdates);

    interface CanReceiveHoursUpdates {
        void hoursUpdate(HourBase24 hourBase24);
    }
}
