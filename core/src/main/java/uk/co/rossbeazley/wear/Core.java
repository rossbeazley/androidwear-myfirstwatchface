package uk.co.rossbeazley.wear;

import uk.co.rossbeazley.wear.days.CanBeObservedForChangesToDays;
import uk.co.rossbeazley.wear.days.DaysFromTick;
import uk.co.rossbeazley.wear.hours.CanBeObservedForChangesToHours;
import uk.co.rossbeazley.wear.hours.HoursFromTick;
import uk.co.rossbeazley.wear.minutes.CanBeObservedForChangesToMinutes;
import uk.co.rossbeazley.wear.minutes.MinutesFromTick;
import uk.co.rossbeazley.wear.months.CanBeObservedForChangesToMonths;
import uk.co.rossbeazley.wear.months.MonthsFromTick;
import uk.co.rossbeazley.wear.rotation.CanBeObservedForChangesToRotation;
import uk.co.rossbeazley.wear.rotation.CanBeRotated;
import uk.co.rossbeazley.wear.rotation.Orientation;
import uk.co.rossbeazley.wear.rotation.Rotation;
import uk.co.rossbeazley.wear.seconds.CanBeObservedForChangesToSeconds;
import uk.co.rossbeazley.wear.seconds.Seconds;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

public class Core {

    public final CanBeObservedForChangesToMonths canBeObservedForChangesToMonths;
    public final CanBeObservedForChangesToDays canBeObservedForChangesToDays;
    public final CanBeObservedForChangesToHours canBeObservedForChangesToHours;
    public final CanBeObservedForChangesToMinutes canBeObservedForChangesToMinutes;
    public final CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds;

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

        canBeObservedForChangesToSeconds = seconds = new Seconds();
        canBeObservedForChangesToMinutes = minutes = new MinutesFromTick();
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
