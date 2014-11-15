package uk.co.rossbeazley.wear.minutes;

import uk.co.rossbeazley.wear.Sexagesimal;

/**
* Created by rdlb on 15/11/14.
*/
public interface CanBeObservedForChangesToMinutes {
    void observe(CanReceiveMinutesUpdates canReceiveMinutesUpdates);

    interface CanReceiveMinutesUpdates {
        void minutesUpdate(Sexagesimal to);
    }
}
