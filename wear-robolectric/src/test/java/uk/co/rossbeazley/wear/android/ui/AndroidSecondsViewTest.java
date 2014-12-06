package uk.co.rossbeazley.wear.android.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import uk.co.rossbeazley.wear.android.R;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class AndroidSecondsViewTest {

    @Test
    public void theOneWhenTheViewShowsTheNumberOfSeconds() {
        LayoutInflater li = LayoutInflater.from(Robolectric.application);

        View views = li.inflate(R.layout.watch_face_view, null);
        AndroidSecondsView view = new AndroidSecondsView(views);
        view.showSecondsString("1234");
        String actual = (String) ((TextView)views.findViewById(R.id.watch_time_secs)).getText();
        assertThat(actual,is("1234"));

    }

}
