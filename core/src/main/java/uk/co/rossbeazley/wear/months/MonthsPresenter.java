package uk.co.rossbeazley.wear.months;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.ui.Disposable;

/**
* Created by beazlr02 on 25/11/2014.
*/
public class MonthsPresenter implements Disposable {
    private final CanBeObserved<CanReceiveMonthsUpdates> months;
    public final CanReceiveMonthsUpdates updateView;

    public MonthsPresenter(CanBeObserved<CanReceiveMonthsUpdates> months, final MonthView view) {
        this.months = months;
        updateView = new CanReceiveMonthsUpdates() {
            @Override
            public void monthsUpdate(Month month) {
                view.showMonthString(month.toString());
            }
        };
        months.addListener(updateView);
    }

    @Override
    public void dispose() {
        months.removeListener(updateView);
    }

    public static interface MonthView {
        void showMonthString(String monthString);
    }
}
