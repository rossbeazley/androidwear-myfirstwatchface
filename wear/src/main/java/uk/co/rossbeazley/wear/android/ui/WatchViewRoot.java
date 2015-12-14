package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.minutes.CanReceiveMinutesUpdates;
import uk.co.rossbeazley.wear.rotation.CanReceiveRotationUpdates;
import uk.co.rossbeazley.wear.rotation.Orientation;
import uk.co.rossbeazley.wear.seconds.CanReceiveSecondsUpdates;
import uk.co.rossbeazley.wear.ui.Disposable;

class WatchViewRoot {

    private int color;
    private boolean ambient;
    private boolean offset;
    private boolean active;
    private Context context;
    private final WatchFaceService.CanInvalidateWatchFaceView canInvalidateWatchFaceView;

    public WatchViewRoot(Context context, WatchFaceService.CanInvalidateWatchFaceView canInvalidateWatchFaceView) {
        this.context = context;
        this.canInvalidateWatchFaceView = canInvalidateWatchFaceView;

        color = Color.WHITE;
    }

    public void drawToBounds(Canvas canvas, Rect bounds) {
        if(watchFaceView==null) return;

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

    private void log(String msg) {
        //System.out.println("RWF " + msg);
    }

    public void toAmbient() {
        log("maybeToAmbient");
        if (ambient) return;

        log("entered ambient");

        ambient = true;
        active = false;
        offset = false;

        color = Color.BLACK;
        tearDownView();
        inflatePassiveView();

        Main.instance().tickTock.stop();
    }

    public void toActive() {
        log("maybeToActive");
        if (active) return;

        log("entered active");
        ambient = false;
        active = true;
        offset = false;

        color = Color.WHITE;
        tearDownView();
        inflateActiveView();

        Main.instance().tickTock.start();
        log("done toActive");
    }

    public void toOffsetView() {
        log("maybeToOffsetView");
        if (offset) return;

        log("entered offsetView");
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

    public final CanReceiveRotationUpdates invalidateViewWhenRotationChanges = new CanReceiveRotationUpdates() {
        @Override
        public void rotationUpdate(Orientation to) {
            canInvalidateWatchFaceView.postInvalidate();
        }
    };

    private void tearDownView() {
        log("tearDownView");
        if (watchFaceView != null) {
            ((Disposable) watchFaceView).dispose();
            watchFaceView=null;
            log("disposed");
        }
        Core.instance().canBeObservedForChangesToMinutes.removeListener(invalidateViewWhenMinutesChange);
        Core.instance().canBeObservedForChangesToSeconds.removeListener(invalidateViewWhenSecondsChange);
    }


    private void inflateActiveView() {
        log("inflateActiveView");
        LayoutInflater li = LayoutInflater.from(context);
        watchFaceView = li.inflate(R.layout.watch_face_view, null);
        Core.instance().canBeObservedForChangesToSeconds.addListener(invalidateViewWhenSecondsChange);

    }

    private void inflatePassiveView() {
        log("inflatePassiveView");
        LayoutInflater li = LayoutInflater.from(context);
        watchFaceView = li.inflate(R.layout.watch_face_view_dimmed, null);
        Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
    }

    private void inflateOffsetView() {
        log("inflateOffsetView");
        LayoutInflater li = LayoutInflater.from(context);
        watchFaceView = li.inflate(R.layout.watch_face_view_offset, null);
        Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
    }


    public void destroy() {
        log("destroy");
        tearDownView();
        //Core.instance().canBeObservedForChangesToRotation.removeListener(invalidateViewWhenRotationChanges);
    }

    public void toInvisible() {
        log("toInvisible");
        Main.instance().tickTock.stop();
        ambient = false;
        active = false;
        offset = false;

        //  tearDownView();
    }
}
