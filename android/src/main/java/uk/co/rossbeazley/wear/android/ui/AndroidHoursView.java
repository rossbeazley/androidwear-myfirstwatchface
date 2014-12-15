package uk.co.rossbeazley.wear.android.ui;

import android.view.View;
import android.widget.TextView;


import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.R;
import uk.co.rossbeazley.wear.hours.CanReceiveHoursUpdates;
import uk.co.rossbeazley.wear.hours.HoursPresenter;
import uk.co.rossbeazley.wear.ui.Disposable;

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

    public static Disposable createHoursView(Core core, View views) {
        CanBeObserved<CanReceiveHoursUpdates> hours = core.canBeObservedForChangesToHours;
        AndroidHoursView hoursView = new AndroidHoursView(views);
        return new HoursPresenter(hours, hoursView);
    }
}
