package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.minutes.CanReceiveMinutesUpdates;
import uk.co.rossbeazley.wear.rotation.CanReceiveRotationUpdates;
import uk.co.rossbeazley.wear.rotation.Orientation;
import uk.co.rossbeazley.wear.seconds.CanReceiveSecondsUpdates;
import uk.co.rossbeazley.wear.ui.Disposable;

public class WatchFaceService extends CanvasWatchFaceService {

    @Override
    public Engine onCreateEngine() {
        return new RotateEngine(getApplicationContext());
    }

    public interface CanInvalidateWatchFaceView {
        void postInvalidate();
    }

    class RotateEngine extends CanvasWatchFaceService.Engine implements WatchFaceService.CanInvalidateWatchFaceView {

        private final Context context;
        public WatchView watchView;
        private Rect currentPeekCardPosition;

        RotateEngine(Context context) {

            this.context = context;
        }

        @Override
        public void onCreate(SurfaceHolder holder) {
            log("on create");
            super.onCreate(holder);

            watchView = new WatchView(context, this);
            watchView.toActive();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            log("on draw");
            super.onDraw(canvas, bounds);
            Rect drawToBounds = adjustDrawingAreaForAnyNotificationCards(bounds);
            watchView.drawToBounds(canvas, drawToBounds);
            log("done draw");
        }

        @Override
        public void onPeekCardPositionUpdate(Rect rect) {
            log("onPeekCardPositionUpdate");
            super.onPeekCardPositionUpdate(rect);
            currentPeekCardPosition = rect;
            updateView();
        }


        @Override
        public void onUnreadCountChanged(int count) {
            log("onUnreadCountChanged");
            updateView();
        }

        private void updateView() {
            log("updateView");
            if(isVisible()) {
                if (isInAmbientMode()) {
                    watchView.toAmbient();
                } else {
                    if (getUnreadCount() > 0) {
                        watchView.toOffsetView();
                    } else {
                        watchView.toActive();
                    }
                }
                invalidate();
            } else {
                watchView.toInvisible();
            }
        }

        @Override
        public void invalidate() {
            log("invalidate");
            super.invalidate();
        }

        @Override
        public void postInvalidate() {
            log("postInvalidate");
            super.postInvalidate();
        }

        private void log(String msg) {
            //System.out.println("RWF " + msg);
        }

        @NonNull
        private Rect adjustDrawingAreaForAnyNotificationCards(Rect bounds) {
            Rect peekCardPosition = currentPeekCardPosition;
            if(peekCardPosition!=null) bounds.bottom = bounds.bottom + (peekCardPosition.top - peekCardPosition.bottom);
            return bounds;
        }

        @Override
        public void onTimeTick() {
            log("onTimeTick");
            super.onTimeTick();
            Core.instance().canBeTicked.tick(Calendar.getInstance());
            invalidate();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            log("onAmbientModeChanged");
            super.onAmbientModeChanged(inAmbientMode);
            updateView();
        }


        @Override
        public void onVisibilityChanged(boolean visible) {
            log("onVisibilityChanged " + visible);
            super.onVisibilityChanged(visible);
            updateView();
        }

        @Override
        public void onDestroy() {
            log("onDestroy");
            super.onDestroy();
            watchView.destroy();
        }


    }


    private static class WatchView {

        private int color;
        private boolean ambient;
        private boolean offset;
        private boolean active;
        private Context context;
        private final CanInvalidateWatchFaceView canInvalidateWatchFaceView;

        public WatchView(Context context, CanInvalidateWatchFaceView canInvalidateWatchFaceView) {
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
            Core.instance().canBeObservedForChangesToRotation.removeListener(invalidateViewWhenRotationChanges);
        }

        public void toInvisible() {
            log("toInvisible");
            Main.instance().tickTock.stop();
            ambient = false;
            active = false;
            offset = false;

            tearDownView();
        }
    }

}
