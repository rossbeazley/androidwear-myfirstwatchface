package uk.co.rossbeazley.wear.seconds;

import uk.co.rossbeazley.wear.Sexagesimal;

/**
* Created by beazlr02 on 14/11/2014.
*/
public class SecondsPresenter implements uk.co.rossbeazley.wear.ui.Disposable {

    public final CanReceiveSecondsUpdates updateView;
    private final CanBeObservedForChangesToSeconds<CanReceiveSecondsUpdates> canBeObservedForChangesToSeconds;

    public SecondsPresenter(final CanBeObservedForChangesToSeconds<CanReceiveSecondsUpdates> canBeObservedForChangesToSeconds, final SecondsView view) {
        this.canBeObservedForChangesToSeconds = canBeObservedForChangesToSeconds;
        updateView = new CanReceiveSecondsUpdates() {
            @Override
            public void secondsUpdate(Sexagesimal to) {
                view.showSecondsString(to.base10String());
            }
        };
        canBeObservedForChangesToSeconds.addListener(updateView);
    }

    @Override
    public void dispose() {
        canBeObservedForChangesToSeconds.removeListener(updateView);
    }

    public static interface SecondsView {
        void showSecondsString(String seconds);
    }
}
