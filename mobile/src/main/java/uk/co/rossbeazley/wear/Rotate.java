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
import uk.co.rossbeazley.wear.android.ui.SetTextOnMainThread;
import uk.co.rossbeazley.wear.colour.CanReceiveColourUpdates;
import uk.co.rossbeazley.wear.colour.Colours;
import uk.co.rossbeazley.wear.rotation.Orientation;
import uk.co.rossbeazley.wear.ticktock.TickTock;

public class Rotate extends Activity {


    private Nodes nodes = null;
    private TickTock tickTock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nodes = new Nodes(this);
        tickTock = TickTock.createTickTock(Core.instance().canBeTicked);
        SetTextOnMainThread.strategy = new SetTextOnMainThread.PostingStrategy();
        createView();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tickTock.stop();
    }

    private void createView() {
        setContentView(R.layout.rotate);

        findViewById(R.id.rotate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rotate.this.nodes.sendMessage("/face/rotate/right");
                Core.instance().canBeRotated.right();
            }
        });

        findViewById(R.id.black_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rotate.this.nodes.sendMessage("/face/colour/black");
            }
        });

        findViewById(R.id.white_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rotate.this.nodes.sendMessage("/face/colour/white");
            }
        });
    }


}
