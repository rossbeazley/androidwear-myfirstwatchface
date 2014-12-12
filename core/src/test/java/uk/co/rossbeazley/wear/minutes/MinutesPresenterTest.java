package uk.co.rossbeazley.wear.minutes;

import org.junit.Test;

import uk.co.rossbeazley.wear.Sexagesimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MinutesPresenterTest {

    @Test
    public void theOneWhereWeUpdateTheScreen() {
        Minutes minutes = new Minutes();
        MinutesView view = new MinutesView();
        MinutesPresenter presenter = new MinutesPresenter(minutes, view);
        minutes.observer.minutesUpdate(Sexagesimal.fromBase10(10));
        assertThat(view.timeComponentString, is("10"));
    }

    private class Minutes implements CanBeObservedForChangesToMinutes {
        private CanReceiveMinutesUpdates observer;

        @Override public void observe(CanReceiveMinutesUpdates canReceiveSecondsUpdates) {
            observer = canReceiveSecondsUpdates;
        }

        @Override
        public void unObserve(CanReceiveMinutesUpdates canReceiveSecondsUpdates) {

        }
    }

    private class MinutesView implements MinutesPresenter.MinutesView {
        private String timeComponentString;

        @Override public void showMinutesString(String seconds) {
            timeComponentString = seconds;
        }
    }

}
