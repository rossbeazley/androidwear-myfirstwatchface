package uk.co.rossbeazley.wear.android.ui;

import android.graphics.Color;

import java.util.Calendar;

public class WatchViewState implements WatchView {
    private final WatchView watchView;

    private BaseWatchView currentStrategy;
    private BaseWatchView offset;
    private BaseWatchView active;
    private BaseWatchView ambiet;
    private BaseWatchView invisible;

    public WatchViewState(WatchView watchView) {
        this.watchView = watchView;

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
    public void registerInvalidator(RedrawOnInvalidate redrawOnInvalidate) {
        currentStrategy.registerInvalidator(redrawOnInvalidate);
    }

    @Override
    public void timeTick(Calendar instance) {
        currentStrategy.timeTick(instance);
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
        }

        @Override
        public void toActive() {
            watchViewRoot.currentStrategy = watchViewRoot.active;
            watchViewRoot.watchView.toActive();
        }

        @Override
        public void toActiveOffset() {
            watchViewRoot.currentStrategy = watchViewRoot.offset;
            watchViewRoot.watchView.toActiveOffset();
        }

        @Override
        public void toInvisible() {
            watchViewRoot.currentStrategy = watchViewRoot.invisible;
            watchViewRoot.watchView.toInvisible();
        }

        @Override
        public void registerInvalidator(RedrawOnInvalidate redrawOnInvalidate) {
            watchViewRoot.watchView.registerInvalidator(redrawOnInvalidate);
        }

        @Override
        public void timeTick(Calendar instance) {
            watchViewRoot.watchView.timeTick(instance);
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
