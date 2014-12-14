package uk.co.rossbeazley.wear.seconds;


import org.junit.Test;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.ui.Disposable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SecondsPresenterTest {

    private String timeComponentString = "UNSET";

    CanReceiveSecondsUpdates observer;

    @Test
    public void theOneWhereWeUpdateTheScreen() {

        CanBeObserved<CanReceiveSecondsUpdates> canBeObserved = new CanBeObserved<CanReceiveSecondsUpdates>(){
            @Override public void addListener(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
                observer = canReceiveSecondsUpdates;
            }

            @Override
            public void removeListener(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {}
        };

        SecondsPresenter.SecondsView view = new SecondsPresenter.SecondsView(){
            @Override public void showSecondsString(String seconds) {
                timeComponentString = seconds;
            }
        };

        Disposable secondsPresenter = new SecondsPresenter(canBeObserved, view);
        observer.secondsUpdate(Sexagesimal.fromBase10(10));
        assertThat(timeComponentString, is("10"));
    }


}
