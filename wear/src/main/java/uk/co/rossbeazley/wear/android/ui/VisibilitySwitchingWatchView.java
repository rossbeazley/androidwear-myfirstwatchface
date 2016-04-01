package uk.co.rossbeazley.wear.android.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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

public class VisibilitySwitchingWatchView extends FrameLayout implements WatchView {

    private View offsetView;
    private View darkView;
    private View fullView;
    private RedrawOnInvalidate redrawOnInvalidate;
    private TimeTick timeTick;
    private WatchFaceService.CanLog logger = new WatchFaceService.CanLog() {
        @Override
        public void log(String msg) {
            // System.out.println("InflatingWatchView " + msg);
        }
    };

    private void initViews() {
        System.out.println("INIT VIEWS");
        this.fullView = inflateFullView();
        this.darkView = inflateDarkView();
        this.offsetView = inflateOffsetView();
    }

    private TimeTick.Tick currentTicks = TimeTick.Tick.NULL;

    public VisibilitySwitchingWatchView(Context context) {
        super(context);
        initViews();
    }

    public VisibilitySwitchingWatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public VisibilitySwitchingWatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VisibilitySwitchingWatchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews();
    }

    @Override
    public void toAmbient() {
        offsetView.setVisibility(GONE);
        fullView.setVisibility(GONE);
        darkView.setVisibility(VISIBLE);
    }

    @Override
    public void toActive() {
        offsetView.setVisibility(GONE);
        darkView.setVisibility(GONE);
        fullView.setVisibility(VISIBLE);
    }

    @Override
    public void toActiveOffset() {
        offsetView.setVisibility(VISIBLE);
        darkView.setVisibility(GONE);
        fullView.setVisibility(GONE);
    }

    @Override
    public void toInvisible() {
        offsetView.setVisibility(GONE);
        darkView.setVisibility(GONE);
        fullView.setVisibility(VISIBLE);
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
        super.onDetachedFromWindow();
    }

    private View inflateFullView() {
        return inflateLayout(R.layout.watch_face_view);
    }

    private View inflateDarkView() {
        return inflateLayout(R.layout.watch_face_view_dimmed);
    }

    private View inflateOffsetView() {
        return inflateLayout(R.layout.watch_face_view_offset);
    }

    private View inflateLayout(@LayoutRes int layoutId) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View rtn = li.inflate(layoutId, this, false);
        addView(rtn);
        return rtn;
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
            this.background = to.background();

        }
    }

    {
        Core.instance().canBeObservedForChangesToColour.addListener(colourUpdates);
    }
}
