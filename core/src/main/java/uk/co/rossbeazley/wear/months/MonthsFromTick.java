package uk.co.rossbeazley.wear.months;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

/**
* Created by beazlr02 on 20/11/2014.
*/
public class MonthsFromTick implements CanBeObservedForChangesToMonths<CanReceiveMonthsUpdates>, CanBeTicked {

    final private Announcer<CanReceiveMonthsUpdates> announcer;
    private Month current;

    public MonthsFromTick() {
        announcer = Announcer.to(CanReceiveMonthsUpdates.class);
        announcer.registerProducer(new Announcer.Producer<CanReceiveMonthsUpdates>() {
            @Override
            public void observed(CanReceiveMonthsUpdates observer) {
                if(current!=null) observer.monthsUpdate(current);
            }
        });
    }

    @Override
    public void addListener(CanReceiveMonthsUpdates canReceiveMonthsUpdates) {
        announcer.addListener(canReceiveMonthsUpdates);
    }

    @Override
    public void removeListener(CanReceiveMonthsUpdates canReceiveMonthsUpdates) {
    }

    @Override
    public void tick(Calendar to) {
        int monthInt = to.get(Calendar.MONTH) + 1;
        Month month = Month.fromBaseTen(monthInt);
        tick(month);
    }

    private void tick(Month to) {
        current = to.equals(current) ? current : update(to);
    }

    private Month update(Month to) {
        announcer.announce().monthsUpdate(to);
        return to;
    }
}
