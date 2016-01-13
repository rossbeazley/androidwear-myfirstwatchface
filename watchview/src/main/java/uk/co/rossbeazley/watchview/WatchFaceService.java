package uk.co.rossbeazley.watchview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.TextView;

public class WatchFaceService extends CanvasWatchFaceService {

    @Override
    public CanvasWatchFaceService.Engine onCreateEngine() {
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
            final WatchView.TimeTick timeTick = createTimeTick();

            watchViewRoot = new WatchViewRoot(context, this, timeTick, this);

            View inflatingWatchView = createWatchView(watchViewRoot);

            watchViewRoot.registerView(inflatingWatchView, this);

            updateView();

//            onSurfaceRedrawNeeded(holder);
        }

        @NonNull
        private WatchView.TimeTick createTimeTick() {
            final Handler handler = new Handler(Looper.myLooper());
            return new HandlerTimeTick(this, handler);
        }


        @NonNull
        private View createWatchView(WatchViewRoot viewRoot) {
            View rtn = null;
            rtn = newViewClass(viewRoot.getContext());
            if(rtn==null) rtn = inflate(viewRoot);
            if(rtn==null) rtn = createErrorView(viewRoot);
            return rtn;
        }

        private View createErrorView(WatchViewRoot viewRoot) {
            TextView textView = new TextView(viewRoot.getContext());
            textView.setText("Could not create view,\nhave you included meta data");
            textView.setLines(2);
            textView.setGravity(Gravity.CENTER);
            return textView;
        }

        private View inflate(WatchViewRoot viewRoot) {
            return new InflatedView(WatchFaceService.this, viewRoot).infate();
        }

        private View newViewClass(Context context) {
            return new ViewClass(WatchFaceService.this, this, context).construct();
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
            log("forceInvalidate");
            invalidate();
            onSurfaceRedrawNeeded(getSurfaceHolder());
        }

        public void log(String msg) {
            //System.out.println("RWF " + System.currentTimeMillis() + ":" + msg);
        }


        @Override
        public void onTimeTick() {
            log("onTimeTick");
            watchViewRoot.timeTick();
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
