package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.wearable.watchface.CanvasWatchFaceService;

/**
 * Created by beazlr02 on 06/01/2015.
 *
 *
 */
public class WatchFaceService extends CanvasWatchFaceService {

    @Override
    public Engine onCreateEngine() {
        return new RotateEngine(getApplicationContext());
    }

    class RotateEngine extends CanvasWatchFaceService.Engine {

        public final WatchFaceView watchFaceView;

        RotateEngine(Context watchFaceService) {
            watchFaceView = new WatchFaceView(watchFaceService);
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            super.onDraw(canvas, bounds);
            watchFaceView.measure(bounds.width(),bounds.height());
            watchFaceView.draw(canvas);
        }
    }
}
