package uk.co.rossbeazley.seconds;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import uk.co.rossbeazley.wear.seconds.SecondsPresenter;
import uk.co.rossbeazley.wear.seconds.SecondsPresenterTest;

@RunWith(RobolectricTestRunner.class)
public class AndroidSecondsViewTest {

    @Test
    public void theOneWhenTheViewShowsTheNumberOfSeconds() {
        //LayoutInflater.from(Robolectric.application);

        new AndroidSecondsView();
    }

    public static class AndroidSecondsView implements SecondsPresenter.SecondsView{
        @Override
        public void showSecondsString(String seconds) {

        }
    }
}
