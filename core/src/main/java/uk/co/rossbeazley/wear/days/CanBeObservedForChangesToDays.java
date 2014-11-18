package uk.co.rossbeazley.wear.days;

public interface CanBeObservedForChangesToDays {
    void observe(CanReceiveDaysUpdates canReceiveSecondsUpdates);

    interface CanReceiveDaysUpdates {
        void daysUpdate(Day to);
    }
}
