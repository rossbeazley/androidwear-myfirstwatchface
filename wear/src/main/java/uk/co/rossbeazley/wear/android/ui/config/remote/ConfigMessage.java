package uk.co.rossbeazley.wear.android.ui.config.remote;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageApi.MessageListener;
import com.google.android.gms.wearable.Wearable;

import uk.co.rossbeazley.wear.android.gsm.GoogleWearApiConnection;

public class ConfigMessage implements GoogleWearApiConnection.ConnectedApiClient {
    private MessageListener messageAdapter;

    public ConfigMessage(MessageListener messageAdapter) {
        this.messageAdapter = messageAdapter;
    }

    @Override
    public void invoke(GoogleApiClient gac) {
        Wearable.MessageApi.addListener(gac, messageAdapter);
    }
}
