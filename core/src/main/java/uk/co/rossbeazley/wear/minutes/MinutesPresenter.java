package uk.co.rossbeazley.wear.minutes;

import uk.co.rossbeazley.wear.Sexagesimal;

public class MinutesPresenter {

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
