package uk.co.rossbeazley.console.ui;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.days.DaysPresenter;
import uk.co.rossbeazley.wear.hours.HoursPresenter;
import uk.co.rossbeazley.wear.minutes.MinutesPresenter;
import uk.co.rossbeazley.wear.months.MonthsPresenter;
import uk.co.rossbeazley.wear.seconds.SecondsPresenter;
import uk.co.rossbeazley.wear.ticktock.TickTock;

public class Clock {

    public Clock() {
        Core core = new Core();
        TickTock.createTickTock(core.canBeTicked);
        class SysOutPrinter implements Printer {
            @Override
            public void print(String out) {
                System.out.println(out);
            }
        }
        Printer printer = new SysOutPrinter();
        ConsoleView view = new ConsoleView(printer);
        new DaysPresenter(core.canBeObservedForChangesToDays, view);
        new MonthsPresenter(core.canBeObservedForChangesToMonths, view);
        new HoursPresenter(core.canBeObservedForChangesToHours, view);
        new MinutesPresenter(core.canBeObservedForChangesToMinutes, view);
        new SecondsPresenter(core.canBeObserved, view);
    }

    interface Printer {
        void print(String out);
    }


    public static void main(String... vargs) {
        new Clock();
    }

}
