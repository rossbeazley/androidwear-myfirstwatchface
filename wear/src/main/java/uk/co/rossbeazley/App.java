package uk.co.rossbeazley;

import android.app.Application;

import uk.co.rossbeazley.wear.Main;

/**
 * Created by rdlb on 14/11/14.
 */
public class App extends Application {
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Main.init(this);
    }
}
