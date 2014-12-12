package uk.co.rossbeazley.wear.minutes;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.wear.seconds.CanBeObservedForChangesToSeconds;
import uk.co.rossbeazley.wear.seconds.SecondsPresenter;
import uk.co.rossbeazley.wear.ui.Disposable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MinutesPresenterDisposalTest {



    @Test
    public void theOneWhereWeStopObservingCoreOnDisposal() {
        List observers = new ArrayList(1);

        Disposable disposable = buildDisposable(observers);
        disposable.dispose();

        assertThat(observers.size(), is(0));
    }

    private Disposable buildDisposable(final List observers) {
        MinutesPresenter.MinutesView UNUSED_VIEW = null;
        CanBeObservedForChangesToMinutes canBeObservedForChangesToMinutes = new CanBeObservedForChangesToMinutes() {
            @Override
            public void observe(CanReceiveMinutesUpdates canReceiveSecondsUpdates) {
                observers.add(canReceiveSecondsUpdates);
            }

            @Override
            public void unObserve(CanReceiveMinutesUpdates canReceiveSecondsUpdates) {
                observers.remove(canReceiveSecondsUpdates);
            }


        };
        return new MinutesPresenter(canBeObservedForChangesToMinutes, UNUSED_VIEW);
    }

}
