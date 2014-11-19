package uk.co.rossbeazley.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.examples.myfirstwatchface.R;

import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.days.CanBeObservedForChangesToDays;
import uk.co.rossbeazley.wear.days.DaysPresenter;
import uk.co.rossbeazley.wear.hours.CanBeObservedForChangesToHours;
import uk.co.rossbeazley.wear.hours.HoursPresenter;
import uk.co.rossbeazley.wear.minutes.CanBeObservedForChangesToMinutes;
import uk.co.rossbeazley.wear.minutes.MinutesPresenter;
import uk.co.rossbeazley.wear.seconds.CanBeObservedForChangesToSeconds;
import uk.co.rossbeazley.wear.seconds.SecondsPresenter;

/**
 * Created by beazlr02 on 14/11/2014.
 */
public class WatchFaceView extends RelativeLayout {
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
        createDaysView(main);
    }

    private void createDaysView(Main main) {
        CanBeObservedForChangesToDays days = main.core.canBeObservedForChangesToDays;
        new DaysPresenter(days, new DayMonthView(this));
    }

    private void createHoursView(Main main) {
        CanBeObservedForChangesToHours hours = main.core.canBeObservedForChangesToHours;
        AndroidHoursView hoursView = new AndroidHoursView(this);
        HoursPresenter hoursPresenter = new HoursPresenter(hours, hoursView);
    }

    private void createMinutesView(Main main) {
        CanBeObservedForChangesToMinutes minutes = main.core.canBeObservedForChangesToMinutes;
        MinutesPresenter.MinutesView minutesView = new AndroidMinutesView(this);
        MinutesPresenter minutesPresenter = new MinutesPresenter(minutes, minutesView);
    }

    private void createSecondsView(Main main) {
        CanBeObservedForChangesToSeconds seconds = main.core.canBeObservedForChangesToSeconds;
        AndroidSecondsView secondsview = new AndroidSecondsView(this);
        SecondsPresenter secondsPresenter = new SecondsPresenter(seconds, secondsview);
    }

    private static class AndroidMinutesView implements MinutesPresenter.MinutesView {
        private SetTextOnMainThread setTextOnMainThread;

        public AndroidMinutesView(View view) {
            TextView textView = (TextView) view.findViewById(R.id.watch_time_mins);
            setTextOnMainThread = new SetTextOnMainThread(textView);
        }

        @Override
        public void showMinutesString(final String minuteString) {
            setTextOnMainThread.to(minuteString);
        }
    }

    private static class AndroidHoursView implements HoursPresenter.HoursView {
        private SetTextOnMainThread setTextOnMainThread;

        public AndroidHoursView(View view) {
            TextView textView = (TextView) view.findViewById(R.id.watch_time);
            setTextOnMainThread = new SetTextOnMainThread(textView);
        }

        @Override
        public void showHoursString(final String newHour) {
            setTextOnMainThread.to(newHour);
        }
    }

    private class AndroidSecondsView implements SecondsPresenter.SecondsView {
        private SetTextOnMainThread setTextOnMainThread;

        public AndroidSecondsView(View inflatedViews) {
            TextView textView = (TextView) inflatedViews.findViewById(R.id.watch_time_secs);
            setTextOnMainThread = new SetTextOnMainThread(textView);
        }

        @Override
        public void showSecondsString(final String newSeconds) {
            setTextOnMainThread.to(newSeconds);
        }

    }

    private class DayMonthView implements DaysPresenter.DaysView {

        private final SetTextOnMainThread setTextOnMainThread;
        private String days;

        public DayMonthView(View inflatedViews) {
            TextView textView = (TextView) inflatedViews.findViewById(R.id.date);
            setTextOnMainThread = new SetTextOnMainThread(textView);
        }

        @Override
        public void showDaysString(String newDays) {
            days = newDays;
            update();
        }

        private void update() {
            setTextOnMainThread.to(days + " MONTH");
        }
    }

    private static class SetTextOnMainThread {
        private final TextView textView;

        public SetTextOnMainThread(TextView textView) {
            this.textView = textView;
        }

        public void to(final String text) {
             textView.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(text);
                }
            });
        }
    }
}
