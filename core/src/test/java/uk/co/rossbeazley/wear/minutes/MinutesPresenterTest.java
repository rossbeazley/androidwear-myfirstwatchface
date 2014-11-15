package uk.co.rossbeazley.wear.minutes;

import org.junit.Test;

import uk.co.rossbeazley.wear.Sexagesimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MinutesPresenterTest {

    private String timeComponentString;
    private CanBeObservedForChangesToMinutes.CanReceiveMinutesUpdates observer;

    @Test
    public void theOneWhereWeUpdateTheScreen() {
        CanBeObservedForChangesToMinutes canBeObservedForChangesToMinutes = new CanBeObservedForChangesToMinutes(){
            @Override public void observe(CanReceiveMinutesUpdates canReceiveSecondsUpdates) {
                observer = canReceiveSecondsUpdates;
            }
        };

        MinutesPresenter.MinutesView view = new MinutesPresenter.MinutesView(){
            @Override public void showMinutesString(String seconds) {
                timeComponentString = seconds;
            }
        };

        MinutesPresenter presenter = new MinutesPresenter(canBeObservedForChangesToMinutes, view);
        observer.minutesUpdate(Sexagesimal.fromBase10(10));
        assertThat(timeComponentString, is("10"));
    }

    private static class MinutesPresenter {

        public interface MinutesView {
            void showMinutesString(String seconds);
        }

        public MinutesPresenter(final CanBeObservedForChangesToMinutes canBeObservedForChangesToMinutes, final MinutesView view) {
            canBeObservedForChangesToMinutes.observe(new CanBeObservedForChangesToMinutes.CanReceiveMinutesUpdates() {
                @Override
                public void minutesUpdate(Sexagesimal to) {
                    view.showMinutesString(to.base10String());
                }
            });
        }
    }
}
