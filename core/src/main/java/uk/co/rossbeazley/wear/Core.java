package uk.co.rossbeazley.wear;

import uk.co.rossbeazley.wear.days.CanReceiveDaysUpdates;
import uk.co.rossbeazley.wear.days.DaysFromTick;
import uk.co.rossbeazley.wear.hours.CanReceiveHoursUpdates;
import uk.co.rossbeazley.wear.hours.HoursFromTick;
import uk.co.rossbeazley.wear.minutes.CanReceiveMinutesUpdates;
import uk.co.rossbeazley.wear.minutes.MinutesFromTick;
import uk.co.rossbeazley.wear.months.CanReceiveMonthsUpdates;
import uk.co.rossbeazley.wear.months.MonthsFromTick;
import uk.co.rossbeazley.wear.rotation.CanBeRotated;
import uk.co.rossbeazley.wear.rotation.CanReceiveRotationUpdates;
import uk.co.rossbeazley.wear.rotation.Orientation;
import uk.co.rossbeazley.wear.rotation.Rotation;
import uk.co.rossbeazley.wear.seconds.CanReceiveSecondsUpdates;
import uk.co.rossbeazley.wear.seconds.Seconds;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

public class Core {

    public final CanBeObserved<CanReceiveMonthsUpdates> canBeObservedForChangesToMonths;
    public final CanBeObserved<CanReceiveDaysUpdates> canBeObservedForChangesToDays;
    public final CanBeObserved<CanReceiveHoursUpdates> canBeObservedForChangesToHours;
    public final CanBeObserved<CanReceiveMinutesUpdates> canBeObservedForChangesToMinutes;
    public final CanBeObserved<CanReceiveSecondsUpdates> canBeObservedForChangesToSeconds;

    public final CanBeTicked canBeTicked;

    public final CanBeRotated canBeRotated;
    public final CanBeObserved<CanReceiveRotationUpdates> canBeObservedForChangesToRotation;

    public Core() {
        this(Orientation.north());
    }


    public Core(Orientation orientation) {
        System.out.println("CORE INIT");
        Seconds seconds;
        MinutesFromTick minutes;
        HoursFromTick hours;
        DaysFromTick days;
        MonthsFromTick months;

        final Announcer<CanReceiveSecondsUpdates> canReceiveSecondsUpdatesAnnouncer = Announcer.to(CanReceiveSecondsUpdates.class);
        seconds = new Seconds(canReceiveSecondsUpdatesAnnouncer.announce());
        canBeObservedForChangesToSeconds = canReceiveSecondsUpdatesAnnouncer;

        Announcer<CanReceiveMinutesUpdates> canReceiveMinutesUpdatesAnnouncer = Announcer.to(CanReceiveMinutesUpdates.class);
        minutes = new MinutesFromTick(canReceiveMinutesUpdatesAnnouncer);
        canBeObservedForChangesToMinutes = canReceiveMinutesUpdatesAnnouncer;

        Announcer<CanReceiveHoursUpdates> canReceiveHoursUpdatesAnnouncer = Announcer.to(CanReceiveHoursUpdates.class);
        hours = new HoursFromTick(canReceiveHoursUpdatesAnnouncer);
        canBeObservedForChangesToHours = canReceiveHoursUpdatesAnnouncer;

        Announcer<CanReceiveDaysUpdates> canReceiveDaysUpdatesAnnouncer = Announcer.to(CanReceiveDaysUpdates.class);
        days = new DaysFromTick(canReceiveDaysUpdatesAnnouncer);
        canBeObservedForChangesToDays = canReceiveDaysUpdatesAnnouncer;

        Announcer<CanReceiveMonthsUpdates> canReceiveMonthsUpdatesAnnouncer = Announcer.to(CanReceiveMonthsUpdates.class);
        months = new MonthsFromTick(canReceiveMonthsUpdatesAnnouncer);
        canBeObservedForChangesToMonths = canReceiveMonthsUpdatesAnnouncer;

        canBeTicked = Announcer.to(CanBeTicked.class)
                .addListeners(seconds, minutes, hours, days, months)
                .announce();

        Announcer<CanReceiveRotationUpdates> canReceiveRotationUpdatesAnnouncer = Announcer.to(CanReceiveRotationUpdates.class);
        Rotation rotation = new Rotation(orientation, canReceiveRotationUpdatesAnnouncer);
        canBeRotated = rotation;
        canBeObservedForChangesToRotation = canReceiveRotationUpdatesAnnouncer;
    }

    private static Core instance;

    public static Core instance() {
        return instance;
    }

    public static Core init() {
        return init(Orientation.north());
    }

    public static Core init(Orientation orientation) {
        return (instance  = new Core(orientation));
    }
}
