package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.ui.Disposable;

public class WatchFaceView extends RelativeLayout {

    private Announcer<Disposable> disposables = Announcer.to(Disposable.class);


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
        disposables.addListener(AndroidSecondsView.createSecondsView(core, this));
        AndroidMinutesView.createMinutesView(core, this);
        AndroidHoursView.createHoursView(core, this);
        AndroidDayMonthView.createMonthDaysView(core, this);
        AndroidRotationView.createRotationView(core, this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        disposables.announce().dispose();
    }
}
