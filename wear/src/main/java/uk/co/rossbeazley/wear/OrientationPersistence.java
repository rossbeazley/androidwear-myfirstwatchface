package uk.co.rossbeazley.wear;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import uk.co.rossbeazley.wear.android.gsm.GoogleWearApiConnection;
import uk.co.rossbeazley.wear.rotation.CanBeObservedForChangesToRotation;
import uk.co.rossbeazley.wear.rotation.CanReceiveRotationUpdates;
import uk.co.rossbeazley.wear.rotation.Orientation;

class OrientationPersistence implements GoogleWearApiConnection.ConnectedApiClient {
    public final static String rotation_key = "ROTATION";
    public final static String rotation_path = "count";
    private CanBeObservedForChangesToRotation<CanReceiveRotationUpdates> canBeObservedForChangesToRotation;

    public OrientationPersistence(CanBeObservedForChangesToRotation<CanReceiveRotationUpdates> canBeObservedForChangesToRotation) {
        this.canBeObservedForChangesToRotation = canBeObservedForChangesToRotation;
    }

    @Override
    public void invoke(final GoogleApiClient gac) {
        canBeObservedForChangesToRotation.addListener(new CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                PutDataMapRequest dataMap = PutDataMapRequest.create("/" + rotation_path);
                dataMap.getDataMap().putFloat(rotation_key, to.degrees());
                PutDataRequest request = dataMap.asPutDataRequest();
                Wearable.DataApi.putDataItem(gac, request);
            }
        });
    }
}
