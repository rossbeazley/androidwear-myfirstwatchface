package uk.co.rossbeazley.wear.android.ui;

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

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.Nodes;
import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.gsm.GoogleWearApiConnection;
import uk.co.rossbeazley.wear.colour.CanReceiveColourUpdates;
import uk.co.rossbeazley.wear.colour.Colours;
import uk.co.rossbeazley.wear.rotation.Orientation;

public class ConfigActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView();
    }

    private void createView() {
        Main.instance().tickTock.start();
        setContentView(R.layout.rotate);

        final Core core = Core.instance();
        core.canBeObservedForChangesToColour.addListener(new CanReceiveColourUpdates() {
            @Override
            public void colourUpdate(Colours to) {
                findViewById(R.id.rotate_container).setBackgroundColor(to.background().toInt());
            }
        });

        findViewById(R.id.rotate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                core.canBeRotated.right();
            }
        });

        findViewById(R.id.black_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                core.canBeColoured.background(Colours.Colour.BLACK);
            }
        });

        findViewById(R.id.white_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                core.canBeColoured.background(Colours.Colour.WHITE);
            }
        });
    }

}
