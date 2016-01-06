package uk.co.rossbeazley.wear.days;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

/**
* Created by beazlr02 on 20/11/2014.
*/
public class DaysFromTick implements CanBeTicked {

    private final Announcer<CanReceiveDaysUpdates> announcer;
    private final CanReceiveDaysUpdates announce;
    private Day current;

    public DaysFromTick(Announcer<CanReceiveDaysUpdates> canReceiveDaysUpdatesAnnouncer) {
        announcer = canReceiveDaysUpdatesAnnouncer;
        announcer.registerProducer(new Announcer.Producer<CanReceiveDaysUpdates>() {
            @Override
            public void observed(CanReceiveDaysUpdates observer) {
                if(current!=null) observer.daysUpdate(current);
            }
        });
        announce = announcer.announce();
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
        announce.daysUpdate(day);
        return day;
    }
}
