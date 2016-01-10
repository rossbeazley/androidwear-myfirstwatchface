package uk.co.rossbeazley.wear;

import android.graphics.Color;
import android.net.Uri;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import uk.co.rossbeazley.wear.android.gsm.GoogleWearApiConnection;
import uk.co.rossbeazley.wear.colour.Colours;
import uk.co.rossbeazley.wear.rotation.Orientation;

/**
* Created by beazlr02 on 09/12/2014.
*/
class RestoreColourSPIKE implements GoogleWearApiConnection.ConnectedApiClient {

    public static interface Restored {
        void to(Colours colours);
    }

    private Announcer<Restored> announcer;

    RestoreColourSPIKE() {
        announcer = Announcer.to(Restored.class);
    }

    public void observe(Restored observer) {
        announcer.addListener(observer);
    }

    @Override
    public void invoke(final GoogleApiClient gac) {
//        System.out.println("RESTORED ROTATION drawToBounds");
        // load rotation
        Wearable.NodeApi.getLocalNode(gac).setResultCallback(new ResultCallback<NodeApi.GetLocalNodeResult>() {
            @Override
            public void onResult(NodeApi.GetLocalNodeResult getLocalNodeResult) {
//                System.out.println("RESTORED ROTATION got local node");
                Uri requestUri = new Uri.Builder()
                                        .scheme("wear")
                                        .authority(getLocalNodeResult.getNode().getId())
                                        .path(ColourPersistence.path)
                                        .build();
                PendingResult<DataApi.DataItemResult> pendingResult;
                pendingResult = Wearable.DataApi.getDataItem(gac, requestUri);
                pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(DataApi.DataItemResult dataItemResult) {
//                        System.out.println("RESTORED ROTATION got data item");
                        try {
                            DataItem dataItem = dataItemResult.getDataItem();
                            DataMapItem map = DataMapItem.fromDataItem(dataItem);
                            int colourAsInt = map.getDataMap().getInt(ColourPersistence.key);
                            Colours from = new Colours(new Colours.Colour(colourAsInt));
                            System.out.println(from);
                            System.out.println("RESTORED Colour announcing ");
                            announcer.announce().to(from);
                        } catch (Exception ignored) {
                            ignored.printStackTrace();
                            System.out.println("RESTORED Colour announcing ");
                            announcer.announce().to(new Colours(Colours.Colour.WHITE));
                        }
                    }
                });
            }
        });


    }
}