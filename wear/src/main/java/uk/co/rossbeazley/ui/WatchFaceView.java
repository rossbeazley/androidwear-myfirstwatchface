package uk.co.rossbeazley.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.Main;

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

        AndroidSecondsView.createSecondsView(Core.instance, this);
        AndroidMinutesView.createMinutesView(Core.instance, this);
        AndroidHoursView.createHoursView(Core.instance, this);
        AndroidDayMonthView.createMonthDaysView(Core.instance, this);
        AndroidRotationView.createRotationView(Core.instance, this);
    }

}
