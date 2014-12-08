package uk.co.rossbeazley.wear;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import uk.co.rossbeazley.wear.rotation.CanBeObservedForChangesToRotation;
import uk.co.rossbeazley.wear.rotation.Orientation;

class OrientationPersistence implements ConnectedApiClient {
    private CanBeObservedForChangesToRotation canBeObservedForChangesToRotation;

    public OrientationPersistence(CanBeObservedForChangesToRotation canBeObservedForChangesToRotation) {
        this.canBeObservedForChangesToRotation = canBeObservedForChangesToRotation;
    }

    @Override
    public void invoke(final GoogleApiClient gac) {
        canBeObservedForChangesToRotation.observe(new CanBeObservedForChangesToRotation.CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                PutDataMapRequest dataMap = PutDataMapRequest.create("/count");
                dataMap.getDataMap().putFloat("ROTATION", to.degrees());
                PutDataRequest request = dataMap.asPutDataRequest();
                Wearable.DataApi.putDataItem(gac, request);
            }
        });
    }
}
