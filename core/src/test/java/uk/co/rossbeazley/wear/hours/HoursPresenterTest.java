package uk.co.rossbeazley.wear.hours;

import org.junit.Ignore;
import org.junit.Test;

import java.text.DecimalFormat;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HoursPresenterTest {

    private String timeComponentString;
    private CanReceiveHoursUpdates observer;

    @Test
    public void theOneWhereItsMorningAndTheHourUpdates() {

        new HoursPresenter(new CanBeObservedForChangesToHours(){
            @Override
            public void observe(CanReceiveHoursUpdates canReceiveHoursUpdates) {
                observer = canReceiveHoursUpdates;
            }
        }, new HoursView(){
            @Override
            public void showHoursString(String hours) {
                timeComponentString = hours;
            }
        });

        observer.hoursUpdate(Base24.fromBase10(8));

        assertThat(timeComponentString, is("08"));
    }

    @Test @Ignore("one at a time")
    public void theOneWhereItsEveningAndTheHourUpdates() {

    }

    private interface CanReceiveHoursUpdates {
        void hoursUpdate(Base24 base24);
    }

    private static class Base24 {
        private final int value;

        public Base24(int base10) {
            value = base10 % 24;
        }

        public static Base24 fromBase10(int base10) {
            return new Base24(base10);
        }

        public String toBase10() {
            DecimalFormat numberFormat = new DecimalFormat("00");
            return numberFormat.format(value);
        }
    }

    private class HoursPresenter {
        public HoursPresenter(CanBeObservedForChangesToHours canBeObservedForChangesToHours, final HoursView hoursView) {
            canBeObservedForChangesToHours.observe(new CanReceiveHoursUpdates() {
                @Override
                public void hoursUpdate(Base24 base24) {
                    hoursView.showHoursString(base24.toBase10());
                }
            });
        }
    }

    private interface CanBeObservedForChangesToHours {
        public void observe(CanReceiveHoursUpdates canReceiveHoursUpdates);
    }

    private interface HoursView {
        public void showHoursString(String hours);
    }
}
