package uk.co.rossbeazley.wear.seconds;

import uk.co.rossbeazley.wear.Sexagesimal;

/**
* Created by beazlr02 on 14/11/2014.
*/
public class SecondsPresenter {
    public SecondsPresenter(final CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds, final SecondsView view) {
        canBeObservedForChangesToSeconds.observe(new CanBeObservedForChangesToSeconds.CanReceiveSecondsUpdates() {
            @Override
            public void secondsUpdate(Sexagesimal to) {
                view.showSecondsString(to.base10String());
            }
        });
    }

    public static interface SecondsView {
        void showSecondsString(String seconds);
    }
}
