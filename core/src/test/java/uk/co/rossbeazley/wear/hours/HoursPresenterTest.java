package uk.co.rossbeazley.wear.hours;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.colour.CanReceiveColourUpdates;
import uk.co.rossbeazley.wear.colour.Colours;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HoursPresenterTest {

    private Hours hours;
    private HoursView hoursView;
    private HoursPresenter hoursPresenter;
    private HoursColour hoursColour;

    @Before
    public void setUp() throws Exception {
        hours = new Hours();
        hoursView = new HoursView();
        hoursColour = new HoursColour();
        hoursPresenter = new HoursPresenter(hours, hoursView,hoursColour);
    }

    @Test
    public void theOneWhereItsMorningAndTheHourUpdates() {
        hours.observer.hoursUpdate(HourBase24.fromBase10(8));
        assertThat(hoursView.timeComponentString, is("08"));
    }

    @Test
    public void theOneWhereItsEveningAndTheHourUpdatesInTwelveHourFormat() {
        hours.observer.hoursUpdate(HourBase24.fromBase10(14));
        assertThat(hoursView.timeComponentString, is("02"));
    }

    @Test
    public void theBackgroundColourUpdates() {
        hoursColour.announceColour(Colours.Colour.RED);
        assertThat(hoursView.colourInt, is(0xFFFF0000));
    }

    private static class HoursColour implements CanBeObserved<CanReceiveColourUpdates> {
        private CanReceiveColourUpdates canReceiveSecondsUpdates;

        @Override
        public void addListener(CanReceiveColourUpdates canReceiveSecondsUpdates) {
            this.canReceiveSecondsUpdates = canReceiveSecondsUpdates;
        }

        @Override
        public void removeListener(CanReceiveColourUpdates canReceiveSecondsUpdates) {

        }

        public void announceColour(Colours.Colour red) {
            canReceiveSecondsUpdates.colourUpdate(new Colours(red));
        }
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
        public int colourInt;

        @Override
        public void showHoursString(String hours) {
            timeComponentString = hours;
        }

        @Override
        public void showHoursColour(int colourInt) {
            this.colourInt = colourInt;
        }
    }
}
