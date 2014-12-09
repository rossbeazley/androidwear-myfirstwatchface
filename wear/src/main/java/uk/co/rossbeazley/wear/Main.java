package uk.co.rossbeazley.wear;

import android.content.Context;

import uk.co.rossbeazley.wear.ticktock.TickTock;

public class Main {

    private static Main instance;

    public static Main instance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new Main(context);
    }


    public Main(final Context context) {
        final Core core = Core.init();
        TickTock.createTickTock(core.canBeTicked);
        new GoogleWearApiConnection(context, new RotationMessage(core.canBeRotated));
        new GoogleWearApiConnection(context, new OrientationPersistence(core.canBeObservedForChangesToRotation));
        //Debug.waitForDebugger();
        new GoogleWearApiConnection(context, new RestoreRotationSPIKE(core.canBeRotated));
    }

}
