package uk.co.rossbeazley.seconds;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.examples.myfirstwatchface.R;

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

        new SecondsView(this);

    }

    private class SecondsView {

        private final TextView seconds;

        public SecondsView(View inflatedViews) {
            seconds = (TextView) inflatedViews.findViewById(R.id.watch_time_secs);
        }
    }
}
