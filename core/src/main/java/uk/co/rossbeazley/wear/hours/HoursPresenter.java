package uk.co.rossbeazley.wear.hours;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.HourBase24;
import uk.co.rossbeazley.wear.ui.Disposable;

public class HoursPresenter implements Disposable {

    public final CanReceiveHoursUpdates updateView;
    private final CanBeObserved<CanReceiveHoursUpdates> canBeObservedForChangesToHours;

    public HoursPresenter(CanBeObserved<CanReceiveHoursUpdates> canBeObservedForChangesToHours, final HoursView hoursView) {
        this.canBeObservedForChangesToHours = canBeObservedForChangesToHours;
        updateView = new CanReceiveHoursUpdates() {
            @Override
            public void hoursUpdate(HourBase24 hourBase24) {
                hoursView.showHoursString(hourBase24.toBase10TwelveHour());
            }
        };
        canBeObservedForChangesToHours.addListener(updateView);
    }

    @Override
    public void dispose() {
        canBeObservedForChangesToHours.removeListener(updateView);
    }

    public static interface HoursView {
        public void showHoursString(String hours);
    }
}
