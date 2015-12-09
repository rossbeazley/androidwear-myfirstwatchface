package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.LayoutInflater;
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

    class RotateEngine extends CanvasWatchFaceService.Engine {

        private final Context context;
        public final WatchView watchView;

        RotateEngine(Context context) {
            this.context = context;
            watchView = new WatchView();
            watchView.toActive();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            super.onDraw(canvas, bounds);
            Rect drawToBounds = adjustDrawingAreaForAnyNotificationCards(bounds);
            watchView.drawToBounds(canvas, drawToBounds);
        }

        @Override
        public void onUnreadCountChanged(int count) {

            updateView();
        }

        private void updateView() {
            if (isInAmbientMode()) {
                watchView.toAmbient();
                Main.instance().tickTock.stop();
            } else {
                if (getUnreadCount() > 0) {
                    watchView.offsetView();
                    Main.instance().tickTock.stop();
                } else {
                    watchView.toActive();
                    Main.instance().tickTock.start();
                }
            }
        }

        @NonNull
        private Rect adjustDrawingAreaForAnyNotificationCards(Rect bounds) {
            Rect peekCardPosition = getPeekCardPosition();
            Rect drawToBounds = new Rect(bounds);
            drawToBounds.bottom = drawToBounds.bottom + (peekCardPosition.top - peekCardPosition.bottom);
            return drawToBounds;
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
           updateView();
        }


        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            updateView();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            watchView.destroy();

        }

        private class WatchView {

            private int color;

            public WatchView() {

                color = Color.WHITE;
            }

            public void drawToBounds(Canvas canvas, Rect bounds) {
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

            public void toAmbient() {

                color = Color.BLACK;
                tearDownView();
                inflatePassiveView(context);
            }

            public void toActive() {

                color = Color.WHITE;
                tearDownView();
                inflateActiveView(context);
            }


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

            private void tearDownView() {
                if (watchFaceView != null) {
                    ((Disposable) watchFaceView).dispose();
                }
                Core.instance().canBeObservedForChangesToMinutes.removeListener(invalidateViewWhenMinutesChange);
                Core.instance().canBeObservedForChangesToSeconds.removeListener(invalidateViewWhenSecondsChange);
            }


            private void inflateActiveView(Context watchFaceService) {
                LayoutInflater li = (LayoutInflater) watchFaceService.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                watchFaceView = (ViewGroup) li.inflate(R.layout.watch_face_view, null);
                Core.instance().canBeObservedForChangesToSeconds.addListener(invalidateViewWhenSecondsChange);

            }

            private void inflatePassiveView(Context watchFaceService) {
                LayoutInflater li = (LayoutInflater) watchFaceService.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                watchFaceView = (ViewGroup) li.inflate(R.layout.watch_face_view_dimmed, null);
                Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
            }

            public void destroy() {
                tearDownView();
                Core.instance().canBeObservedForChangesToRotation.removeListener(invalidateViewWhenRotationChanges);
            }

            public void offsetView() {
                color = Color.WHITE;
                tearDownView();
                inflateOffsetView(context);

            }

            private void inflateOffsetView(Context context) {
                LayoutInflater li = LayoutInflater.from(context);
                watchFaceView = (ViewGroup) li.inflate(R.layout.watch_face_view_offset, null);
                Core.instance().canBeObservedForChangesToMinutes.addListener(invalidateViewWhenMinutesChange);
            }
        }
    }
}
