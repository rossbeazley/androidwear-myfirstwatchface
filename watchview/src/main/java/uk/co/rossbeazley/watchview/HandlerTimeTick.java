package uk.co.rossbeazley.watchview;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

class HandlerTimeTick implements WatchView.TimeTick {
    private WatchFaceService.RotateEngine rotateEngine;
    private final Handler handler;
    private Tick currentTicks;

    public HandlerTimeTick(WatchFaceService.RotateEngine rotateEngine, Handler handler) {
        this.rotateEngine = rotateEngine;
        this.handler = handler;
        this.currentTicks = Tick.NULL;
    }

    @Override
    public Tick scheduleTicks(final long period, final TimeUnit timeUnit) {

        Tick newTick = new Tick() {

            final Runnable tick = new Runnable() {
                @Override
                public void run() {
                    HandlerTimeTick.this.handler.postDelayed(this, timeUnit.toMillis(period));
                    HandlerTimeTick.this.rotateEngine.onTimeTick();
                }
            };

            @Override
            public void stop() {
                HandlerTimeTick.this.handler.removeCallbacks(tick);
            }

            @Override
            public void start() {
                HandlerTimeTick.this.currentTicks.stop();
                tick.run();
                HandlerTimeTick.this.currentTicks = this;
            }
        };

        newTick.start();

        HandlerTimeTick.this.currentTicks = newTick;

        return HandlerTimeTick.this.currentTicks;
    }

    @Override
    public void stop() {
        this.currentTicks.stop();
    }

    @Override
    public void restart() {
        currentTicks.start();
    }
}
