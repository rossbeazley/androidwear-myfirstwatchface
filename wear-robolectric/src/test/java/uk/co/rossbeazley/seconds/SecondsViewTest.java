package uk.co.rossbeazley.seconds;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import uk.co.rossbeazley.wear.seconds.SecondsPresenterTest;

@RunWith(RobolectricTestRunner.class)
public class SecondsViewTest {

    @Test
    public void theOneWhenTheViewShowsTheNumberOfSeconds() {
        //LayoutInflater.from(Robolectric.application);

        new AndroidSecondsView();
    }

    public static class AndroidSecondsView implements SecondsPresenterTest.SecondsPresenter.SecondsView{
        @Override
        public void showSecondsString(String seconds) {

        }
    }
}
