package uk.co.rossbeazley.wear.seconds;


import org.junit.Ignore;
import org.junit.Test;

import uk.co.rossbeazley.wear.Sexagesimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SecondsPresenterTest {

    private String timeComponentString = "UNSET";

    Seconds.CanReceiveSecondsUpdates observer;

    @Test
    @Ignore
    public void theOneWhereWeUpdateTheScreen() {

        CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds = new CanBeObservedForChangesToSeconds(){
            public void observe(Seconds.CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
                observer = canReceiveSecondsUpdates;
            }
        };

        SecondsView view = new SecondsView(){
            @Override public void showSecondsString(String seconds) {
                timeComponentString = seconds;
            }
        };

        SecondsPresenter secondsPresenter = new SecondsPresenter(canBeObservedForChangesToSeconds, view);

        observer.secondsUpdate(Sexagesimal.fromBase10(10));

        assertThat(timeComponentString, is("10"));
    }


    private class SecondsPresenter {
        public SecondsPresenter(CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds, SecondsView view) {

        }
    }

    private interface CanBeObservedForChangesToSeconds {
    }

    private interface SecondsView {
        void showSecondsString(String seconds);
    }
}
