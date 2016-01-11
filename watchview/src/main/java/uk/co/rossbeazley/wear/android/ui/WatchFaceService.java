package uk.co.rossbeazley.wear.android.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.service.wallpaper.WallpaperService;
import android.support.annotation.NonNull;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.util.Calendar;

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

            watchViewRoot = new WatchViewRoot(context, this, (CanLog)this);

            View inflatingWatchView = createWatchView(watchViewRoot);

            watchViewRoot.registerView(inflatingWatchView, (WatchView.RedrawOnInvalidate)this);

            updateView();

//            onSurfaceRedrawNeeded(holder);
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
            View rtn = null;
            try {
                ServiceInfo applicationInfo = getPackageManager().getServiceInfo(new ComponentName(viewRoot.getContext(), WatchFaceService.class), PackageManager.GET_META_DATA);
                Bundle metaData = applicationInfo.metaData;
                System.out.println("Going to try inflating");

                int layoutId = metaData.getInt("watchFaceViewLayout");
                System.out.println("Going to inflate " + layoutId);
                LayoutInflater layoutInflater = LayoutInflater.from(viewRoot.getContext());
                rtn = layoutInflater.inflate(layoutId, viewRoot, false);
            } catch (Exception e) {

            }

            return rtn;
        }

        private View newViewClass(Context context) {
            View rtn = null;
            try {
                ServiceInfo applicationInfo = getPackageManager().getServiceInfo(new ComponentName(context,WatchFaceService.class), PackageManager.GET_META_DATA);
                Bundle metaData = applicationInfo.metaData;
                String viewClass = metaData.getString("watchFaceViewClass");
                if(viewClass==null) return rtn;

                System.out.println("Going to construct" + viewClass);
                Class<?> aClass = Class.forName(viewClass);
                Class<Context> contextType = Context.class;
                Constructor<?> constructor = aClass.getConstructor(contextType);
                rtn = (View) constructor.newInstance(context);
                log("Found" + aClass.getSimpleName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return rtn;
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
