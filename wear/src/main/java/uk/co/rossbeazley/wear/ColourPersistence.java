package uk.co.rossbeazley.wear;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import uk.co.rossbeazley.wear.android.gsm.GoogleWearApiConnection;
import uk.co.rossbeazley.wear.colour.CanReceiveColourUpdates;
import uk.co.rossbeazley.wear.colour.Colours;

class ColourPersistence implements GoogleWearApiConnection.ConnectedApiClient {
    public final static String key = "COLOR";
    public final static String path = "colour";
    private CanBeObserved<CanReceiveColourUpdates> canReceiveColourUpdatesCanBeObserved;

    public ColourPersistence(CanBeObserved<CanReceiveColourUpdates> canReceiveColourUpdatesCanBeObserved) {
        this.canReceiveColourUpdatesCanBeObserved = canReceiveColourUpdatesCanBeObserved;
    }

    @Override
    public void invoke(final GoogleApiClient gac) {
        canReceiveColourUpdatesCanBeObserved.addListener(new CanReceiveColourUpdates() {
            @Override
            public void colourUpdate(Colours to) {

                PutDataMapRequest dataMap = PutDataMapRequest.create("/" + path);
                dataMap.getDataMap().putString(key, "");
                PutDataRequest request = dataMap.asPutDataRequest();
                Wearable.DataApi.putDataItem(gac, request);
            }
        });
    }
}
