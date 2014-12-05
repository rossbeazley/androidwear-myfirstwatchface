package uk.co.rossbeazley.ui;

import android.view.View;
import android.widget.TextView;


import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.hours.CanBeObservedForChangesToHours;
import uk.co.rossbeazley.wear.hours.HoursPresenter;

class AndroidHoursView implements HoursPresenter.HoursView {
    private SetTextOnMainThread setTextOnMainThread;

    public AndroidHoursView(View view) {
        TextView textView = (TextView) view.findViewById(R.id.watch_time);
        setTextOnMainThread = new SetTextOnMainThread(textView);
    }

    @Override
    public void showHoursString(final String newHour) {
        setTextOnMainThread.to(newHour);
    }

    public static void createHoursView(Core core, View views) {
        CanBeObservedForChangesToHours hours = core.canBeObservedForChangesToHours;
        AndroidHoursView hoursView = new AndroidHoursView(views);
        new HoursPresenter(hours, hoursView);
    }
}
