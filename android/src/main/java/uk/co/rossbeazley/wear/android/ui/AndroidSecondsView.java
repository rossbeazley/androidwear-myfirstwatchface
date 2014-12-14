package uk.co.rossbeazley.wear.android.ui;

import android.view.View;
import android.widget.TextView;


import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.R;
import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.seconds.CanReceiveSecondsUpdates;
import uk.co.rossbeazley.wear.seconds.SecondsPresenter;
import uk.co.rossbeazley.wear.ui.Disposable;

class AndroidSecondsView implements SecondsPresenter.SecondsView {
    private SetTextOnMainThread setTextOnMainThread;

    public AndroidSecondsView(View inflatedViews) {
        TextView textView = (TextView) inflatedViews.findViewById(R.id.watch_time_secs);
        setTextOnMainThread = new SetTextOnMainThread(textView);
    }

    @Override
    public void showSecondsString(final String newSeconds) {
        setTextOnMainThread.to(newSeconds);
    }

    public static Disposable createSecondsView(Core core, View views) {
        CanBeObserved<CanReceiveSecondsUpdates> seconds = core.canBeObservedForChangesToSeconds;
        AndroidSecondsView secondsview = new AndroidSecondsView(views);
        return new SecondsPresenter(seconds, secondsview);
    }
}
