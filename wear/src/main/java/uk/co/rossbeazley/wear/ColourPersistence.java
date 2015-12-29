package uk.co.rossbeazley.wear;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import uk.co.rossbeazley.wear.android.gsm.GoogleWearApiConnection;
import uk.co.rossbeazley.wear.rotation.CanReceiveRotationUpdates;
import uk.co.rossbeazley.wear.rotation.Orientation;

class ColourPersistence implements GoogleWearApiConnection.ConnectedApiClient {
    public final static String key = "COLOR";
    public final static String path = "colour";
    private CanBeObserved<CanReceiveRotationUpdates> canBeObservedForChangesToRotation;

    public ColourPersistence(CanBeObserved<CanReceiveRotationUpdates> canBeObservedForChangesToRotation) {
        this.canBeObservedForChangesToRotation = canBeObservedForChangesToRotation;
    }

    @Override
    public void invoke(final GoogleApiClient gac) {
        canBeObservedForChangesToRotation.addListener(new CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                PutDataMapRequest dataMap = PutDataMapRequest.create("/" + path);
                dataMap.getDataMap().putString(key, "");
                PutDataRequest request = dataMap.asPutDataRequest();
                Wearable.DataApi.putDataItem(gac, request);
            }
        });
    }
}
