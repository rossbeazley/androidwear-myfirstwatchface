package uk.co.rossbeazley.wear.hours;

import java.text.DecimalFormat;
import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

public class HoursFromTick implements CanBeTicked {

    final private Announcer<CanReceiveHoursUpdates> announcer;
    private final HoursBaseConfigItem hoursBaseConfigItem;
    private final CanReceiveHoursUpdates announce;
    private HourBase24 current;

    public HoursFromTick(Announcer<CanReceiveHoursUpdates> canReceiveHoursUpdatesAnnouncer, HoursBaseConfigItem hoursBaseConfigItem) {
        announcer = canReceiveHoursUpdatesAnnouncer;
        this.hoursBaseConfigItem = hoursBaseConfigItem;
        announcer.registerProducer(new Announcer.Producer<CanReceiveHoursUpdates>() {
            @Override
            public void observed(CanReceiveHoursUpdates observer) {
                if(current!=null) observer.hoursUpdate(current);
            }
        });
        announce = announcer.announce();
    }

    @Override
    public void tick(Calendar to) {
        final int hourInt = to.get(Calendar.HOUR_OF_DAY);
        HourBase24 hourBase24;
        hourBase24 = HourBase24.fromBase10(hourInt);
        if(hoursBaseConfigItem!=null && hoursBaseConfigItem.is24Hour()) {
            hourBase24 = new HourBase24(hourInt){

                public String toBase10TwelveHour() {
                    DecimalFormat numberFormat = new DecimalFormat("00");
                    String format = numberFormat.format(hourInt % 24);
                    return format;
                }
            };
        }
        tick(hourBase24);
    }

    private void tick(HourBase24 to) {
        current = to.equals(current) ? current : update(to);
    }

    private HourBase24 update(HourBase24 to) {
        announce.hoursUpdate(to);
        return to;
    }
}
