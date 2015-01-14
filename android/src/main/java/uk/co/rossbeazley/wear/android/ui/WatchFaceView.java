package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.seconds.CanReceiveSecondsUpdates;
import uk.co.rossbeazley.wear.ui.Disposable;

public class WatchFaceView extends RelativeLayout implements Disposable {

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
        disposables.addListener(AndroidMinutesView.createMinutesView(core, this));
        disposables.addListener(AndroidHoursView.createHoursView(core, this));
        disposables.addListener(AndroidDayMonthView.createMonthDaysView(core, this));
        disposables.addListener(AndroidRotationView.createRotationView(core, this));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dispose();
    }

    public void dispose() {
        disposables.announce().dispose();
    }

}
