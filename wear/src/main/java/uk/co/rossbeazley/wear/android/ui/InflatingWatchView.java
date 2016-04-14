package uk.co.rossbeazley.wear.android.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.watchview.WatchFaceService;
import uk.co.rossbeazley.watchview.WatchView;
import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.colour.CanReceiveColourUpdates;
import uk.co.rossbeazley.wear.colour.Colours;
import uk.co.rossbeazley.wear.hours.CanReceiveHoursUpdates;
import uk.co.rossbeazley.wear.hours.HourBase24;
import uk.co.rossbeazley.wear.minutes.CanReceiveMinutesUpdates;
import uk.co.rossbeazley.wear.seconds.CanReceiveSecondsUpdates;

class InflatingWatchView extends FrameLayout implements WatchView {

    private RedrawOnInvalidate redrawOnInvalidate;
    private TimeTick timeTick ;
    private WatchFaceService.CanLog logger = new WatchFaceService.CanLog() {
        @Override
        public void log(String msg) {
           // System.out.println("InflatingWatchView " + msg);
        }
    };
    private int currentLayout;
    private TimeTick.Tick currentTicks = TimeTick.Tick.NULL;

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
        inflateDarkView();
    }

    @Override
    public void toActive() {
        inflateFullView();
        logger.log("done toActive");
    }

    @Override
    public void toActiveOffset() {
        inflateOffsetView();
        logger.log("done toActiveOffset");
    }

    @Override
    public void toInvisible() {
        logger.log("toInvisible");
        tearDownView();
    }

    @Override
    public void registerServices(RedrawOnInvalidate redrawOnInvalidate, TimeTick timeTick) {
        this.redrawOnInvalidate = redrawOnInvalidate;
        this.timeTick = timeTick;
        Core.instance().canBeObservedForChangesToColour.addListener(invalidateWhenColourChages);
        Core.instance().canBeObservedForChangesToSeconds.addListener(invalidateViewWhenSecondsChange);
        Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
        Core.instance().canBeObservedForChangesToHours.addListener(invalidateViewWhenHoursChange);

        currentTicks = timeTick.scheduleTicks(200, TimeUnit.MILLISECONDS);
    }

    @Override
    public void timeTick(long duration, TimeUnit timeUnit) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(timeUnit.toMillis(duration));
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
        currentLayout = 0;
        this.removeAllViews();
    }


    private void inflateFullView() {
        logger.log("inflateActiveView");
        inflateLayout(R.layout.watch_face_view);
    }

    private void inflateDarkView() {
        logger.log("inflatePassiveView");
        inflateLayout(R.layout.watch_face_view_dimmed);
    }

    private void inflateOffsetView() {
        logger.log("inflateOffsetView");
        inflateLayout(R.layout.watch_face_view_offset);
    }

    private void inflateLayout(@LayoutRes int layoutId) {
        if (currentLayout != layoutId) {
            tearDownView();
            LayoutInflater li = LayoutInflater.from(getContext());
            li.inflate(layoutId, this);
            this.currentLayout = layoutId;
            logger.log("inflate complete");
        } else {
            logger.log("Not over inflating");
        }
    }

    private final boolean invalidateCanvasOnViewChanges = true;

    public final CanReceiveSecondsUpdates invalidateViewWhenSecondsChange = new CanReceiveSecondsUpdates() {
        @Override
        public void secondsUpdate(Sexagesimal to) {
            if (invalidateCanvasOnViewChanges) redrawOnInvalidate.postInvalidate();
        }
    };

    public final CanReceiveMinutesUpdates invalidateViewWhenMinutesChange = new CanReceiveMinutesUpdates() {
        @Override
        public void minutesUpdate(Sexagesimal to) {
            if (invalidateCanvasOnViewChanges) redrawOnInvalidate.postInvalidate();
        }
    };

    public final CanReceiveColourUpdates invalidateWhenColourChages = new CanReceiveColourUpdates() {
        @Override
        public void colourUpdate(Colours to) {
            if (invalidateCanvasOnViewChanges) redrawOnInvalidate.postInvalidate();
        }
    };

    public final CanReceiveHoursUpdates invalidateViewWhenHoursChange = new CanReceiveHoursUpdates() {
        @Override
        public void hoursUpdate(HourBase24 hourBase24) {
            if (invalidateCanvasOnViewChanges) redrawOnInvalidate.postInvalidate();
        }
    };

    public final MyCanReceiveColourUpdates colourUpdates = new MyCanReceiveColourUpdates();

    private static class MyCanReceiveColourUpdates implements CanReceiveColourUpdates {
        public Colours.Colour background;

        @Override
        public void colourUpdate(Colours to) {
            this.background = to.colour();

        }
    }

    {
        Core.instance().canBeObservedForChangesToColour.addListener(colourUpdates);
    }
}
