package uk.co.rossbeazley.wear.minutes;

import uk.co.rossbeazley.wear.CanBeObserved;

public interface CanBeObservedForChangesToMinutes extends CanBeObserved<CanReceiveMinutesUpdates> {
    void addListener(CanReceiveMinutesUpdates canReceiveMinutesUpdates);

    void removeListener(CanReceiveMinutesUpdates canReceiveSecondsUpdates);

}
