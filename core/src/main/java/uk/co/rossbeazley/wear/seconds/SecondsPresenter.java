package uk.co.rossbeazley.wear.seconds;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.Sexagesimal;

/**
* Created by beazlr02 on 14/11/2014.
*/
public class SecondsPresenter implements uk.co.rossbeazley.wear.ui.Disposable {

    public final CanReceiveSecondsUpdates updateView;
    private final CanBeObserved<CanReceiveSecondsUpdates> canBeObserved;

    public SecondsPresenter(final CanBeObserved<CanReceiveSecondsUpdates> canBeObserved, final SecondsView view) {
        this.canBeObserved = canBeObserved;
        updateView = new CanReceiveSecondsUpdates() {
            @Override
            public void secondsUpdate(Sexagesimal to) {
                view.showSecondsString(to.base10String());
            }
        };
        canBeObserved.addListener(updateView);
    }

    @Override
    public void dispose() {
        canBeObserved.removeListener(updateView);
    }

    public static interface SecondsView {
        void showSecondsString(String seconds);
    }
}
