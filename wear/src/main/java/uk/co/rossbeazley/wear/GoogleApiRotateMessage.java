package uk.co.rossbeazley.wear;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import uk.co.rossbeazley.wear.rotation.CanBeRotated;

public class GoogleApiRotateMessage {
    private final GoogleApiClient gac;

    public GoogleApiRotateMessage(Context context, final ConnectedApiClient connected) {
        this.gac = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        connected.invoke(gac);
                    }

                    @Override
                    public void onConnectionSuspended(int i) { }
                })
                .build();
                gac.connect();
    }

    // since the app dosnt "Stop" unless killed, maybe we dont call this at all
    public void destroy() {
        //Wearable.MessageApi.removeListener(gac, rotateMessage);
        //gac.disconnect();
    }

}
