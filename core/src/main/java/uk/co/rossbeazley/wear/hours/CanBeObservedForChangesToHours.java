package uk.co.rossbeazley.wear.hours;

public interface CanBeObservedForChangesToHours<T> {
    public void addListener(T canReceiveHoursUpdates);
    public void removeListener(T canReceiveHoursUpdates);
}
