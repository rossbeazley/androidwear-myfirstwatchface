package uk.co.rossbeazley.wear;

import android.app.Application;

import uk.co.rossbeazley.wear.config.HashMapPersistence;
import uk.co.rossbeazley.wear.months.MonthFactory;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String[] months = this.getResources().getStringArray(R.array.months);
        MonthFactory.registerMonthStrings(months);
        Core.init(new HashMapPersistence());

    }

}
