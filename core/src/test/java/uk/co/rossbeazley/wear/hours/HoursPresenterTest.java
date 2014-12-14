package uk.co.rossbeazley.wear.hours;

import org.junit.Test;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.HourBase24;

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

    private class Hours implements CanBeObserved<CanReceiveHoursUpdates> {
        private CanReceiveHoursUpdates observer;

        @Override
        public void addListener(CanReceiveHoursUpdates canReceiveHoursUpdates) {
            this.observer = canReceiveHoursUpdates;
        }

        @Override
        public void removeListener(CanReceiveHoursUpdates canReceiveHoursUpdates) {

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
