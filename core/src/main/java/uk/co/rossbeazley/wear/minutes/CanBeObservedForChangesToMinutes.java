package uk.co.rossbeazley.wear.minutes;

import uk.co.rossbeazley.wear.CanBeObserved;

public interface CanBeObservedForChangesToMinutes<T> extends CanBeObserved<T> {
    void addListener(T canReceiveMinutesUpdates);

    void removeListener(T canReceiveSecondsUpdates);

}
