package uk.co.rossbeazley.wear.seconds;


import org.junit.Test;

import uk.co.rossbeazley.wear.Sexagesimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SecondsPresenterTest {

    private String timeComponentString = "UNSET";

    CanBeObservedForChangesToSeconds.CanReceiveSecondsUpdates observer;

    @Test
    public void theOneWhereWeUpdateTheScreen() {

        CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds = new CanBeObservedForChangesToSeconds(){
            @Override public void observe(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
                observer = canReceiveSecondsUpdates;
            }
        };

        SecondsPresenter.SecondsView view = new SecondsPresenter.SecondsView(){
            @Override public void showSecondsString(String seconds) {
                timeComponentString = seconds;
            }
        };

        SecondsPresenter secondsPresenter = new SecondsPresenter(canBeObservedForChangesToSeconds, view);
        observer.secondsUpdate(Sexagesimal.fromBase10(10));
        assertThat(timeComponentString, is("10"));
    }


    public static class SecondsPresenter {
        public SecondsPresenter(final CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds, final SecondsView view) {
            canBeObservedForChangesToSeconds.observe(new CanBeObservedForChangesToSeconds.CanReceiveSecondsUpdates() {
                @Override
                public void secondsUpdate(Sexagesimal to) {
                    view.showSecondsString(to.base10String());
                }
            });
        }

        public static interface SecondsView {
            void showSecondsString(String seconds);
        }
    }

}
