package uk.co.rossbeazley.wear.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.LayoutInflater;
import android.view.View;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.seconds.CanReceiveSecondsUpdates;

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
            LayoutInflater li = (LayoutInflater)watchFaceService.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            watchFaceView = (WatchFaceView) li.inflate(R.layout.watch_face_view,null);
//            Core.instance().canBeObservedForChangesToSeconds.addListener(new CanReceiveSecondsUpdates() {
//                @Override
//                public void secondsUpdate(Sexagesimal to) {
//                    invalidate();
//                }
//            });
            watchFaceView.attachService(new WatchFaceView.ToInvalidate() {
                @Override
                public void invalidate() {
                    RotateEngine.this.invalidate();
                }
            });
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            super.onDraw(canvas, bounds);
            int widthSpec = View.MeasureSpec.makeMeasureSpec(bounds.width(), View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(bounds.height(), View.MeasureSpec.EXACTLY);
            watchFaceView.measure(widthSpec, heightSpec);
            watchFaceView.layout(0,0,bounds.width(),bounds.height());
            watchFaceView.draw(canvas);
            System.out.println("ONDRAW" + bounds.width() + ":" + bounds.height() + ";" + widthSpec + ":" + heightSpec);
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
        }
    }
}
