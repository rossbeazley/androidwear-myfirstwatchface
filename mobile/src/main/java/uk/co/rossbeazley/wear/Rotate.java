package uk.co.rossbeazley.wear;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import uk.co.rossbeazley.wear.android.ui.SetTextOnMainThread;
import uk.co.rossbeazley.wear.ticktock.TickTock;

public class Rotate extends Activity {


    private Broadcast broadcast = null;
    private TickTock tickTock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcast = new Nodes(this);
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
                Rotate.this.broadcast.sendMessage("/face/rotate/right");
                Core.instance().canBeRotated.right();
            }
        });

        findViewById(R.id.black_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rotate.this.broadcast.sendMessage("/face/colour/black");
            }
        });

        findViewById(R.id.white_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rotate.this.broadcast.sendMessage("/face/colour/white");
            }
        });
    }


}
