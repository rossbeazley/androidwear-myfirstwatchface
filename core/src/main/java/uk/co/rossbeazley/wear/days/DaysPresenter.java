package uk.co.rossbeazley.wear.days;

/**
* Created by beazlr02 on 19/11/2014.
*/
public class DaysPresenter {
    public DaysPresenter(CanBeObservedForChangesToDays<CanReceiveDaysUpdates> days, final DaysView daysView) {
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
