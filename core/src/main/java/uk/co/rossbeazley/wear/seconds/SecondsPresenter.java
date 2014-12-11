package uk.co.rossbeazley.wear.seconds;

import uk.co.rossbeazley.wear.Sexagesimal;

/**
* Created by beazlr02 on 14/11/2014.
*/
public class SecondsPresenter implements uk.co.rossbeazley.wear.ui.Disposable {

    public final CanBeObservedForChangesToSeconds.CanReceiveSecondsUpdates updateView;
    private final CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds;

    public SecondsPresenter(final CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds, final SecondsView view) {
        this.canBeObservedForChangesToSeconds = canBeObservedForChangesToSeconds;
        updateView = new CanBeObservedForChangesToSeconds.CanReceiveSecondsUpdates() {
            @Override
            public void secondsUpdate(Sexagesimal to) {
                view.showSecondsString(to.base10String());
            }
        };
        canBeObservedForChangesToSeconds.observe(updateView);
    }

    @Override
    public void dispose() {
        canBeObservedForChangesToSeconds.unObserve(updateView);
    }

    public static interface SecondsView {
        void showSecondsString(String seconds);
    }
}
