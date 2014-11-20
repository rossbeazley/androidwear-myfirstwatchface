package uk.co.rossbeazley.wear.days;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.days.CanBeObservedForChangesToDays;
import uk.co.rossbeazley.wear.days.Day;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

/**
* Created by beazlr02 on 20/11/2014.
*/
public class DaysFromTick implements CanBeObservedForChangesToDays, CanBeTicked {

    private final Announcer<CanReceiveDaysUpdates> announcer = Announcer.to(CanReceiveDaysUpdates.class);
    private Day current;

    @Override
    public void observe(CanReceiveDaysUpdates canReceiveSecondsUpdates) {
        announcer.addListener(canReceiveSecondsUpdates);
    }

    @Override
    public void tick(Calendar to) {
        int dayInt = to.get(Calendar.DAY_OF_MONTH);
        Day day = Day.fromBase10(dayInt);
        tick(day);
    }

    private void tick(Day day) {
        current = day.equals(current) ? current : change(day);
    }

    private Day change(Day day) {
        announcer.announce().daysUpdate(day);
        return day;
    }
}