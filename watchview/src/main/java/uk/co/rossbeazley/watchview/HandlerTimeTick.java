package uk.co.rossbeazley.watchview;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

class HandlerTimeTick implements WatchView.TimeTick {
    private WatchFaceService.RotateEngine rotateEngine;
    private final Handler handler;

    public HandlerTimeTick(WatchFaceService.RotateEngine rotateEngine, Handler handler) {
        this.rotateEngine = rotateEngine;
        this.handler = handler;
    }

    @Override
    public Cancelable scheduleTicks(final long period, final TimeUnit timeUnit) {
        final Runnable tick = new Runnable() {
            @Override
            public void run() {
                rotateEngine.onTimeTick();
                handler.postDelayed(this, timeUnit.toMillis(period));
            }
        };

        handler.post(tick);

        return new Cancelable() {
            @Override
            public void cancel() {
                handler.removeCallbacks(tick);
            }
        };
    }
}
