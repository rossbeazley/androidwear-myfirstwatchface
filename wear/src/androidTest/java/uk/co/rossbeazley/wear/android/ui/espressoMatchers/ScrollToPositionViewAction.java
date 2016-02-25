package uk.co.rossbeazley.wear.android.ui.espressoMatchers;

import android.os.SystemClock;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.view.WearableListView;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.allOf;

public final class ScrollToPositionViewAction implements ViewAction {

    public static ViewAction scrollWearableListToPosition(int i) {
        return new ScrollToPositionViewAction(i);
    }

    private final int position;

    private ScrollToPositionViewAction(int position) {
        this.position = position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Matcher<View> getConstraints() {
        return allOf(isAssignableFrom(WearableListView.class), isDisplayed());
    }

    @Override
    public String getDescription() {
        return "scroll WearableListView to position: " + position;
    }

    @Override
    public void perform(UiController uiController, View view) {
        WearableListView wearableListView = (WearableListView) view;
        MyOnScrollListener listener = new MyOnScrollListener();
        wearableListView.addOnScrollListener(listener);
        wearableListView.smoothScrollToPosition(position);
        while(listener.notFinishedScrolling())
        {
            uiController.loopMainThreadForAtLeast(520);
        }
        uiController.loopMainThreadUntilIdle();

        wearableListView.removeOnScrollListener(listener);
    }

    private static class MyOnScrollListener implements WearableListView.OnScrollListener {
        private int lastScrollState = -1;
        private long MAX_WAIT = SECONDS.toMillis(1);
        private long startTime = SystemClock.currentThreadTimeMillis();

        @Override public void onScroll(int i) { }

        @Override public void onAbsoluteScrollChange(int i) { }

        @Override public void onScrollStateChanged(int i) {
            lastScrollState = i;
        }

        @Override public void onCentralPositionChanged(int i) {  }

        boolean notFinishedScrolling() {
            boolean scrollState = lastScrollState != RecyclerView.SCROLL_STATE_IDLE;
            boolean timedOut = (SystemClock.currentThreadTimeMillis() - startTime) > MAX_WAIT;
            return !timedOut && scrollState;
        }
    }
}
