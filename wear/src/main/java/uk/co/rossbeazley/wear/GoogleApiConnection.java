package uk.co.rossbeazley.wear;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

class GoogleApiConnection {
    private final GoogleApiClient gac;

    public GoogleApiConnection(Context context, final ConnectedApiClient connected) {
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
}
