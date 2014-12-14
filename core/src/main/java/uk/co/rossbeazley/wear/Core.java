package uk.co.rossbeazley.wear;

import uk.co.rossbeazley.wear.days.CanReceiveDaysUpdates;
import uk.co.rossbeazley.wear.days.DaysFromTick;
import uk.co.rossbeazley.wear.hours.CanReceiveHoursUpdates;
import uk.co.rossbeazley.wear.hours.HoursFromTick;
import uk.co.rossbeazley.wear.minutes.CanReceiveMinutesUpdates;
import uk.co.rossbeazley.wear.minutes.MinutesFromTick;
import uk.co.rossbeazley.wear.months.CanReceiveMonthsUpdates;
import uk.co.rossbeazley.wear.months.MonthsFromTick;
import uk.co.rossbeazley.wear.rotation.CanBeObservedForChangesToRotation;
import uk.co.rossbeazley.wear.rotation.CanBeRotated;
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
    public final CanBeObservedForChangesToRotation canBeObservedForChangesToRotation;

    public Core() {
        this(Orientation.north());
    }


    public Core(Orientation orientation) {
        Seconds seconds;
        MinutesFromTick minutes;
        HoursFromTick hours;
        DaysFromTick days;
        MonthsFromTick months;

        final Announcer<CanReceiveSecondsUpdates> to = Announcer.to(CanReceiveSecondsUpdates.class);
        seconds = new Seconds(to.announce());
        canBeObservedForChangesToSeconds = to;

        minutes = new MinutesFromTick();
        canBeObservedForChangesToMinutes = minutes;

        canBeObservedForChangesToHours = hours = new HoursFromTick();

        canBeObservedForChangesToDays = days = new DaysFromTick();

        canBeObservedForChangesToMonths = months = new MonthsFromTick();

        canBeTicked = Announcer.to(CanBeTicked.class)
                .addListeners(seconds, minutes, hours, days, months)
                .announce();

        Rotation rotation = new Rotation(orientation);
        canBeRotated = rotation;
        canBeObservedForChangesToRotation = rotation;
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
