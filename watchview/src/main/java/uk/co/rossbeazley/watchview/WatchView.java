package uk.co.rossbeazley.watchview;

import android.support.annotation.ColorInt;

import java.util.concurrent.TimeUnit;

public interface WatchView {
    void toAmbient();

    void toActive();

    void toActiveOffset();

    void toInvisible();

    void registerServices(RedrawOnInvalidate redrawOnInvalidate, TimeTick timeTick);

    void timeTick(long duration, TimeUnit timeUnit);

    @ColorInt
    int background();

    interface RedrawOnInvalidate {
        void forceInvalidate();
        void postInvalidate();
    }

    interface TimeTick {
        /**
         * If you schedule any ticks, the current ticker is canceled before the new one is started.
         * @param period
         * @param timeUnit
         * @return
         */
        Tick scheduleTicks(long period, TimeUnit timeUnit);

        void stop();

        void restart();

        interface Tick {
            void stop();

            Tick NULL = new Tick() {
                @Override
                public void stop() {}

                @Override
                public void start() {}
            };

            void start();
        }
    }
}
