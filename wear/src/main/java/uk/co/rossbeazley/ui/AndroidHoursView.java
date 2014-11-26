package uk.co.rossbeazley.ui;

import android.view.View;
import android.widget.TextView;

import com.examples.myfirstwatchface.R;

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
}
