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
    private final CanBeRotated canBeRotated;

    private MessageApi.MessageListener rotateMessage = new MessageApi.MessageListener() {
        @Override
        public void onMessageReceived(MessageEvent messageEvent) {
            if ("/face/rotate".equals(messageEvent.getPath())) {
                canBeRotated.right();
            }
        }
    };

    public GoogleApiRotateMessage(Context context, final CanBeRotated canBeRotated) {
        this.canBeRotated = canBeRotated;
        this.gac = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        Wearable.MessageApi.addListener(gac, rotateMessage);
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {

                    }
                })
                .build();
                gac.connect();
    }

    public void destroy() {
        Wearable.MessageApi.removeListener(gac, rotateMessage);
        gac.disconnect();
    }
}
