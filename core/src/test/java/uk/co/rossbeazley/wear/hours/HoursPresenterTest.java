package uk.co.rossbeazley.wear.hours;

import org.junit.Test;

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
