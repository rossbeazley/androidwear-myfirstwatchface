package uk.co.rossbeazley.wear.hours;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.HourBase24;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

public class HoursFromTick implements CanBeObserved<CanReceiveHoursUpdates>,CanBeTicked {

    final private Announcer<CanReceiveHoursUpdates> announcer;
    private HourBase24 current;

    public HoursFromTick() {
        announcer = Announcer.to(CanReceiveHoursUpdates.class);
        announcer.registerProducer(new Announcer.Producer<CanReceiveHoursUpdates>() {
            @Override
            public void observed(CanReceiveHoursUpdates observer) {
                if(current!=null) observer.hoursUpdate(current);
            }
        });
    }

    @Override
    public void addListener(CanReceiveHoursUpdates canReceiveHoursUpdates) {
        announcer.addListener(canReceiveHoursUpdates);
    }

    @Override
    public void removeListener(CanReceiveHoursUpdates canReceiveHoursUpdates) {

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
