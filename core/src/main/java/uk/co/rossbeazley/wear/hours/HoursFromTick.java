package uk.co.rossbeazley.wear.hours;

import java.text.DecimalFormat;
import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.CanConfigureHours;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static uk.co.rossbeazley.wear.hours.HoursBaseConfigItem.HR_12;
import static uk.co.rossbeazley.wear.hours.HoursBaseConfigItem.HR_24;

public class HoursFromTick implements CanBeTicked, CanConfigureHours {

    final private Announcer<CanReceiveHoursUpdates> announcer;
    private final HoursBaseConfigItem hoursBaseConfigItem;
    private final ConfigService configService;
    private final CanReceiveHoursUpdates announce;
    private HourBase24 current;
    private Object hr;

    public HoursFromTick(Announcer<CanReceiveHoursUpdates> canReceiveHoursUpdatesAnnouncer, HoursBaseConfigItem hoursBaseConfigItem, ConfigService configService) {
        announcer = canReceiveHoursUpdatesAnnouncer;
        this.hoursBaseConfigItem = hoursBaseConfigItem;
        this.configService = configService;
        rehydrateHoursSettings(hoursBaseConfigItem, configService);
        announcer.registerProducer(new Announcer.Producer<CanReceiveHoursUpdates>() {
            @Override
            public void observed(CanReceiveHoursUpdates observer) {
                if(current!=null) observer.hoursUpdate(current);
            }
        });
        announce = announcer.announce();
    }

    private void rehydrateHoursSettings(HoursBaseConfigItem hoursBaseConfigItem, ConfigService configService) {
        hr = hoursBaseConfigItem==null?null:hoursBaseConfigItem.defaultHR();

        final String currentOptionForItem = configService.currentOptionForItem(hoursBaseConfigItem.itemId());

        hr = hoursBaseConfigItem.hoursModeFromOption(currentOptionForItem);

    }

    @Override
    public void tick(Calendar to) {
        final int hourInt = to.get(Calendar.HOUR_OF_DAY);
        HourBase24 hourBase24;
        hourBase24 = HourBase24.fromBase10(hourInt);
        if(is24Hour()) {
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

    private boolean is24Hour() {
        return hr!=null && hr== HR_24;
    }

    private void tick(HourBase24 to) {
        current = to.equals(current) ? current : update(to);
    }

    private HourBase24 update(HourBase24 to) {
        announce.hoursUpdate(to);
        return to;
    }

    @Override
    public void twentyFourHour() {
        hr = HR_24;
        configService.persistItemChoice(hoursBaseConfigItem.itemId(),"24 Hours");
    }

    @Override
    public void twelveHour() {
        hr = HR_12;
    }
}
