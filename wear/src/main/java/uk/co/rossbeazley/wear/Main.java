package uk.co.rossbeazley.wear;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.wear.ticktock.TickTock;

public class Main {

    private static Main instance;

    public static Main instance() {
        return instance;
    }

    public static void init() {
        instance = new Main();
    }


    public final Core core;

    public Main() {
        this.core = new Core();
        TickTock.createTickTock(this.core.canBeTicked);
        Executors.newScheduledThreadPool(1)
                .scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        core.canBeRotated.right();
                    }
                },10,10, TimeUnit.SECONDS);
    }

}
