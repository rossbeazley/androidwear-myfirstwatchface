package uk.co.rossbeazley.wear.seconds;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.wear.ui.Disposable;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SecondsPresenterDisposalTest {



    @Test
    public void theOneWhereWeStopObservingCoreOnDisposal() {
        List observers = new ArrayList(1);

        Disposable disposable = buildDisposable(observers);
        disposable.dispose();

        assertThat(observers.size(), is(0));
    }

    private Disposable buildDisposable(final List observers) {
        SecondsPresenter.SecondsView UNUSED_VIEW = null;
        CanBeObservedForChangesToSeconds<CanReceiveSecondsUpdates> canBeObservedForChangesToSeconds = new CanBeObservedForChangesToSeconds<CanReceiveSecondsUpdates>() {
            @Override
            public void addListener(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
                observers.add(canReceiveSecondsUpdates);
            }

            @Override
            public void removeListener(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
                observers.remove(canReceiveSecondsUpdates);
            }


        };
        return new SecondsPresenter(canBeObservedForChangesToSeconds, UNUSED_VIEW);
    }

}
