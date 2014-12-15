package uk.co.rossbeazley.wear.days;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.ui.Disposable;

/**
* Created by beazlr02 on 19/11/2014.
*/
public class DaysPresenter implements Disposable {
    private final CanBeObserved<CanReceiveDaysUpdates> days;
    public final CanReceiveDaysUpdates updateView;

    public DaysPresenter(CanBeObserved<CanReceiveDaysUpdates> days, final DaysView daysView) {
        this.days = days;
        updateView = new CanReceiveDaysUpdates() {
            @Override
            public void daysUpdate(Day to) {
                daysView.showDaysString(to.toOrdinalString());
            }
        };
        days.addListener(updateView);
    }

    @Override
    public void dispose() {
        days.removeListener(updateView);
    }

    public interface DaysView {
        void showDaysString(String newDays);
    }
}
