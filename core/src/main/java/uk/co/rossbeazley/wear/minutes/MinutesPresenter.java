package uk.co.rossbeazley.wear.minutes;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.ui.Disposable;

public class MinutesPresenter implements Disposable {

    private final CanReceiveMinutesUpdates updateView;
    private final CanBeObserved<CanReceiveMinutesUpdates> canBeObservedForChangesToMinutes;

    public interface MinutesView {
        void showMinutesString(String seconds);
    }

    public MinutesPresenter(final CanBeObserved<CanReceiveMinutesUpdates> canBeObservedForChangesToMinutes, final MinutesView view) {
        this.canBeObservedForChangesToMinutes = canBeObservedForChangesToMinutes;
        updateView = new CanReceiveMinutesUpdates() {
            @Override
            public void minutesUpdate(Sexagesimal to) {
                view.showMinutesString(to.base10String());
            }
        };
        canBeObservedForChangesToMinutes.addListener(updateView);
    }

    @Override
    public void dispose() {
        canBeObservedForChangesToMinutes.removeListener(updateView);
    }

}
