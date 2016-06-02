package uk.co.rossbeazley.wear.android.gsm;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

public class GoogleWearApiConnection {
    private final GoogleApiClient gac;

    public GoogleWearApiConnection(Context context, final ConnectedApiClient connected) {
        this.gac = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        System.out.println("CONNECTED========");
                        connected.invoke(gac);
                    }

                    @Override
                    public void onConnectionSuspended(int i) { }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                    }
                })
                .build();
        gac.connect();
    }

    public static interface ConnectedApiClient {
        void invoke(GoogleApiClient gac);
    }
}
