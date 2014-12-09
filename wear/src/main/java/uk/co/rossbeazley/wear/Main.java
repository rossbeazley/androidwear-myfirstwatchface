package uk.co.rossbeazley.wear;

import android.content.Context;
import android.net.Uri;
import android.os.Debug;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

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
        TickTock.createTickTock(core.canBeTicked);
        new GoogleApiConnection(context, new RotationMessage(core.canBeRotated));
        new GoogleApiConnection(context, new OrientationPersistence(core.canBeObservedForChangesToRotation));
        //Debug.waitForDebugger();
        new GoogleApiConnection(context, new RestoreRotationSPIKE());
    }

    private static class RestoreRotationSPIKE implements ConnectedApiClient {
        @Override
        public void invoke(final GoogleApiClient gac) {
            // load rotation
            Wearable.NodeApi.getLocalNode(gac).setResultCallback(new ResultCallback<NodeApi.GetLocalNodeResult>() {
                @Override
                public void onResult(NodeApi.GetLocalNodeResult getLocalNodeResult) {

                    Uri requestUri = new Uri.Builder()
                                            .scheme("wear")
                                            .authority(getLocalNodeResult.getNode().getId())
                                            .path("count")
                                            .build();
                    PendingResult<DataApi.DataItemResult> pendingResult;
                    pendingResult = Wearable.DataApi.getDataItem(gac, requestUri);
                    pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                        @Override
                        public void onResult(DataApi.DataItemResult dataItemResult) {
                            DataMapItem map = DataMapItem.fromDataItem(dataItemResult.getDataItem());
                            float rotation = map.getDataMap().getFloat("ROTATION");
                            System.out.println(rotation);
                        }
                    });
                }
            });


        }
    }
}
