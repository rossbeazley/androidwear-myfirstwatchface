package uk.co.rossbeazley.wear.minutes;

import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.ui.Disposable;

public class MinutesPresenter implements Disposable {

    @Override
    public void dispose() {

    }

    public interface MinutesView {
        void showMinutesString(String seconds);
    }

    public MinutesPresenter(final CanBeObservedForChangesToMinutes canBeObservedForChangesToMinutes, final MinutesView view) {
        canBeObservedForChangesToMinutes.observe(new CanBeObservedForChangesToMinutes.CanReceiveMinutesUpdates() {
            @Override
            public void minutesUpdate(Sexagesimal to) {
                view.showMinutesString(to.base10String());
            }
        });
    }
}
