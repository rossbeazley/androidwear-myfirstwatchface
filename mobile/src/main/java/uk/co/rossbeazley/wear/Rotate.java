package uk.co.rossbeazley.wear;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

import uk.co.rossbeazley.wear.android.gsm.GoogleWearApiConnection;
import uk.co.rossbeazley.wear.rotation.Orientation;

public class Rotate extends Activity {


    private Nodes nodes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nodes = new Nodes(this);
        new GoogleWearApiConnection(this, new RotationToDegreesMessage());
        createView();
    }

    private void createView() {
        setContentView(R.layout.roate);
        findViewById(R.id.rotate_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rotate.this.nodes.sendMessage("/face/rotate/right");
            }
        });
    }


    private static class RotationToDegreesMessage implements GoogleWearApiConnection.ConnectedApiClient {
        @Override
        public void invoke(GoogleApiClient gac) {
            Wearable.DataApi.addListener(gac,new DataApi.DataListener() {
                @Override
                public void onDataChanged(DataEventBuffer dataEvents) {
                    final List<DataEvent> events = FreezableUtils.freezeIterable(dataEvents);
                    for (DataEvent event : events) {
                        Uri uri = event.getDataItem().getUri();
                        if(uri.getPath().contains("count")) {
                            DataMapItem map = DataMapItem.fromDataItem(event.getDataItem());
                            float degreesAsFloat = map.getDataMap().getFloat("ROTATION");
                            Core.instance().canBeRotated.to(Orientation.from(degreesAsFloat));
                        }
                    }
                }
            });
        }
    }
}
