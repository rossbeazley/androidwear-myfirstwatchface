package uk.co.rossbeazley.wear.hours;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

public class HoursFromTick implements CanBeTicked {

    final private Announcer<CanReceiveHoursUpdates> announcer;
    private HourBase24 current;

    public HoursFromTick(Announcer<CanReceiveHoursUpdates> canReceiveHoursUpdatesAnnouncer) {
        announcer = canReceiveHoursUpdatesAnnouncer;
        announcer.registerProducer(new Announcer.Producer<CanReceiveHoursUpdates>() {
            @Override
            public void observed(CanReceiveHoursUpdates observer) {
                if(current!=null) observer.hoursUpdate(current);
            }
        });
    }

    @Override
    public void tick(Calendar to) {
        int hourInt = to.get(Calendar.HOUR);
        HourBase24 hourBase24 = HourBase24.fromBase10(hourInt);
        tick(hourBase24);
    }

    private void tick(HourBase24 to) {
        current = to.equals(current) ? current : update(to);
    }

    private HourBase24 update(HourBase24 to) {
        announcer.announce().hoursUpdate(to);
        return to;
    }
}
