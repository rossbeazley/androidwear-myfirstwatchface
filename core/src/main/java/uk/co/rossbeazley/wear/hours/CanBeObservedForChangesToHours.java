package uk.co.rossbeazley.wear.hours;

public interface CanBeObservedForChangesToHours {
    public void addListener(CanReceiveHoursUpdates canReceiveHoursUpdates);
    public void removeListener(CanReceiveHoursUpdates canReceiveHoursUpdates);
}
