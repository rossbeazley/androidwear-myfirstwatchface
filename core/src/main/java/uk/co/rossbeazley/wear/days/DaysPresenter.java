package uk.co.rossbeazley.wear.days;

import uk.co.rossbeazley.wear.CanBeObserved;

/**
* Created by beazlr02 on 19/11/2014.
*/
public class DaysPresenter {
    public DaysPresenter(CanBeObserved<CanReceiveDaysUpdates> days, final DaysView daysView) {
        days.addListener(new CanReceiveDaysUpdates() {
            @Override
            public void daysUpdate(Day to) {
                daysView.showDaysString(to.toOrdinalString());
            }
        });
    }

    public interface DaysView {
        void showDaysString(String newDays);
    }
}
