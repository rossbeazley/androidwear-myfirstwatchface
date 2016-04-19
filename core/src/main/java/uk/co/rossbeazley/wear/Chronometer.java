package uk.co.rossbeazley.wear;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;
import uk.co.rossbeazley.wear.days.CanReceiveDaysUpdates;
import uk.co.rossbeazley.wear.days.DaysFromTick;
import uk.co.rossbeazley.wear.hours.CanReceiveHoursUpdates;
import uk.co.rossbeazley.wear.hours.HoursFromTick;
import uk.co.rossbeazley.wear.hours.HoursModeConfigItem;
import uk.co.rossbeazley.wear.minutes.CanReceiveMinutesUpdates;
import uk.co.rossbeazley.wear.minutes.MinutesFromTick;
import uk.co.rossbeazley.wear.months.CanReceiveMonthsUpdates;
import uk.co.rossbeazley.wear.months.MonthsFromTick;
import uk.co.rossbeazley.wear.seconds.CanReceiveSecondsUpdates;
import uk.co.rossbeazley.wear.seconds.Seconds;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

public class Chronometer {

    public CanBeObserved<CanReceiveSecondsUpdates> canBeObservedForChangesToSeconds;
    public CanBeObserved<CanReceiveMinutesUpdates> canBeObservedForChangesToMinutes;
    public CanBeObserved<CanReceiveDaysUpdates> canBeObservedForChangesToDays;
    public CanBeObserved<CanReceiveMonthsUpdates> canBeObservedForChangesToMonths;
    public CanBeObserved<CanReceiveHoursUpdates> canBeObservedForChangesToHours;
    public CanConfigureHours canConfigureHours;
    public CanBeTicked canBeTicked;

    public Chronometer(HoursModeConfigItem hoursModeConfigItem, ConfigService configService) {


        final Announcer<CanReceiveSecondsUpdates> canReceiveSecondsUpdatesAnnouncer = Announcer.to(CanReceiveSecondsUpdates.class);
        Seconds seconds = new Seconds(canReceiveSecondsUpdatesAnnouncer);
        canBeObservedForChangesToSeconds = canReceiveSecondsUpdatesAnnouncer;

        Announcer<CanReceiveMinutesUpdates> canReceiveMinutesUpdatesAnnouncer = Announcer.to(CanReceiveMinutesUpdates.class);
        MinutesFromTick minutes = new MinutesFromTick(canReceiveMinutesUpdatesAnnouncer);
        canBeObservedForChangesToMinutes = canReceiveMinutesUpdatesAnnouncer;

        Announcer<CanReceiveDaysUpdates> canReceiveDaysUpdatesAnnouncer = Announcer.to(CanReceiveDaysUpdates.class);
        DaysFromTick days = new DaysFromTick(canReceiveDaysUpdatesAnnouncer);
        canBeObservedForChangesToDays = canReceiveDaysUpdatesAnnouncer;

        Announcer<CanReceiveMonthsUpdates> canReceiveMonthsUpdatesAnnouncer = Announcer.to(CanReceiveMonthsUpdates.class);
        MonthsFromTick months = new MonthsFromTick(canReceiveMonthsUpdatesAnnouncer);
        canBeObservedForChangesToMonths = canReceiveMonthsUpdatesAnnouncer;

        Announcer<CanReceiveHoursUpdates> canReceiveHoursUpdatesAnnouncer = Announcer.to(CanReceiveHoursUpdates.class);
        HoursFromTick hours = new HoursFromTick(canReceiveHoursUpdatesAnnouncer, hoursModeConfigItem, configService);
        canBeObservedForChangesToHours = canReceiveHoursUpdatesAnnouncer;
        canConfigureHours = hours;

        canBeTicked = Announcer.to(CanBeTicked.class)
                .addListeners(seconds, minutes, days, months, hours)
                .announce();
    }
}
