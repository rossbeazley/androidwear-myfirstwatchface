package uk.co.rossbeazley.wear.days;

public interface CanBeObservedForChangesToDays<T> {
    void addListener(T canReceiveSecondsUpdates);
    void removeListener(T canReceiveSecondsUpdates);

}
