package uk.co.rossbeazley.seconds;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.examples.myfirstwatchface.R;

import uk.co.rossbeazley.wear.Main;
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

        AndroidSecondsView secondsview = new AndroidSecondsView(this);
        CanBeObservedForChangesToSeconds seconds = Main.canBeObservedForChangesToSeconds;
        SecondsPresenter secondsPresenter = new SecondsPresenter(seconds, secondsview);

    }

    private class AndroidSecondsView implements SecondsPresenter.SecondsView {

        private final TextView seconds;

        public AndroidSecondsView(View inflatedViews) {
            seconds = (TextView) inflatedViews.findViewById(R.id.watch_time_secs);
        }

        @Override
        public void showSecondsString(String seconds) {
            this.seconds.setText(seconds);
        }
    }
}
