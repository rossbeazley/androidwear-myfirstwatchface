package uk.co.rossbeazley.watchview;

import android.graphics.Color;

import java.util.concurrent.TimeUnit;

class WatchViewState implements WatchView {
    private final WatchView watchView;
    private final TimeTick timeTick;

    private BaseWatchView currentStrategy;
    private BaseWatchView offset;
    private BaseWatchView active;
    private BaseWatchView ambiet;
    private BaseWatchView invisible;

    WatchViewState(WatchView watchView, TimeTick timeTick) {
        this.watchView = watchView;
        this.timeTick = timeTick;

        currentStrategy = invisible = new InvisibleWatchView(this);
        active = new ActiveWatchView(this);
        offset = new OffsetWatchView(this);
        ambiet = new AmbientWatchView(this);

    }

    @Override
    public String toString() {
        return currentStrategy.getClass().getSimpleName();
    }

    public void toAmbient() {
        currentStrategy.toAmbient();
    }

    public void toActive() {
        currentStrategy.toActive();
    }

    public void toActiveOffset() {
        currentStrategy.toActiveOffset();
    }

    public void toInvisible() {
        currentStrategy.toInvisible();
    }

    @Override
    public void registerServices(RedrawOnInvalidate redrawOnInvalidate, TimeTick timeTick) {
        currentStrategy.registerServices(redrawOnInvalidate, timeTick);
    }

    @Override
    public void timeTick(long duration, TimeUnit timeUnit) {
        currentStrategy.timeTick(duration, timeUnit);
    }

    @Override
    public int background() {
        return currentStrategy.background();
    }

    public boolean isVisible() {
        return currentStrategy.isVisibile();
    }


    private static class BaseWatchView implements WatchView {
        protected final WatchViewState watchViewRoot;

        public BaseWatchView(WatchViewState watchViewRoot) {
            this.watchViewRoot = watchViewRoot;
        }

        @Override
        public void toAmbient() {
            watchViewRoot.currentStrategy = watchViewRoot.ambiet;
            watchViewRoot.watchView.toAmbient();
            watchViewRoot.timeTick.stop();
        }

        @Override
        public void toActive() {
            watchViewRoot.currentStrategy = watchViewRoot.active;
            watchViewRoot.watchView.toActive();
            watchViewRoot.timeTick.restart();
        }

        @Override
        public void toActiveOffset() {
            watchViewRoot.currentStrategy = watchViewRoot.offset;
            watchViewRoot.watchView.toActiveOffset();
            watchViewRoot.timeTick.restart();
        }

        @Override
        public void toInvisible() {
            watchViewRoot.currentStrategy = watchViewRoot.invisible;
            watchViewRoot.watchView.toInvisible();
            watchViewRoot.timeTick.stop();
        }

        @Override
        public void registerServices(RedrawOnInvalidate redrawOnInvalidate, TimeTick timeTick) {
            watchViewRoot.watchView.registerServices(redrawOnInvalidate, timeTick);
        }

        @Override
        public void timeTick(long duration, TimeUnit timeUnit) {
            watchViewRoot.watchView.timeTick(duration, timeUnit);
        }

        @Override
        public int background() {
            return watchViewRoot.watchView.background();
        }

        public boolean isVisibile() {
            return true;
        }
    }

    private static class ActiveWatchView extends BaseWatchView {

        public ActiveWatchView(WatchViewState watchViewRoot) {
            super(watchViewRoot);
        }

        @Override
        public void toActive() { }

        @Override
        public boolean isVisibile() {
            return true;
        }
    }

    private static class InvisibleWatchView extends BaseWatchView {

        public InvisibleWatchView(WatchViewState watchViewRoot) {
            super(watchViewRoot);
        }

        @Override
        public void toInvisible() { }

        @Override
        public boolean isVisibile() {
            return false;
        }

        @Override
        public int background() {
            return Color.BLACK;
        }
    }

    private static class OffsetWatchView extends BaseWatchView {
        public OffsetWatchView(WatchViewState watchViewRoot) {
            super(watchViewRoot);
        }

        @Override
        public void toActiveOffset() {

        }

        @Override
        public boolean isVisibile() {
            return true;
        }
    }

    private static class AmbientWatchView extends BaseWatchView {
        public AmbientWatchView(WatchViewState watchViewRoot) {
            super(watchViewRoot);
        }

        @Override
        public void toAmbient() {

        }

        @Override
        public boolean isVisibile() {
            return true;
        }

        @Override
        public int background() {
            return Color.BLACK;
        }
    }

}
