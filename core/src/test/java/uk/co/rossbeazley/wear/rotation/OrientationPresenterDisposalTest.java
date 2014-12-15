package uk.co.rossbeazley.wear.rotation;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.months.CanReceiveMonthsUpdates;
import uk.co.rossbeazley.wear.months.MonthsPresenter;
import uk.co.rossbeazley.wear.ui.Disposable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrientationPresenterDisposalTest {



    @Test
    public void theOneWhereWeStopObservingCoreOnDisposal() {
        List observers = new ArrayList(1);

        Disposable disposable = buildDisposable(observers);
        disposable.dispose();

        assertThat(observers.size(), is(0));
    }

    private Disposable buildDisposable(final List observers) {

        CanBeObserved<CanReceiveRotationUpdates> canBeObserved = new CanBeObserved<CanReceiveRotationUpdates>() {
            @Override
            public void addListener(CanReceiveRotationUpdates canReceiveSecondsUpdates) {
                observers.add(canReceiveSecondsUpdates);
            }

            @Override
            public void removeListener(CanReceiveRotationUpdates canReceiveSecondsUpdates) {
                observers.remove(canReceiveSecondsUpdates);
            }


        };
        return new RotationPresenter(null,canBeObserved);
    }

}
