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
        hours.observer.hoursUpdate(Base24.fromBase10(8));
        assertThat(hoursView.timeComponentString, is("08"));
    }

    @Test
    public void theOneWhereItsEveningAndTheHourUpdatesInTwelveHourFormat() {
        Hours hours = new Hours();
        HoursView hoursView = new HoursView();
        new HoursPresenter(hours, hoursView);
        hours.observer.hoursUpdate(Base24.fromBase10(14));
        assertThat(hoursView.timeComponentString, is("02"));
    }

    private static class Base24 {
        private final int value;

        public Base24(int base10) {
            value = base10 % 24;
        }

        public static Base24 fromBase10(int base10) {
            return new Base24(base10);
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
                public void hoursUpdate(Base24 base24) {
                    hoursView.showHoursString(base24.toBase10TwelveHour());
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
            void hoursUpdate(Base24 base24);
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
