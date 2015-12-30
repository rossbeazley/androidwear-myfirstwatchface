package uk.co.rossbeazley.wear.android.ui;

import java.util.Calendar;

class WatchViewState implements WatchView {
    private final WatchView watchView;

    private WatchView currentStrategy;
    private WatchView offset;
    private WatchView active;
    private WatchView ambiet;
    private WatchView invisible;

    public WatchViewState(WatchView watchView) {
        this.watchView = watchView;

        currentStrategy = invisible = new InvisibleWatchView(this);
        active = new ActiveWatchView(this);
        offset = new OffsetWatchView(this);
        ambiet = new AmbientWatchView(this);

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
    }

    @Override
    public void timeTick(Calendar instance) {

    }

    @Override
    public int background() {
        return 0;
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

        }

        @Override
        public void timeTick(Calendar instance) {

        }

        @Override
        public int background() {
            return 0;
        }
    }

    private static class ActiveWatchView extends BaseWatchView {

        public ActiveWatchView(WatchViewState watchViewRoot) {
            super(watchViewRoot);
        }

        @Override
        public void toActive() {

        }

        @Override
        public void timeTick(Calendar instance) {

        }

        @Override
        public int background() {
            return 0;
        }
    }

    private static class InvisibleWatchView extends BaseWatchView {

        public InvisibleWatchView(WatchViewState watchViewRoot) {
            super(watchViewRoot);
        }

        @Override
        public void toAmbient() {
        }

        @Override
        public void timeTick(Calendar instance) {

        }

        @Override
        public int background() {
            return 0;
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
        public void timeTick(Calendar instance) {

        }

        @Override
        public int background() {
            return 0;
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
        public void timeTick(Calendar instance) {

        }

        @Override
        public int background() {
            return 0;
        }
    }

}
