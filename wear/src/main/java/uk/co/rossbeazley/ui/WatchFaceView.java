package uk.co.rossbeazley.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

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

        Main main = Main.instance();

        AndroidSecondsView.createSecondsView(main, this);
        AndroidMinutesView.createMinutesView(main,this);
        AndroidHoursView.createHoursView(main,this);
        AndroidDayMonthView.createMonthDaysView(main,this);
        AndroidRotationView.createRotationView(main.core, this);
    }

}
