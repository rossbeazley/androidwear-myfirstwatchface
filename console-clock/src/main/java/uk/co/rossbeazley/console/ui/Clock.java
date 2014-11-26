package uk.co.rossbeazley.console.ui;

import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.days.DaysPresenter;
import uk.co.rossbeazley.wear.hours.HoursPresenter;
import uk.co.rossbeazley.wear.minutes.MinutesPresenter;
import uk.co.rossbeazley.wear.months.MonthsPresenter;
import uk.co.rossbeazley.wear.seconds.SecondsPresenter;

public class Clock {

    public Clock() {
        Main.init();
        Main main = Main.instance();
        class SysOutPrinter implements Printer {

            @Override
            public void print(String out) {
                System.out.println(out);
            }
        }
        Printer printer = new SysOutPrinter();
        ConsoleView view = new ConsoleView(printer);
        new DaysPresenter(main.core.canBeObservedForChangesToDays, view);
        new MonthsPresenter(main.core.canBeObservedForChangesToMonths, view);
        new HoursPresenter(main.core.canBeObservedForChangesToHours, view);
        new MinutesPresenter(main.core.canBeObservedForChangesToMinutes, view);
        new SecondsPresenter(main.core.canBeObservedForChangesToSeconds, view);
    }

    interface Printer {
        void print(String out);
    }


    public static void main(String... vargs) {
        new Clock();
    }

}
