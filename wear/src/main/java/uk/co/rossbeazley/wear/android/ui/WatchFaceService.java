package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.SurfaceHolder;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Core;

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
        public WatchViewRoot watchViewRoot;

        RotateEngine(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate(SurfaceHolder holder) {
            log("on create");
            super.onCreate(holder);

            watchViewRoot = new WatchViewRoot(context, this);
            watchViewRoot.toActive();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            log("on draw");
            watchViewRoot.drawToBounds(canvas, bounds);
            log("done draw");
        }

        @Override
        public void onPeekCardPositionUpdate(Rect rect) {
            log("onPeekCardPositionUpdate");
            watchViewRoot.storeCurrentPeekCardPosition(rect);
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
                    watchViewRoot.toAmbient();
                } else {
                    if (getUnreadCount() > 0) {
                        watchViewRoot.toOffsetView();
                    } else {
                        watchViewRoot.toActive();
                    }
                }
                invalidate();
            } else {
                watchViewRoot.toInvisible();
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
            watchViewRoot.destroy();
        }

    }


}
