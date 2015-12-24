package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.SurfaceHolder;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Core;

public class WatchFaceService extends CanvasWatchFaceService {

    @Override
    public Engine onCreateEngine() {
        return new RotateEngine(getApplicationContext());
    }

    public interface CanLog {
        void log(String msg);
    }

    class RotateEngine extends CanvasWatchFaceService.Engine implements WatchView.RedrawOnInvalidate, WatchFaceService.CanLog {

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

            updateView();
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
                    if (cardsShowing()) {
                        watchViewRoot.toOffsetView();
                    } else {
                        watchViewRoot.toActive();
                    }
                }
            } else {
                watchViewRoot.toInvisible();
            }
            invalidate();
        }

        private boolean cardsShowing() {
            return noneZeroRect(getPeekCardPosition());
        }

        private boolean noneZeroRect(Rect rect) {
            return (rect.top + rect.bottom + rect.left + rect.right) != 0;
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

        public void log(String msg) {
            //System.out.println("RWF " + msg);
        }


        @Override
        public void onTimeTick() {
            log("onTimeTick");
            updateView();
            Core.instance().canBeTicked.tick(Calendar.getInstance()); //TODO get rid of this line, or determine if I should forward the message
            onSurfaceRedrawNeeded(getSurfaceHolder());
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            log("onAmbientModeChanged");
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
