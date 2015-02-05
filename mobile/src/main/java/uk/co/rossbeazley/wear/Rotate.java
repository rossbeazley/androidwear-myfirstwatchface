package uk.co.rossbeazley.wear;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Collections;
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
                Rotate.this.nodes.sendMessage();
            }
        });
    }





    static class Nodes {

        private final Context context;
        private GoogleApiClient gac;

        interface Listable {
            public List<Node> list();
        }

        private final Listable empty = new Listable() {
            @Override
            public List<Node> list() { return Collections.emptyList(); }
        };

        private final Listable connected = new Listable() {
            @Override
            public List<Node> list() {
                PendingResult<NodeApi.GetConnectedNodesResult> connectedNodes = Wearable.NodeApi.getConnectedNodes(gac);
                NodeApi.GetConnectedNodesResult await = connectedNodes.await();
                return await.getNodes(); }
        };

        private Listable nodes = empty;

        Nodes(Context context) {
            this.context = context.getApplicationContext();
            gac = connectToPlayServices();
        }

        public List<Node> nodes() {
            return nodes.list();
        }


        private GoogleApiClient connectToPlayServices() {
            GoogleApiClient gac = new GoogleApiClient.Builder(context)
                    .addApi(Wearable.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {
                            nodes=connected;
                            Log.d("WATCH","connected");
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            nodes=empty;
                            Log.d("WATCH","connection suspended" + i);
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            nodes=empty;
                            Log.d("WATCH",connectionResult.toString());
                            try {
                                Toast.makeText(context,"Failed to conenct to google play services " + connectionResult.toString(),Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Log.e("WATCH","toasting error",e);
                            }
                        }
                    })
                    .build();
            gac.connect();
            return gac;
        }

        public void sendMessage() {

            Runnable runnable = new Runnable() {
                public void run() {
                    for(Node node : nodes()) {
                        byte[] b = new byte[0];
                        Wearable.MessageApi.sendMessage(gac,node.getId(),"/face/rotate/right",b);
                    }
                }
            };
            new Thread(runnable).start();
        }

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
