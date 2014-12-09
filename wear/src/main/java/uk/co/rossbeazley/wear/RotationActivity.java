package uk.co.rossbeazley.wear;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by beazlr02 on 09/12/2014.
 */
public class RotationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotate);
        findViewById(R.id.rotateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Core.instance().canBeRotated.right();
            }
        });
    }
}
