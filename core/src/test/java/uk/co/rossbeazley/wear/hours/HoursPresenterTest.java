package uk.co.rossbeazley.wear.hours;

import org.junit.Test;

import java.text.DecimalFormat;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HoursPresenterTest {

    @Test
    public void theOneWhereItsMorningAndTheHourUpdates() {
        Hours hours = new Hours();
        HoursView hoursView = new HoursView();
        new HoursPresenter(hours, hoursView);
        hours.observer.hoursUpdate(HourBase24.fromBase10(8));
        assertThat(hoursView.timeComponentString, is("08"));
    }

    @Test
    public void theOneWhereItsEveningAndTheHourUpdatesInTwelveHourFormat() {
        Hours hours = new Hours();
        HoursView hoursView = new HoursView();
        new HoursPresenter(hours, hoursView);
        hours.observer.hoursUpdate(HourBase24.fromBase10(14));
        assertThat(hoursView.timeComponentString, is("02"));
    }

    private static class HourBase24 {
        private final int value;

        public HourBase24(int base10) {
            value = base10 % 24;
        }

        public static HourBase24 fromBase10(int base10) {
            return new HourBase24(base10);
        }

        public String toBase10TwelveHour() {
            DecimalFormat numberFormat = new DecimalFormat("00");
            return numberFormat.format(value%12);
        }
    }

    private static class HoursPresenter {
        public HoursPresenter(CanBeObservedForChangesToHours canBeObservedForChangesToHours, final HoursView hoursView) {
            canBeObservedForChangesToHours.observe(new CanBeObservedForChangesToHours.CanReceiveHoursUpdates() {
                @Override
                public void hoursUpdate(HourBase24 hourBase24) {
                    hoursView.showHoursString(hourBase24.toBase10TwelveHour());
                }
            });
        }

        private interface HoursView {
            public void showHoursString(String hours);
        }
    }

    private interface CanBeObservedForChangesToHours {
        public void observe(CanReceiveHoursUpdates canReceiveHoursUpdates);

        interface CanReceiveHoursUpdates {
            void hoursUpdate(HourBase24 hourBase24);
        }
    }

    private class Hours implements CanBeObservedForChangesToHours {
        private CanReceiveHoursUpdates observer;

        @Override
        public void observe(CanReceiveHoursUpdates canReceiveHoursUpdates) {
            this.observer = canReceiveHoursUpdates;
        }
    }

    private class HoursView implements HoursPresenter.HoursView {
        private String timeComponentString;

        @Override
        public void showHoursString(String hours) {
            timeComponentString = hours;
        }
    }
}
