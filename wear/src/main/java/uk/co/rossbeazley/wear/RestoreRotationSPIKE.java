package uk.co.rossbeazley.wear;

import android.net.Uri;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import uk.co.rossbeazley.wear.rotation.CanBeRotated;
import uk.co.rossbeazley.wear.rotation.Orientation;

/**
* Created by beazlr02 on 09/12/2014.
*/
class RestoreRotationSPIKE implements GoogleWearApiConnection.ConnectedApiClient {
    private final CanBeRotated canBeRotated;

    public RestoreRotationSPIKE(CanBeRotated canBeRotated) {
        this.canBeRotated = canBeRotated;
    }

    @Override
    public void invoke(final GoogleApiClient gac) {
        // load rotation
        Wearable.NodeApi.getLocalNode(gac).setResultCallback(new ResultCallback<NodeApi.GetLocalNodeResult>() {
            @Override
            public void onResult(NodeApi.GetLocalNodeResult getLocalNodeResult) {

                Uri requestUri = new Uri.Builder()
                                        .scheme("wear")
                                        .authority(getLocalNodeResult.getNode().getId())
                                        .path(OrientationPersistence.rotation_path)
                                        .build();
                PendingResult<DataApi.DataItemResult> pendingResult;
                pendingResult = Wearable.DataApi.getDataItem(gac, requestUri);
                pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(DataApi.DataItemResult dataItemResult) {
                        try {
                            DataItem dataItem = dataItemResult.getDataItem();
                            DataMapItem map = DataMapItem.fromDataItem(dataItem);
                            float degreesAsFloat = map.getDataMap().getFloat(OrientationPersistence.rotation_key);
                            System.out.println(degreesAsFloat);
                            canBeRotated.to(Orientation.from(degreesAsFloat));
                        } catch (Exception ignored) {
                            ignored.printStackTrace();
                        }
                    }
                });
            }
        });


    }
}
