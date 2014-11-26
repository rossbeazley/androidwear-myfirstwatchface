package uk.co.rossbeazley.ui;

import android.view.View;
import android.widget.TextView;

import com.examples.myfirstwatchface.R;

import uk.co.rossbeazley.wear.seconds.SecondsPresenter;

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

}
