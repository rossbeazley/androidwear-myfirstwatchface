package uk.co.rossbeazley.wear;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import uk.co.rossbeazley.wear.rotation.CanBeObservedForChangesToRotation;
import uk.co.rossbeazley.wear.rotation.Orientation;
import uk.co.rossbeazley.wear.ticktock.TickTock;

public class Main {

    private static Main instance;

    public static Main instance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new Main(context);
    }


    public Main(final Context context) {
        final Core core = Core.init();
        new GoogleApiConnection(context, new RotationMessage(core.canBeRotated));
        new GoogleApiConnection(context, new OrientationPersistence(core.canBeObservedForChangesToRotation));
        new GoogleApiConnection(context,new ConnectedApiClient() {
            @Override
            public void invoke(final GoogleApiClient gac) {
                // load rotation
                PutDataMapRequest dataMap = PutDataMapRequest.create("/count");
                Wearable.NodeApi.getLocalNode(gac).setResultCallback(new ResultCallback<NodeApi.GetLocalNodeResult>() {
                    @Override
                    public void onResult(NodeApi.GetLocalNodeResult getLocalNodeResult) {

                        Uri requestUri = new Uri.Builder().query("count").scheme("wear").authority(getLocalNodeResult.getNode().getId()).build();
                        System.out.println("ASDASDASDA " + requestUri);
                        PendingResult<DataApi.DataItemResult> pendingResult;
                        pendingResult = Wearable.DataApi.getDataItem(gac, requestUri);
                        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                            @Override
                            public void onResult(DataApi.DataItemResult dataItemResult) {
                                System.out.println("ASDASDASDA " + dataItemResult.toString());
                                TickTock.createTickTock(core.canBeTicked);
                            }
                        });
                    }
                });


            }
        });
    }

    private class OrientationPersistence implements ConnectedApiClient {
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
                    PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi
                            .putDataItem(gac, request);
                }
            });
        }
    }
}
