package uk.co.rossbeazley.wear.months;

/**
* Created by beazlr02 on 25/11/2014.
*/
public class MonthsPresenter {
    public MonthsPresenter(CanBeObservedForChangesToMonths months, final MonthView view) {
        months.observe(new CanReceiveMonthsUpdates() {
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
