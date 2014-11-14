package uk.co.rossbeazley.wear.seconds;

import uk.co.rossbeazley.wear.Sexagesimal;

interface CanBeObservedForChangesToSeconds {
    void observe(CanReceiveSecondsUpdates canReceiveSecondsUpdates);

    interface CanReceiveSecondsUpdates {
        void secondsUpdate(Sexagesimal to);
    }
}
