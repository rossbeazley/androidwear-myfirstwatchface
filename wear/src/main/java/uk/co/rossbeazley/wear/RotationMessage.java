package uk.co.rossbeazley.wear;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import uk.co.rossbeazley.wear.rotation.CanBeRotated;

public class RotationMessage implements GoogleWearApiConnection.ConnectedApiClient {

    private CanBeRotated canBeRotated;
    private MessageApi.MessageListener rotateMessage = new MessageApi.MessageListener() {
        @Override
        public void onMessageReceived(MessageEvent messageEvent) {
            if ("/face/rotate/right".equals(messageEvent.getPath())) {
                canBeRotated.right();
            }
        }
    };

    public RotationMessage(CanBeRotated canBeRotated) {
        this.canBeRotated = canBeRotated;
    }

    public void invoke(GoogleApiClient googleApiClient) {
        Wearable.MessageApi.addListener(googleApiClient, rotateMessage);
    }
}
