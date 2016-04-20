package uk.co.rossbeazley.wear.hours;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.colour.CanReceiveColourUpdates;
import uk.co.rossbeazley.wear.colour.Colours;
import uk.co.rossbeazley.wear.ui.Disposable;

public class HoursPresenter implements Disposable {
    private final CanBeObserved<CanReceiveHoursUpdates> canBeObservedForChangesToHours;
    public final CanReceiveHoursUpdates updateView;
    private final CanReceiveColourUpdates canReceiveSecondsUpdates;
    private final CanBeObserved<CanReceiveColourUpdates> hoursColour;

    public HoursPresenter(CanBeObserved<CanReceiveHoursUpdates> canBeObservedForChangesToHours, final HoursView hoursView, CanBeObserved<CanReceiveColourUpdates> hoursColour) {
        this.canBeObservedForChangesToHours = canBeObservedForChangesToHours;
        updateView = new CanReceiveHoursUpdates() {
            @Override
            public void hoursUpdate(HourBase24 hourBase24) {
                hoursView.showHoursString(hourBase24.toBase10TwelveHour());
            }
        };
        canBeObservedForChangesToHours.addListener(updateView);
        canReceiveSecondsUpdates = new CanReceiveColourUpdates() {
            @Override
            public void colourUpdate(Colours to) {
                hoursView.showHoursColour(to.colour().toInt());
            }
        };
        this.hoursColour = hoursColour;
        this.hoursColour.addListener(canReceiveSecondsUpdates);
    }

    @Override
    public void dispose() {
        canBeObservedForChangesToHours.removeListener(updateView);
        hoursColour.removeListener(canReceiveSecondsUpdates);
    }

    public static interface HoursView {
        public void showHoursString(String hours);

        void showHoursColour(int colourInt);
    }
}
