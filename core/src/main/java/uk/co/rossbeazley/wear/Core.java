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
import uk.co.rossbeazley.wear.rotation.Rotation;
import uk.co.rossbeazley.wear.seconds.CanBeObservedForChangesToSeconds;
import uk.co.rossbeazley.wear.seconds.Seconds;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

public class Core {

    public final CanBeObservedForChangesToHours canBeObservedForChangesToHours;
    public final CanBeObservedForChangesToMinutes canBeObservedForChangesToMinutes;
    public final CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds;
    public final CanBeObservedForChangesToDays canBeObservedForChangesToDays;

    public final CanBeTicked canBeTicked;

    private final Seconds seconds;
    private final MinutesFromTick minutes;
    private final HoursFromTick hours;
    private final DaysFromTick days;
    public final CanBeObservedForChangesToMonths canBeObservedForChangesToMonths;
    private final MonthsFromTick months;

    CanBeObservedForChangesToRotation.CanReceiveRotationUpdates rotationUpdates;

    public CanBeObservedForChangesToRotation canBeObservedForChangesToRotation = new CanBeObservedForChangesToRotation() {
        @Override
        public void observe(CanReceiveRotationUpdates canReceiveRotationUpdates) {
            rotationUpdates = canReceiveRotationUpdates;
        }
    };
    public CanBeRotated canBeRotated = new CanBeRotated() {

        Rotation rotation = Rotation.north();

        @Override
        public void right() {
            rotation = rotation.right();
            rotationUpdates.rotationUpdate(rotation);
        }
    };

    public Core() {
        canBeObservedForChangesToSeconds = seconds = new Seconds();
        canBeObservedForChangesToMinutes = minutes = new MinutesFromTick();
        canBeObservedForChangesToHours = hours = new HoursFromTick();
        canBeObservedForChangesToDays = days = new DaysFromTick();
        canBeObservedForChangesToMonths = months = new MonthsFromTick();

        canBeTicked = Announcer.to(CanBeTicked.class)
                .addListeners(seconds, minutes, hours, days, months)
                .announce();
    }

    public static final Core instance = new Core();
}
