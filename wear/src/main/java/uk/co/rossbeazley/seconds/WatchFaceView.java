package uk.co.rossbeazley.seconds;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.examples.myfirstwatchface.R;

import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.hours.CanBeObservedForChangesToHours;
import uk.co.rossbeazley.wear.hours.HoursPresenter;
import uk.co.rossbeazley.wear.minutes.CanBeObservedForChangesToMinutes;
import uk.co.rossbeazley.wear.minutes.MinutesPresenter;
import uk.co.rossbeazley.wear.seconds.CanBeObservedForChangesToSeconds;
import uk.co.rossbeazley.wear.seconds.SecondsPresenter;

/**
 * Created by beazlr02 on 14/11/2014.
 */
public class WatchFaceView extends RelativeLayout implements CanPostToMainThread {
    public WatchFaceView(Context context) {
        super(context);
    }

    public WatchFaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WatchFaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Main main = Main.instance();

        createSecondsView(main);
        createMinutesView(main);
        createHoursView(main);
    }

    private void createHoursView(Main main) {
        CanBeObservedForChangesToHours hours = main.core.canBeObservedForChangesToHours;
        AndroidHoursView hoursView = new AndroidHoursView(this);
        HoursPresenter hoursPresenter = new HoursPresenter(hours, hoursView);
    }

    private void createMinutesView(Main main) {
        CanBeObservedForChangesToMinutes minutes = main.core.canBeObservedForChangesToMinutes;
        MinutesPresenter.MinutesView minutesView = new AndroidMinutesView(this, this);
        MinutesPresenter minutesPresenter = new MinutesPresenter(minutes, minutesView);
    }

    private void createSecondsView(Main main) {
        CanBeObservedForChangesToSeconds seconds = main.core.canBeObservedForChangesToSeconds;
        AndroidSecondsView secondsview = new AndroidSecondsView(this, this);
        SecondsPresenter secondsPresenter = new SecondsPresenter(seconds, secondsview);
    }

    private static class AndroidMinutesView implements MinutesPresenter.MinutesView {
        private final TextView minutes;
        private final CanPostToMainThread mainThread;

        public AndroidMinutesView(View view, CanPostToMainThread canPostToMainThread) {
            mainThread = canPostToMainThread;
            this.minutes = (TextView) view.findViewById(R.id.watch_time_mins);
        }

        @Override
        public void showMinutesString(final String minuteString) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    minutes.setText(minuteString);
                }
            });
        }
    }

    private static class AndroidHoursView implements HoursPresenter.HoursView {
        private final TextView hours;

        public AndroidHoursView(View view) {
            hours = (TextView) view.findViewById(R.id.watch_time);
        }

        @Override
        public void showHoursString(final String newHour) {
            this.hours.post(new Runnable() {
                @Override
                public void run() {
                    hours.setText(newHour);
                }
            });
        }
    }

    private class AndroidSecondsView implements SecondsPresenter.SecondsView {

        private final TextView seconds;
        private final CanPostToMainThread mainThread;

        public AndroidSecondsView(View inflatedViews, CanPostToMainThread mainThread) {
            this.mainThread = mainThread;
            seconds = (TextView) inflatedViews.findViewById(R.id.watch_time_secs);
        }

        @Override
        public void showSecondsString(final String newSeconds) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    seconds.setText(newSeconds);
                }
            }); //SMELL this main thread post solution will get out of hand quickly, maybe I could dispatch on the main thread from the Announcer?
        }
    }
}
