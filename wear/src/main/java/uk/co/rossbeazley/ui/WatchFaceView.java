package uk.co.rossbeazley.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.days.CanBeObservedForChangesToDays;
import uk.co.rossbeazley.wear.days.DaysPresenter;
import uk.co.rossbeazley.wear.hours.CanBeObservedForChangesToHours;
import uk.co.rossbeazley.wear.hours.HoursPresenter;
import uk.co.rossbeazley.wear.minutes.CanBeObservedForChangesToMinutes;
import uk.co.rossbeazley.wear.minutes.MinutesPresenter;
import uk.co.rossbeazley.wear.months.MonthsPresenter;
import uk.co.rossbeazley.wear.rotation.RotationPresenter;
import uk.co.rossbeazley.wear.seconds.CanBeObservedForChangesToSeconds;
import uk.co.rossbeazley.wear.seconds.SecondsPresenter;

/**
 * Created by beazlr02 on 14/11/2014.
 */
public class WatchFaceView extends RelativeLayout {
    public WatchFaceView(Context context) {
        super(context);
    }

    public WatchFaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WatchFaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Main main = Main.instance();

        createSecondsView(main);
        createMinutesView(main);
        createHoursView(main);
        createMonthDaysView(main);
        AndroidRotationView.createRotationView(main.core,this);
    }


    private void createMonthDaysView(Main main) {
        CanBeObservedForChangesToDays days = main.core.canBeObservedForChangesToDays;
        AndroidDayMonthView androidDayMonthView = new AndroidDayMonthView(this);
        new DaysPresenter(days, androidDayMonthView);
        new MonthsPresenter(main.core.canBeObservedForChangesToMonths, androidDayMonthView);
    }

    private void createHoursView(Main main) {
        CanBeObservedForChangesToHours hours = main.core.canBeObservedForChangesToHours;
        AndroidHoursView hoursView = new AndroidHoursView(this);
        new HoursPresenter(hours, hoursView);
    }

    private void createMinutesView(Main main)
    {
        CanBeObservedForChangesToMinutes minutes = main.core.canBeObservedForChangesToMinutes;
        MinutesPresenter.MinutesView minutesView = new AndroidMinutesView(this);
        new MinutesPresenter(minutes, minutesView);
    }

    private void createSecondsView(Main main) {
        CanBeObservedForChangesToSeconds seconds = main.core.canBeObservedForChangesToSeconds;
        AndroidSecondsView secondsview = new AndroidSecondsView(this);
        new SecondsPresenter(seconds, secondsview);
    }

}
