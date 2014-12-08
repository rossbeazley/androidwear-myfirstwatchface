package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import uk.co.rossbeazley.wear.Core;

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

        Core core = Core.instance();
        AndroidSecondsView.createSecondsView(core, this);
        AndroidMinutesView.createMinutesView(core, this);
        AndroidHoursView.createHoursView(core, this);
        AndroidDayMonthView.createMonthDaysView(core, this);
        AndroidRotationView.createRotationView(core, this);
    }

}
