package uk.co.rossbeazley.wear.minutes;

/**
* Created by rdlb on 15/11/14.
*/
public interface CanBeObservedForChangesToMinutes {
    void observe(CanReceiveMinutesUpdates canReceiveMinutesUpdates);

    void unObserve(CanReceiveMinutesUpdates canReceiveSecondsUpdates);

}
