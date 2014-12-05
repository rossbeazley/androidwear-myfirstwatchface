package uk.co.rossbeazley.wear.android.ui;

import android.view.View;
import android.widget.TextView;


import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.R;
import uk.co.rossbeazley.wear.days.CanBeObservedForChangesToDays;
import uk.co.rossbeazley.wear.days.DaysPresenter;
import uk.co.rossbeazley.wear.months.MonthsPresenter;

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

    public static void createMonthDaysView(Core core, View view) {
        CanBeObservedForChangesToDays days = core.canBeObservedForChangesToDays;
        AndroidDayMonthView androidDayMonthView = new AndroidDayMonthView(view);
        new DaysPresenter(days, androidDayMonthView);
        new MonthsPresenter(core.canBeObservedForChangesToMonths, androidDayMonthView);
    }
}
