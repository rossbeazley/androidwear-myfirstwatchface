package uk.co.rossbeazley.wear.android.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
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

    private int color;
    private boolean ambient;
    private boolean offset;
    private boolean active;
    private RedrawOnInvalidate redrawOnInvalidate;
    private WatchFaceService.CanLog logger = new WatchFaceService.CanLog() {
        @Override
        public void log(String msg) {
            System.out.println("InflatingWatchView " + msg);
        }
    };

    private Rect currentPeekCardPosition;


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


    @NonNull
    private static Rect adjustDrawingAreaForAnyNotificationCards(Rect bounds, Rect peekCardPosition) {
        Rect rtn = new Rect();
        rtn.set(bounds);
        if(peekCardPosition!=null) rtn.bottom = bounds.bottom + (peekCardPosition.top - peekCardPosition.bottom);
        return rtn;
    }


    @Override
    public void toAmbient() {
        logger.log("maybeToAmbient");
        if (ambient) return;

        logger.log("entered ambient");

        ambient = true;
        active = false;
        offset = false;

        color = Color.BLACK;
        tearDownView();
        inflatePassiveView();

        Main.instance().tickTock.stop();
    }

    @Override
    public void toActive() {
        logger.log("maybeToActive");
        if (active) return;

        logger.log("entered active");
        ambient = false;
        active = true;
        offset = false;

        currentPeekCardPosition = null;

        color = Color.WHITE;
        tearDownView();
        inflateActiveView();

        Main.instance().tickTock.start();
        logger.log("done toActive");
    }

    @Override
    public void toOffsetView() {
        logger.log("maybeToOffsetView");
        if (offset) return;

        logger.log("entered offsetView");
        ambient = false;
        active = false;
        offset = true;

        color = Color.WHITE;
        tearDownView();
        inflateOffsetView();

        Main.instance().tickTock.start();


    }


    @Override
    protected void onDetachedFromWindow() {
        logger.log("destroy");
        super.onDetachedFromWindow();
        tearDownView();
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

    private void tearDownView() {
        logger.log("tearDownView");
        this.removeAllViews();
        Core.instance().canBeObservedForChangesToMinutes.removeListener(invalidateViewWhenMinutesChange);
        Core.instance().canBeObservedForChangesToSeconds.removeListener(invalidateViewWhenSecondsChange);
    }


    private void inflateActiveView() {
        logger.log("inflateActiveView");
        LayoutInflater li = LayoutInflater.from(getContext());
        li.inflate(R.layout.watch_face_view, this);
        Core.instance().canBeObservedForChangesToSeconds.addListener(invalidateViewWhenSecondsChange);

    }

    private void inflatePassiveView() {
        logger.log("inflatePassiveView");
        LayoutInflater li = LayoutInflater.from(getContext());
        li.inflate(R.layout.watch_face_view_dimmed, this);
        Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
    }

    private void inflateOffsetView() {
        logger.log("inflateOffsetView");
        LayoutInflater li = LayoutInflater.from(getContext());
                li.inflate(R.layout.watch_face_view_offset, this);
        Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
    }



    @Override
    public void toInvisible() {
        logger.log("toInvisible");
        Main.instance().tickTock.stop();

        ambient = false;
        active = false;
        offset = false;
    }

    @Override
    public void registerInvalidator(RedrawOnInvalidate redrawOnInvalidate) {
        this.redrawOnInvalidate = redrawOnInvalidate;
    }
}
