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

import java.util.Calendar;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.colour.CanReceiveColourUpdates;
import uk.co.rossbeazley.wear.colour.Colours;
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
        Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
    }

    @Override
    public void toActive() {
        tearDownView();
        inflateActiveView();
        Core.instance().canBeObservedForChangesToSeconds.addListener(invalidateViewWhenSecondsChange);
        Core.instance().canBeObservedForChangesToColour.addListener(colourUpdates);
        Main.instance().tickTock.start();
        logger.log("done toActive");
    }

    @Override
    public void toActiveOffset() {
        tearDownView();
        inflateOffsetView();
        Core.instance().canBeObservedForChangesToSeconds.addListener(invalidateViewWhenSecondsChange);
        Core.instance().canBeObservedForChangesToColour.addListener(colourUpdates);
        Main.instance().tickTock.start();
    }

    @Override
    public void toInvisible() {
        logger.log("toInvisible");
        Main.instance().tickTock.startLowResolution();
        Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
    }

    @Override
    public void registerInvalidator(RedrawOnInvalidate redrawOnInvalidate) {
        this.redrawOnInvalidate = redrawOnInvalidate;
    }

    @Override
    public void timeTick(Calendar instance) {
        Core.instance().canBeTicked.tick(instance);
    }

    @Override
    public int background() {
        return colourUpdates.background.toInt();
    }

    @Override
    protected void onDetachedFromWindow() {
        logger.log("destroy");
        tearDownView();
        super.onDetachedFromWindow();
    }

    private void tearDownView() {
        logger.log("tearDownView");
        this.removeAllViews();
        Main.instance().tickTock.stop();
        Core.instance().canBeObservedForChangesToMinutes.removeListener(invalidateViewWhenMinutesChange);
        Core.instance().canBeObservedForChangesToSeconds.removeListener(invalidateViewWhenSecondsChange);
        Core.instance().canBeObservedForChangesToColour.removeListener(colourUpdates);
    }


    private void inflateActiveView() {
        logger.log("inflateActiveView");
        inflateLayout(R.layout.watch_face_view);
    }

    private void inflatePassiveView() {
        logger.log("inflatePassiveView");
        inflateLayout(R.layout.watch_face_view_dimmed);
    }

    private void inflateOffsetView() {
        logger.log("inflateOffsetView");
        inflateLayout(R.layout.watch_face_view_offset);
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

    public final MyCanReceiveColourUpdates colourUpdates = new MyCanReceiveColourUpdates();

    private static class MyCanReceiveColourUpdates implements CanReceiveColourUpdates {
        public Colours.Colour background;

        @Override
        public void colourUpdate(Colours to) {
            this.background = to.background();
        }
    }
}
