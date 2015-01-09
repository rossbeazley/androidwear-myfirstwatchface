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

public class WatchFaceView extends RelativeLayout {

    private Announcer<Disposable> disposables = Announcer.to(Disposable.class);
    private ToInvalidate rotateEngine;


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
        disposables.announce().dispose();
    }

    @Override
    public void requestLayout() {
        System.out.println("requestLayout");
        invalidateService();
        super.requestLayout();
    }

    public ViewParent invalidateChildInParent(final int[] location, final Rect dirty) {
        System.out.println("invalidate child in parent");
        invalidateService();
        return super.invalidateChildInParent(location, dirty);
    }

    private void invalidateService() {
        if (rotateEngine != null) {
            rotateEngine.invalidate();
        }
    }

    public void attachService(ToInvalidate rotateEngine) {

        this.rotateEngine = rotateEngine;

//        Core.instance().canBeObservedForChangesToSeconds.addListener(new CanReceiveSecondsUpdates() {
//            @Override
//            public void secondsUpdate(Sexagesimal to) {
//                WatchFaceView.this.invalidateService();
//            }
//        });

    }

    @Override
    public void invalidate() {
        System.out.println("invalidate");
        invalidateService();
        super.invalidate();
    }

    public static interface ToInvalidate {
        public void invalidate();
    }
}
