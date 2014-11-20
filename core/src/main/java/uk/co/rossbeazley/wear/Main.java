package uk.co.rossbeazley.wear;

import java.util.Calendar;

import uk.co.rossbeazley.wear.days.CanBeObservedForChangesToDays;
import uk.co.rossbeazley.wear.days.Day;
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

    public static Main instance() { return instance; }

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
                             .addListeners(seconds,minutes,hours,days,months)
                             .announce();
        }

        private static class MonthsFromTick implements CanBeObservedForChangesToMonths, CanBeTicked {

            final private Announcer<CanReceiveMonthsUpdates> announcer;

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
                announcer.announce().daysUpdate(Month.fromBaseTen(monthInt));
            }
        }


        private static class DaysFromTick implements CanBeObservedForChangesToDays, CanBeTicked {

            private final Announcer<CanReceiveDaysUpdates> announcer = Announcer.to(CanReceiveDaysUpdates.class);
            private Day current;

            @Override
            public void observe(CanReceiveDaysUpdates canReceiveSecondsUpdates) {
                announcer.addListener(canReceiveSecondsUpdates);
            }

            @Override
            public void tick(Calendar to) {
                int dayInt = to.get(Calendar.DAY_OF_MONTH);
                Day day = Day.fromBase10(dayInt);
                tick(day);
            }

            private void tick(Day day) {
                current = day.equals(current) ? current : change(day);
            }

            private Day change(Day day) {
                announcer.announce().daysUpdate(day);
                return day;
            }
        }
    }

}
