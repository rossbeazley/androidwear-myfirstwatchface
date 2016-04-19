package uk.co.rossbeazley.wear.hours;

import java.text.DecimalFormat;
import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.CanConfigureHours;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigServiceListener;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static uk.co.rossbeazley.wear.hours.HoursModeConfigItem.HR_12;
import static uk.co.rossbeazley.wear.hours.HoursModeConfigItem.HR_24;

public class HoursFromTick implements CanBeTicked, CanConfigureHours {

    final private Announcer<CanReceiveHoursUpdates> announcer;
    private final HoursModeConfigItem hoursModeConfigItem;
    private final ConfigService configService;
    private final CanReceiveHoursUpdates announce;
    private HourBase24 current;
    private Object hr;
    private int hourInt;

    public HoursFromTick(Announcer<CanReceiveHoursUpdates> canReceiveHoursUpdatesAnnouncer, final HoursModeConfigItem hoursModeConfigItem, ConfigService configService) {
        announcer = canReceiveHoursUpdatesAnnouncer;
        this.hoursModeConfigItem = hoursModeConfigItem;
        this.configService = configService;
        rehydrateHoursSettings(hoursModeConfigItem, configService);
        announcer.registerProducer(new Announcer.Producer<CanReceiveHoursUpdates>() {
            @Override
            public void observed(CanReceiveHoursUpdates observer) {
                if(current!=null) observer.hoursUpdate(current);
            }
        });
        announce = announcer.announce();
        configService.addListener(new ConfigServiceListener() {
            private String item;

            @Override
            public void configuring(String item) {

                this.item = item;
            }

            @Override
            public void error(KeyNotFound keyNotFound) {

            }

            @Override
            public void chosenOption(String option) {
                if(item.equals(hoursModeConfigItem.itemId())) {
                    final Object modeFromOption = hoursModeConfigItem.hoursModeFromOption(option);
                    hr = modeFromOption;
                }
            }
        });
    }

    private void rehydrateHoursSettings(HoursModeConfigItem hoursModeConfigItem, ConfigService configService) {
        hr = hoursModeConfigItem ==null?null: hoursModeConfigItem.defaultHR();

        final String currentOptionForItem = configService.currentOptionForItem(hoursModeConfigItem.itemId());

        hr = hoursModeConfigItem.hoursModeFromOption(currentOptionForItem);

    }

    @Override
    public void tick(Calendar to) {
        hourInt = to.get(Calendar.HOUR_OF_DAY);
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
        return hr!=null && hr==HR_24;
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
        configService.persistItemChoice(hoursModeConfigItem.itemId(),hr.toString());
        current = new HourBase24(hourInt){
            public String toBase10TwelveHour() {
                DecimalFormat numberFormat = new DecimalFormat("00");
                String format = numberFormat.format(hourInt % 24);
                return format;
            }
        };
        announce.hoursUpdate(current);
    }

    @Override
    public void twelveHour() {
        hr = HR_12;
        configService.persistItemChoice(hoursModeConfigItem.itemId(),hr.toString());
    }
}
