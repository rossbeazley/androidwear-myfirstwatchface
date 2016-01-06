package uk.co.rossbeazley.wear;

import android.app.Application;

import uk.co.rossbeazley.wear.android.gsm.GoogleWearApiConnection;
import uk.co.rossbeazley.wear.months.MonthFactory;
import uk.co.rossbeazley.wear.ticktock.TickTock;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String[] months = this.getResources().getStringArray(R.array.months);
        MonthFactory.registerMonthStrings(months);
        Core.init();

    }

}
