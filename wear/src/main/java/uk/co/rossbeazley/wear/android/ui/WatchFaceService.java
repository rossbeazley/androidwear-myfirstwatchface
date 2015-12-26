package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.SurfaceHolder;
import android.view.View;

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
                    watchViewState.toAmbient();
                    watchViewRoot.colour = Color.BLACK;
                } else {

                    watchViewRoot.colour = Color.WHITE;
                    if (cardsShowing()) {
                        watchViewState.toOffsetView();
                    } else {
                        watchViewState.toActive();
                    }
                }
            } else {
                watchViewState.toInvisible();
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
