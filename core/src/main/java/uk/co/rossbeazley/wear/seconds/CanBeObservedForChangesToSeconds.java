package uk.co.rossbeazley.wear.seconds;

import uk.co.rossbeazley.wear.Sexagesimal;

public interface CanBeObservedForChangesToSeconds {
    void observe(CanReceiveSecondsUpdates canReceiveSecondsUpdates);

    void unObserve(CanReceiveSecondsUpdates canReceiveSecondsUpdates);

    interface CanReceiveSecondsUpdates {
        void secondsUpdate(Sexagesimal to);
    }
}
