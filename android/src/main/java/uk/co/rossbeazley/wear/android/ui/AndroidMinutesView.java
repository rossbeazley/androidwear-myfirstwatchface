package uk.co.rossbeazley.wear.android.ui;

import android.view.View;
import android.widget.TextView;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.R;
import uk.co.rossbeazley.wear.minutes.CanReceiveMinutesUpdates;
import uk.co.rossbeazley.wear.minutes.MinutesPresenter;
import uk.co.rossbeazley.wear.ui.Disposable;

class AndroidMinutesView implements MinutesPresenter.MinutesView {
    private SetTextOnMainThread setTextOnMainThread;
    public final TextView textView;

    public AndroidMinutesView(View view) {
        textView = (TextView) view.findViewById(R.id.watch_time_mins);
        setTextOnMainThread = new SetTextOnMainThread();
    }

    @Override
    public void showMinutesString(final String minuteString) {
        setTextOnMainThread.updateTextView(minuteString, textView);
    }

    public static Disposable createMinutesView(Core core, View views) {
        CanBeObserved<CanReceiveMinutesUpdates> minutes = core.canBeObservedForChangesToMinutes;
        MinutesPresenter.MinutesView minutesView = new AndroidMinutesView(views);
        return new MinutesPresenter(minutes, minutesView);
    }
}
