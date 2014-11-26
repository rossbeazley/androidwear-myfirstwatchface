package uk.co.rossbeazley.console.ui;

import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.days.DaysPresenter;
import uk.co.rossbeazley.wear.hours.HoursPresenter;
import uk.co.rossbeazley.wear.minutes.MinutesPresenter;
import uk.co.rossbeazley.wear.months.MonthsPresenter;
import uk.co.rossbeazley.wear.seconds.SecondsPresenter;

public class Clock {

    public static void main(String... vargs) {
        Main.init();
        Main main = Main.instance();
        ConsoleView view = new ConsoleView();
        new DaysPresenter(main.core.canBeObservedForChangesToDays, view);
        new MonthsPresenter(main.core.canBeObservedForChangesToMonths, view);
        new HoursPresenter(main.core.canBeObservedForChangesToHours, view);
        new MinutesPresenter(main.core.canBeObservedForChangesToMinutes, view);
        new SecondsPresenter(main.core.canBeObservedForChangesToSeconds, view);
    }

    private static class ConsoleView implements DaysPresenter.DaysView, MonthsPresenter.MonthView, HoursPresenter.HoursView, MinutesPresenter.MinutesView, SecondsPresenter.SecondsView {

        private String newDays;
        private String monthString;
        private String hours;
        private String minutes;
        private String seconds;

        private void redraw() {
            System.out.println(hours + ":" + minutes + ":" + seconds + " " + newDays + " " + monthString);
        }

        @Override
        public void showDaysString(String newDays) {
            this.newDays = newDays;
            redraw();
        }

        @Override
        public void showMonthString(String monthString) {
            this.monthString = monthString;
            redraw();
        }

        @Override
        public void showHoursString(String hours) {
            this.hours = hours;
            redraw();
        }

        @Override
        public void showMinutesString(String minutes) {
            this.minutes = minutes;
            redraw();
        }

        @Override
        public void showSecondsString(String seconds) {
            this.seconds = seconds;
            redraw();
        }
    }
}
