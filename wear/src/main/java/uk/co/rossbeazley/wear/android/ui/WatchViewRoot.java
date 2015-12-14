package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.minutes.CanReceiveMinutesUpdates;
import uk.co.rossbeazley.wear.seconds.CanReceiveSecondsUpdates;
import uk.co.rossbeazley.wear.ui.Disposable;

class WatchViewRoot {

    private int color;
    private boolean ambient;
    private boolean offset;
    private boolean active;
    private Context context;
    private final WatchFaceService.CanInvalidateWatchFaceView canInvalidateWatchFaceView;
    private final WatchFaceService.CanLog logger;
    private Rect currentPeekCardPosition;

    public WatchViewRoot(Context context, WatchFaceService.CanInvalidateWatchFaceView canInvalidateWatchFaceView, WatchFaceService.CanLog logger) {
        this.context = context;
        this.canInvalidateWatchFaceView = canInvalidateWatchFaceView;
        this.logger = logger;

        color = Color.WHITE;
    }


    @NonNull
    private static Rect adjustDrawingAreaForAnyNotificationCards(Rect bounds, Rect peekCardPosition) {
        Rect rtn = new Rect();
        rtn.set(bounds);
        if(peekCardPosition!=null) rtn.bottom = bounds.bottom + (peekCardPosition.top - peekCardPosition.bottom);
        return rtn;
    }

    public void drawToBounds(Canvas canvas, Rect bounds) {
        if(watchFaceView==null) return;

        if(offset) {
            bounds = adjustDrawingAreaForAnyNotificationCards(bounds, currentPeekCardPosition);
        }

        canvas.drawColor(color);

        int widthSpec = View.MeasureSpec.makeMeasureSpec(bounds.width(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(bounds.height(), View.MeasureSpec.EXACTLY);
        watchFaceView.measure(widthSpec, heightSpec);
        watchFaceView.layout(0, 0, bounds.width(), bounds.height());

        Matrix matrix = watchFaceView.getMatrix();
        canvas.save();
        canvas.setMatrix(matrix);
        watchFaceView.draw(canvas);
        canvas.restore();
    }

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

    public View watchFaceView = null;

    public final CanReceiveSecondsUpdates invalidateViewWhenSecondsChange = new CanReceiveSecondsUpdates() {
        @Override
        public void secondsUpdate(Sexagesimal to) {
            canInvalidateWatchFaceView.postInvalidate();
        }
    };

    public final CanReceiveMinutesUpdates invalidateViewWhenMinutesChange = new CanReceiveMinutesUpdates() {
        @Override
        public void minutesUpdate(Sexagesimal to) {
            canInvalidateWatchFaceView.postInvalidate();
        }
    };

    private void tearDownView() {
        logger.log("tearDownView");
        if (watchFaceView != null) {
            ((Disposable) watchFaceView).dispose();
            watchFaceView=null;
            logger.log("disposed");
        }
        Core.instance().canBeObservedForChangesToMinutes.removeListener(invalidateViewWhenMinutesChange);
        Core.instance().canBeObservedForChangesToSeconds.removeListener(invalidateViewWhenSecondsChange);
    }


    private void inflateActiveView() {
        logger.log("inflateActiveView");
        LayoutInflater li = LayoutInflater.from(context);
        watchFaceView = li.inflate(R.layout.watch_face_view, null);
        Core.instance().canBeObservedForChangesToSeconds.addListener(invalidateViewWhenSecondsChange);

    }

    private void inflatePassiveView() {
        logger.log("inflatePassiveView");
        LayoutInflater li = LayoutInflater.from(context);
        watchFaceView = li.inflate(R.layout.watch_face_view_dimmed, null);
        Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
    }

    private void inflateOffsetView() {
        logger.log("inflateOffsetView");
        LayoutInflater li = LayoutInflater.from(context);
        watchFaceView = li.inflate(R.layout.watch_face_view_offset, null);
        Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
    }


    public void destroy() {
        logger.log("destroy");
        tearDownView();
    }

    public void toInvisible() {
        logger.log("toInvisible");
        Main.instance().tickTock.stop();
        ambient = false;
        active = false;
        offset = false;
    }

    public void storeCurrentPeekCardPosition(Rect currentPeekCardPosition) {
        this.currentPeekCardPosition = currentPeekCardPosition;
    }
}
