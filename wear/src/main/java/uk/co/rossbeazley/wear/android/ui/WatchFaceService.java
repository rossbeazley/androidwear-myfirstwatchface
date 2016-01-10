package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.Calendar;

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

            watchViewRoot = new WatchViewRoot(context, this, (CanLog)this);

            View inflatingWatchView = createWatchView(context);
            watchViewRoot.registerView(inflatingWatchView, (WatchView.RedrawOnInvalidate)this);

            updateView();
//            onSurfaceRedrawNeeded(holder);
        }


        @NonNull
        private View createWatchView(Context context) {
            return new InflatingWatchView(context);
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
                        watchViewRoot.storeCurrentPeekCardPosition(getPeekCardPosition());
                        watchViewRoot.toActiveOffset();
                    } else {
                        watchViewRoot.toActive();
                    }
                }
            } else {
                watchViewRoot.toInvisible();
                forceInvalidate();
            }
            postInvalidate();
        }

        private boolean cardsShowing() {
            return noneZeroRect(getPeekCardPosition());
        }

        private boolean noneZeroRect(Rect rect) {
            return (rect.top + rect.bottom + rect.left + rect.right) != 0;
        }

        @Override
        public void postInvalidate() {
            log("postInvalidate");
            super.postInvalidate();
        }

        @Override
        public void invalidate() {
            log("invalidate");
            super.invalidate();
        }

        @Override
        public void forceInvalidate() {
            log("postInvalidate");
            invalidate();
            onSurfaceRedrawNeeded(getSurfaceHolder());
        }

        public void log(String msg) {
            //System.out.println("RWF " + System.currentTimeMillis() + ":" + msg);
        }


        @Override
        public void onTimeTick() {
            log("onTimeTick");
            watchViewRoot.timeTick(Calendar.getInstance());
            invalidate();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            log("onAmbientModeChanged");
            updateView();
        }


        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            log("onVisibilityChanged " + visible);
            updateView();
        }

        @Override
        public void onDestroy() {
            log("onDestroy");
            watchViewRoot.destroy();
            super.onDestroy();
        }



    }


}
