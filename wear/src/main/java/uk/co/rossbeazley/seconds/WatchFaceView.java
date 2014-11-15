package uk.co.rossbeazley.seconds;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.examples.myfirstwatchface.R;

import uk.co.rossbeazley.wear.Main;
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

        Main instance = Main.instance();

        createSecondsView(instance);
        createMinutesView(instance);
    }

    private void createMinutesView(Main instance) {
        CanBeObservedForChangesToMinutes minutes = instance.canBeObservedForChangesToMinutes;
        MinutesPresenter.MinutesView minutesView = new AndroidMinutesView(this, this);
        MinutesPresenter minutesPresenter = new MinutesPresenter(minutes, minutesView);
    }

    private void createSecondsView(Main instance) {
        CanBeObservedForChangesToSeconds seconds = instance.canBeObservedForChangesToSeconds;
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
