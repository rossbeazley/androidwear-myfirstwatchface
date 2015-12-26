package uk.co.rossbeazley.wear.android.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.minutes.CanReceiveMinutesUpdates;
import uk.co.rossbeazley.wear.seconds.CanReceiveSecondsUpdates;

class InflatingWatchView extends FrameLayout implements WatchView {

    private RedrawOnInvalidate redrawOnInvalidate;
    private WatchFaceService.CanLog logger = new WatchFaceService.CanLog() {
        @Override
        public void log(String msg) {
            System.out.println("InflatingWatchView " + msg);
        }
    };

    public InflatingWatchView(Context context) {
        super(context);
    }

    public InflatingWatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InflatingWatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InflatingWatchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void toAmbient() {
        tearDownView();
        inflatePassiveView();
        Main.instance().tickTock.stop();
    }

    @Override
    public void toActive() {
        tearDownView();
        inflateActiveView();
        Main.instance().tickTock.start();
        logger.log("done toActive");
    }

    @Override
    public void toOffsetView() {
        tearDownView();
        inflateOffsetView();
        Main.instance().tickTock.start();
    }

    @Override
    public void toInvisible() {
        logger.log("toInvisible");
        Main.instance().tickTock.stop();
        Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
    }

    @Override
    public void registerInvalidator(RedrawOnInvalidate redrawOnInvalidate) {
        this.redrawOnInvalidate = redrawOnInvalidate;
    }

    @Override
    protected void onDetachedFromWindow() {
        logger.log("destroy");
        super.onDetachedFromWindow();
        tearDownView();
    }

    private void tearDownView() {
        logger.log("tearDownView");
        this.removeAllViews();
        Core.instance().canBeObservedForChangesToMinutes.removeListener(invalidateViewWhenMinutesChange);
        Core.instance().canBeObservedForChangesToSeconds.removeListener(invalidateViewWhenSecondsChange);
    }


    private void inflateActiveView() {
        logger.log("inflateActiveView");
        int watch_face_view = R.layout.watch_face_view;
        inflateLayout(watch_face_view);
        Core.instance().canBeObservedForChangesToSeconds.addListener(invalidateViewWhenSecondsChange);
    }

    private void inflatePassiveView() {
        logger.log("inflatePassiveView");
        inflateLayout(R.layout.watch_face_view_dimmed);
        Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
    }

    private void inflateOffsetView() {
        logger.log("inflateOffsetView");
        inflateLayout(R.layout.watch_face_view_offset);
        Core.instance().canBeObservedForChangesToSeconds.addListener(invalidateViewWhenSecondsChange);
    }

    private void inflateLayout(@LayoutRes int layoutId) {
        LayoutInflater li = LayoutInflater.from(getContext());
        li.inflate(layoutId, this);
    }

    public final CanReceiveSecondsUpdates invalidateViewWhenSecondsChange = new CanReceiveSecondsUpdates() {
        @Override
        public void secondsUpdate(Sexagesimal to) {
            redrawOnInvalidate.postInvalidate();
        }
    };

    public final CanReceiveMinutesUpdates invalidateViewWhenMinutesChange = new CanReceiveMinutesUpdates() {
        @Override
        public void minutesUpdate(Sexagesimal to) {
            redrawOnInvalidate.postInvalidate();
        }
    };

}
