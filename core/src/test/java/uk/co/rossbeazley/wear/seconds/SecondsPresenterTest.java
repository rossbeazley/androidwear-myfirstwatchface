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
    public void theOneWhereWeUpdateTheScreen() {

        CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds = new CanBeObservedForChangesToSeconds(){
            @Override public void observe(Seconds.CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
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
        public SecondsPresenter(final CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds, final SecondsView view) {
            canBeObservedForChangesToSeconds.observe(new Seconds.CanReceiveSecondsUpdates() {
                @Override
                public void secondsUpdate(Sexagesimal to) {
                    view.showSecondsString(to.base10String());
                }
            });
        }
    }

    private interface CanBeObservedForChangesToSeconds {
        void observe(Seconds.CanReceiveSecondsUpdates canReceiveSecondsUpdates);
    }

    private interface SecondsView {
        void showSecondsString(String seconds);
    }
}
