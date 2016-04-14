package uk.co.rossbeazley.wear.android.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

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

public class ControllerWatchView extends FrameLayout implements WatchView {

    private View fullView;
    private RedrawOnInvalidate redrawOnInvalidate;
    private TimeTick timeTick;
    private WatchFaceService.CanLog logger = new WatchFaceService.CanLog() {
        @Override
        public void log(String msg) {
            // System.out.println("InflatingWatchView " + msg);
        }
    };
    private View date;
    private TextView hours;
    private View mins;
    private View seconds;

    private void initViews() {
        this.fullView = inflateFullView();
        this.date = fullView.findViewById(R.id.date);
        this.hours = (TextView) fullView.findViewById(R.id.watch_time);
        this.mins = fullView.findViewById(R.id.watch_time_mins);
        this.seconds = fullView.findViewById(R.id.watch_time_secs);
    }

    private TimeTick.Tick currentTicks = TimeTick.Tick.NULL;

    public ControllerWatchView(Context context) {
        super(context);
        initViews();
    }

    public ControllerWatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ControllerWatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ControllerWatchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews();
    }

    @Override
    public void toAmbient() {
        hours.setTextColor(0xffaaaaaa);
        date.setVisibility(GONE);
        seconds.setVisibility(GONE);
    }

    @Override
    public void toActive() {
        hours.setTextColor(0xffff0000);
        date.setVisibility(VISIBLE);
        seconds.setVisibility(VISIBLE);
    }

    @Override
    public void toActiveOffset() {
        date.setVisibility(GONE);
        seconds.setVisibility(GONE);
    }

    @Override
    public void toInvisible() {
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
            this.background = to.colour();

        }
    }

    {
        Core.instance().canBeObservedForChangesToColour.addListener(colourUpdates);
    }
}
