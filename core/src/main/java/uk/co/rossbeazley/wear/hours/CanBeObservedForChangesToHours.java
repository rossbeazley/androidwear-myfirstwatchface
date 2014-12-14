package uk.co.rossbeazley.wear.hours;

public interface CanBeObservedForChangesToHours {
    public void observe(CanReceiveHoursUpdates canReceiveHoursUpdates);

}
