package uk.co.rossbeazley.wear.android.ui;

import android.view.View;
import android.widget.TextView;


import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.R;
import uk.co.rossbeazley.wear.days.CanReceiveDaysUpdates;
import uk.co.rossbeazley.wear.days.DaysPresenter;
import uk.co.rossbeazley.wear.months.MonthsPresenter;
import uk.co.rossbeazley.wear.ui.Disposable;

class AndroidDayMonthView implements DaysPresenter.DaysView, MonthsPresenter.MonthView {

    private final SetTextOnMainThread setTextOnMainThread;
    private String days;
    private String months;

    public AndroidDayMonthView(View inflatedViews) {
        TextView textView = (TextView) inflatedViews.findViewById(R.id.date);
        setTextOnMainThread = new SetTextOnMainThread(textView);
    }

    @Override
    public void showDaysString(String newDays) {
        days = newDays;
        update();
    }

    private void update() {
        setTextOnMainThread.to(days + " " + months);
    }

    @Override
    public void showMonthString(String monthString) {
        months = monthString;
        update();
    }

    public static Disposable createMonthDaysView(Core core, View view) {
        CanBeObserved<CanReceiveDaysUpdates> days = core.canBeObservedForChangesToDays;
        AndroidDayMonthView androidDayMonthView = new AndroidDayMonthView(view);
        final DaysPresenter dp = new DaysPresenter(days, androidDayMonthView);
        final MonthsPresenter mp = new MonthsPresenter(core.canBeObservedForChangesToMonths, androidDayMonthView);
        return new Disposable() {
            @Override
            public void dispose() {
                dp.dispose();
                mp.dispose();
            }
        };
    }
}
