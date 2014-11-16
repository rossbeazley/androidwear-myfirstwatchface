package uk.co.rossbeazley.wear.hours;

import uk.co.rossbeazley.wear.HourBase24;

public class HoursPresenter {
    public HoursPresenter(CanBeObservedForChangesToHours canBeObservedForChangesToHours, final HoursView hoursView) {
        canBeObservedForChangesToHours.observe(new CanBeObservedForChangesToHours.CanReceiveHoursUpdates() {
            @Override
            public void hoursUpdate(HourBase24 hourBase24) {
                hoursView.showHoursString(hourBase24.toBase10TwelveHour());
            }
        });
    }

    public static interface HoursView {
        public void showHoursString(String hours);
    }
}
