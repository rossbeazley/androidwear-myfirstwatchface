package uk.co.rossbeazley.wear;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import uk.co.rossbeazley.wear.android.gsm.GoogleWearApiConnection;
import uk.co.rossbeazley.wear.ticktock.TickTock;

public class App extends Application {

    private Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        Core.init();
        TickTock.createTickTock(Core.instance().canBeTicked);

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        tracker = analytics.newTracker("UA-8505275-5");

        new GoogleWearApiConnection(this,new GoogleWearApiConnection.ConnectedApiClient() {
            @Override
            public void invoke(GoogleApiClient gac) {
                Wearable.MessageApi.addListener(gac, new MessageApi.MessageListener() {
                    @Override
                    public void onMessageReceived(MessageEvent messageEvent) {
                        if("/google/analytics/heartbeat".equalsIgnoreCase(messageEvent.getPath())) {
                            pingGoogleAnalytics();
                        }
                    }
                });
            }
        });
    }

    private void pingGoogleAnalytics() {
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("HeartBeat")
                .setAction("Hour")
                .setValue(0)
                .build());

    }

}
