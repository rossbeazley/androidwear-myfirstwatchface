package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Core;
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

    class RotateEngine extends CanvasWatchFaceService.Engine {

        private final Context context;
        public ViewGroup watchFaceView = null;
        public final CanReceiveSecondsUpdates invalidateViewWhenSecondsChange = new CanReceiveSecondsUpdates() {
            @Override
            public void secondsUpdate(Sexagesimal to) {
                invalidate();
            }
        };
        public final CanReceiveMinutesUpdates invalidateViewWhenMinutesChange = new CanReceiveMinutesUpdates() {
            @Override
            public void minutesUpdate(Sexagesimal to) {
                invalidate();
            }
        };
        public final CanReceiveRotationUpdates invalidateViewWhenRotationChanges = new CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                invalidate();
            }
        };

        RotateEngine(Context context) {
            this.context = context;
            inflateActiveView(context);
            Core.instance().canBeObservedForChangesToRotation.addListener(invalidateViewWhenRotationChanges);
        }

        private void inflateActiveView(Context watchFaceService) {
            LayoutInflater li = (LayoutInflater)watchFaceService.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            watchFaceView = (ViewGroup) li.inflate(R.layout.watch_face_view,null);
            Core.instance().canBeObservedForChangesToSeconds.addListener(invalidateViewWhenSecondsChange);

        }

        private void inflatePassiveView(Context watchFaceService) {
            LayoutInflater li = (LayoutInflater)watchFaceService.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            watchFaceView = (ViewGroup) li.inflate(R.layout.watch_face_view_dimmed,null);
            Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
        }


        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            super.onDraw(canvas, bounds);
            int widthSpec = View.MeasureSpec.makeMeasureSpec(bounds.width(), View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(bounds.height(), View.MeasureSpec.EXACTLY);
            watchFaceView.measure(widthSpec, heightSpec);
            watchFaceView.layout(0, 0, bounds.width(), bounds.height());
            Matrix matrix = watchFaceView.getMatrix();
            canvas.save();
            canvas.setMatrix(matrix);
            watchFaceView.draw(canvas);
            canvas.restore();
            System.out.println("ONDRAW" + bounds.width() + ":" + bounds.height() + ";" + widthSpec + ":" + heightSpec);
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            Core.instance().canBeTicked.tick(Calendar.getInstance());
            invalidate();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            if(inAmbientMode) {
                tearDownView();
                inflatePassiveView(context);
            } else {
                tearDownView();
                inflateActiveView(context);
            }
        }

        private void tearDownView() {
            if (watchFaceView != null) {
                ((Disposable)watchFaceView).dispose();
            }
            Core.instance().canBeObservedForChangesToMinutes.removeListener(invalidateViewWhenMinutesChange);
            Core.instance().canBeObservedForChangesToSeconds.removeListener(invalidateViewWhenSecondsChange);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            tearDownView();
            Core.instance().canBeObservedForChangesToRotation.removeListener(invalidateViewWhenRotationChanges);
        }
    }
}
