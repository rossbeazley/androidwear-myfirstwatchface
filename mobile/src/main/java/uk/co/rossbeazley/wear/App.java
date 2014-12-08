package uk.co.rossbeazley.wear;

import android.app.Application;

import uk.co.rossbeazley.wear.ticktock.TickTock;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Core.init();
        TickTock.createTickTock(Core.instance().canBeTicked);
    }
}
