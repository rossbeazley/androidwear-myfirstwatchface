package uk.co.rossbeazley.wear.months;

/**
* Created by beazlr02 on 25/11/2014.
*/
public class MonthsPresenter {
    public MonthsPresenter(CanBeObservedForChangesToMonths<CanReceiveMonthsUpdates> months, final MonthView view) {
        months.addListener(new CanReceiveMonthsUpdates() {
            @Override
            public void monthsUpdate(Month month) {
                view.showMonthString(month.toString());
            }
        });
    }

    public static interface MonthView {
        void showMonthString(String monthString);
    }
}
