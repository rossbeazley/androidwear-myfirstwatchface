package uk.co.rossbeazley.wear;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Collections;
import java.util.List;

public class Nodes {

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
                        nodes = connected;
                        Log.d("WATCH", "connected");
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        nodes = empty;
                        Log.d("WATCH", "connection suspended" + i);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        nodes = empty;
                        Log.d("WATCH", connectionResult.toString());
                        try {
                            Toast.makeText(context, "Failed to conenct to google play services " + connectionResult.toString(), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e("WATCH", "toasting error", e);
                        }
                    }
                })
                .build();
        gac.connect();
        return gac;
    }

    public void sendMessage(final String messagePathString) {

        Runnable runnable = new Runnable() {
            public void run() {
                for(Node node : nodes()) {
                    byte[] b = new byte[0];
                    Wearable.MessageApi.sendMessage(gac,node.getId(), messagePathString,b);
                }
            }
        };
        new Thread(runnable).start();
    }

}
