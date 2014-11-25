package uk.co.rossbeazley.wear;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.examples.myfirstwatchface.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class Rotate extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView();
    }

    private void createView() {
        setContentView(R.layout.roate);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence mesg = ((TextView) findViewById(R.id.textView)).getText();
                Rotate.this.sendMessage(mesg);
            }
        });
    }

    private void sendMessage(CharSequence mesg) {
        GoogleApiClient gac = null;
        NodeApi.GetConnectedNodesResult result = Wearable.NodeApi.getConnectedNodes(gac).await();
        for(Node node : result.getNodes()) {
            Wearable.MessageApi.sendMessage(gac,node.getId(),"/face/rotate",mesg.toString().getBytes());
        }
    }
}
