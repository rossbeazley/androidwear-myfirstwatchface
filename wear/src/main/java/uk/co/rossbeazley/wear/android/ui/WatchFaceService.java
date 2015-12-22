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

            watchViewRoot = new WatchViewRoot(context, this, this);
            watchViewRoot.toActive();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            log("on draw");
            watchViewRoot.drawToBounds(canvas, bounds);
            //log(getPeekCardPosition().toString());
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
                invalidate();
            } else {
                watchViewRoot.toInvisible();
            }
        }

        private boolean cardsShowing() {
            return noneZeroRect(getPeekCardPosition());
//            return getUnreadCount() > 0 || noneZeroRect(getPeekCardPosition());
        }

        private boolean noneZeroRect(Rect rect) {
            return rect.top!=0 || rect.bottom!=0 || rect.left!=0 || rect.right!=0;
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
