package uk.co.rossbeazley.wear;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.Collections;
import java.util.List;

public class Rotate extends Activity {


    private Nodes nodes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nodes = new Nodes(this);
        createView();
    }

    private void createView() {
        setContentView(R.layout.roate);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence mesg = ((TextView) findViewById(R.id.textView)).getText();
                Rotate.this.nodes.sendMessage(mesg);
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
            public List<Node> list() { return Wearable.NodeApi.getConnectedNodes(gac).await().getNodes(); }
        };

        private Listable nodes = empty;

        Nodes(Context context) {
            this.context = context.getApplicationContext();
            connectToPlayServices();
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
                        }
                    })
                    .build();
            gac.connect();
            return gac;
        }

        public void sendMessage(CharSequence mesg) {
            for(Node node : nodes.list()) {
                Wearable.MessageApi.sendMessage(gac,node.getId(),"/face/rotate",mesg.toString().getBytes());
            }
        }

    }
}
