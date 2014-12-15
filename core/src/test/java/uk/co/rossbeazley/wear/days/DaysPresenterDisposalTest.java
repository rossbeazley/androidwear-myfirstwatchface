package uk.co.rossbeazley.wear.days;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.hours.CanReceiveHoursUpdates;
import uk.co.rossbeazley.wear.hours.HoursPresenter;
import uk.co.rossbeazley.wear.ui.Disposable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DaysPresenterDisposalTest {



    @Test
    public void theOneWhereWeStopObservingCoreOnDisposal() {
        List observers = new ArrayList(1);

        Disposable disposable = buildDisposable(observers);
        disposable.dispose();

        assertThat(observers.size(), is(0));
    }

    private Disposable buildDisposable(final List observers) {

        CanBeObserved<CanReceiveDaysUpdates> canBeObserved = new CanBeObserved<CanReceiveDaysUpdates>() {
            @Override
            public void addListener(CanReceiveDaysUpdates canReceiveSecondsUpdates) {
                observers.add(canReceiveSecondsUpdates);
            }

            @Override
            public void removeListener(CanReceiveDaysUpdates canReceiveSecondsUpdates) {
                observers.remove(canReceiveSecondsUpdates);
            }


        };
        return new DaysPresenter(canBeObserved, null);
    }

}
