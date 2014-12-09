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
        new GoogleApiConnection(context, new RotationMessage(core.canBeRotated));
        new GoogleApiConnection(context, new OrientationPersistence(core.canBeObservedForChangesToRotation));
        //Debug.waitForDebugger();
        new GoogleApiConnection(context, new RestoreRotationSPIKE(core.canBeRotated));
    }

}
