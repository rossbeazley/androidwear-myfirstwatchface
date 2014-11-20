package uk.co.rossbeazley.wear;

import java.util.Calendar;

import uk.co.rossbeazley.wear.days.CanBeObservedForChangesToDays;
import uk.co.rossbeazley.wear.days.DaysFromTick;
import uk.co.rossbeazley.wear.hours.CanBeObservedForChangesToHours;
import uk.co.rossbeazley.wear.hours.HoursFromTick;
import uk.co.rossbeazley.wear.minutes.CanBeObservedForChangesToMinutes;
import uk.co.rossbeazley.wear.minutes.MinutesFromTick;
import uk.co.rossbeazley.wear.months.CanBeObservedForChangesToMonths;
import uk.co.rossbeazley.wear.months.Month;
import uk.co.rossbeazley.wear.seconds.CanBeObservedForChangesToSeconds;
import uk.co.rossbeazley.wear.seconds.Seconds;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;
import uk.co.rossbeazley.wear.ticktock.TickTock;

public class Main {

    private static Main instance;
    public final Core core;

    public static Main instance() {
        return instance;
    }

    public static void init() { //thinking i will init the core with the external stuff?
        instance = new Main();
    }

    public Main() {

        this.core = new Core();

        TickTock.createTickTock(this.core.canBeTicked);

        /** probably also initialise the ui navigation framework here */
    }

    public static class Core {

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


        public Core() {
            canBeObservedForChangesToSeconds = seconds = new Seconds();
            canBeObservedForChangesToMinutes = minutes = new MinutesFromTick();
            canBeObservedForChangesToHours = hours = new HoursFromTick();
            canBeObservedForChangesToDays = days = new DaysFromTick();
            canBeObservedForChangesToMonths = months = new MonthsFromTick();
            // haha, an eventbus - kinda
            canBeTicked = Announcer.to(CanBeTicked.class)
                    .addListeners(seconds, minutes, hours, days, months)
                    .announce();
        }

        private static class MonthsFromTick implements CanBeObservedForChangesToMonths, CanBeTicked {

            final private Announcer<CanReceiveMonthsUpdates> announcer;
            private Month current;

            private MonthsFromTick() {
                announcer = Announcer.to(CanReceiveMonthsUpdates.class);
            }

            @Override
            public void observe(CanReceiveMonthsUpdates canReceiveMonthsUpdates) {
                announcer.addListener(canReceiveMonthsUpdates);
            }

            @Override
            public void tick(Calendar to) {
                int monthInt = to.get(Calendar.MONTH);
                Month month = Month.fromBaseTen(monthInt);
                tick(month);
            }

            private void tick(Month to) {
                current = to.equals(current) ? current : update(to);
            }

            private Month update(Month to) {
                announcer.announce().daysUpdate(to);
                return to;
            }
        }


    }

}
