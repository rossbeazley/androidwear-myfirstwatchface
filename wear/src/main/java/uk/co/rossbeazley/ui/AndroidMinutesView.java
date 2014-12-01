package uk.co.rossbeazley.ui;

import android.view.View;
import android.widget.TextView;

import com.examples.myfirstwatchface.R;

import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.minutes.CanBeObservedForChangesToMinutes;
import uk.co.rossbeazley.wear.minutes.MinutesPresenter;

class AndroidMinutesView implements MinutesPresenter.MinutesView {
    private SetTextOnMainThread setTextOnMainThread;

    public AndroidMinutesView(View view) {
        TextView textView = (TextView) view.findViewById(R.id.watch_time_mins);
        setTextOnMainThread = new SetTextOnMainThread(textView);
    }

    @Override
    public void showMinutesString(final String minuteString) {
        setTextOnMainThread.to(minuteString);
    }

    public static void createMinutesView(Main main, View views) {
        CanBeObservedForChangesToMinutes minutes = main.core.canBeObservedForChangesToMinutes;
        MinutesPresenter.MinutesView minutesView = new AndroidMinutesView(views);
        new MinutesPresenter(minutes, minutesView);
    }
}
