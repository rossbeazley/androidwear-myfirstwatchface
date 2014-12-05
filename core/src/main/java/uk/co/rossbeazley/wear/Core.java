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
        Seconds seconds;
        canBeObservedForChangesToSeconds = seconds = new Seconds();
        MinutesFromTick minutes;
        canBeObservedForChangesToMinutes = minutes = new MinutesFromTick();
        HoursFromTick hours;
        canBeObservedForChangesToHours = hours = new HoursFromTick();
        DaysFromTick days;
        canBeObservedForChangesToDays = days = new DaysFromTick();
        MonthsFromTick months;
        canBeObservedForChangesToMonths = months = new MonthsFromTick();

        canBeTicked = Announcer.to(CanBeTicked.class)
                .addListeners(seconds, minutes, hours, days, months)
                .announce();
        Rotation rotation = new Rotation();
        canBeRotated = rotation;
        canBeObservedForChangesToRotation = rotation;
    }

    public static final Core instance = new Core(); //This might be a mistake having this "service locator" in this class

    private class Rotation implements CanBeRotated, CanBeObservedForChangesToRotation {

        Orientation orientation = Orientation.north();
        Announcer<CanBeObservedForChangesToRotation.CanReceiveRotationUpdates> rotationUpdates;

        private Rotation() {
            rotationUpdates = Announcer.to(CanReceiveRotationUpdates.class);
        }

        @Override
        public void observe(CanReceiveRotationUpdates canReceiveRotationUpdates) {
            rotationUpdates.addListener(canReceiveRotationUpdates);
        }

        @Override
        public void right() {
            orientation = orientation.right();
            rotationUpdates.announce().rotationUpdate(orientation);
        }
    }
}
