package uk.co.rossbeazley.wear.seconds;

import uk.co.rossbeazley.wear.Sexagesimal;

public interface CanBeObservedForChangesToSeconds {
    void addListener(CanReceiveSecondsUpdates canReceiveSecondsUpdates);

    void removeListener(CanReceiveSecondsUpdates canReceiveSecondsUpdates);

    interface CanReceiveSecondsUpdates {
        void secondsUpdate(Sexagesimal to);
    }
}
