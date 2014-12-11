package uk.co.rossbeazley.wear.seconds;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.wear.ui.Disposable;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SecondsPresenterDisposalTest {

    List<CanBeObservedForChangesToSeconds.CanReceiveSecondsUpdates> observers = new ArrayList<CanBeObservedForChangesToSeconds.CanReceiveSecondsUpdates>(1);

    @Test
    public void theOneWhereWeStopObservingCoreOnDisposal() {
        SecondsPresenter.SecondsView UNUSED_VIEW = null;
        CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds = new CanBeObservedForChangesToSeconds() {
            @Override
            public void observe(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
                observers.add(canReceiveSecondsUpdates);
            }

            @Override
            public void unObserve(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
                observers.remove(canReceiveSecondsUpdates);
            }


        };
        Disposable disposable = new SecondsPresenter(canBeObservedForChangesToSeconds, UNUSED_VIEW);
        disposable.dispose();

        assertThat(observers.size(), is(0));
    }

}
