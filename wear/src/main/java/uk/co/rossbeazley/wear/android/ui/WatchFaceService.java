package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
        WatchView watchView;
        private WatchViewState watchViewState;

        RotateEngine(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate(SurfaceHolder holder) {
            log("on create");
            super.onCreate(holder);

            watchViewRoot = new WatchViewRoot(context, this);

            View inflatingWatchView = createWatchView(context);
            watchViewRoot.addView(inflatingWatchView);

            watchView = (WatchView) inflatingWatchView;
            watchView.registerInvalidator(this);

            watchViewState = new WatchViewState(watchView);
            watchViewState.toActive();

            updateView();
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
                    watchViewState.toAmbient();
                } else {
                    watchViewRoot.toVisibile(watchView);
                    if (cardsShowing()) {
                        watchViewState.toActiveOffset();
                    } else {
                        watchViewState.toActive();
                    }
                }
            } else {
                watchViewRoot.toInvisible();
                watchViewState.toInvisible();
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
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doInvalidate();
                }
            },3);
        }

        void doInvalidate() {

            super.invalidate();
        }

        @Override
        public void forceInvalidate() {
            log("postInvalidate");
            invalidate();
            onSurfaceRedrawNeeded(getSurfaceHolder());
        }

        public void log(String msg) {
//            System.out.println("RWF " + System.currentTimeMillis() + ":" + msg);
        }


        @Override
        public void onTimeTick() {
            log("onTimeTick");
            updateView();
            watchView.timeTick(Calendar.getInstance());
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
            updateView();
            super.onVisibilityChanged(visible);
        }

        @Override
        public void onDestroy() {
            log("onDestroy");
            watchViewRoot.destroy();
            super.onDestroy();
        }



    }


}
