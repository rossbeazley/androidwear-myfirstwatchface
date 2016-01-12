package uk.co.rossbeazley.watchview;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public interface WatchView {
    void toAmbient();

    void toActive();

    void toActiveOffset();

    void toInvisible();

    void registerServices(RedrawOnInvalidate redrawOnInvalidate, TimeTick timeTick);

    void timeTick(Calendar instance);

    int background();

    interface RedrawOnInvalidate {
        void forceInvalidate();
        void postInvalidate();
    }

    interface TimeTick {
        Cancelable scheduleTicks(long period, TimeUnit timeUnit);

        interface Cancelable {
            void cancel();


            Cancelable NULL = new Cancelable() {
                @Override
                public void cancel() {                }
            };

        }
    }
}
