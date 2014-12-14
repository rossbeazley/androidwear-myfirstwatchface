package uk.co.rossbeazley.wear.days;

public interface CanBeObservedForChangesToDays {
    void addListener(CanReceiveDaysUpdates canReceiveSecondsUpdates);
    void removeListener(CanReceiveDaysUpdates canReceiveSecondsUpdates);

}
