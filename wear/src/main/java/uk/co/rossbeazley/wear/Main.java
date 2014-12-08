package uk.co.rossbeazley.wear;

import android.content.Context;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.wear.ticktock.TickTock;

public class Main {

    private static Main instance;

    public static Main instance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new Main(context);
    }


    public final Core core;

    public Main(Context context) {
        this.core = Core.instance();
        TickTock.createTickTock(this.core.canBeTicked);
        //createAutoRotatingAdapter();
        new GoogleApiRotateMessage(context,core.canBeRotated);
    }

    private void createAutoRotatingAdapter() {
        Executors.newScheduledThreadPool(1)
                .scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        core.canBeRotated.right();
                    }
                },10,10, TimeUnit.SECONDS);
    }

}
