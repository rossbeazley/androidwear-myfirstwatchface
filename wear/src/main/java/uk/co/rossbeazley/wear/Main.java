package uk.co.rossbeazley.wear;

import android.content.Context;
import android.os.Debug;

import uk.co.rossbeazley.wear.android.gsm.GoogleWearApiConnection;
import uk.co.rossbeazley.wear.rotation.Orientation;
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
        //Debug.waitForDebugger();
        TickTock.createTickTock(core.canBeTicked);
        RestoreRotationSPIKE loadOrientationFromPersistentStore = new RestoreRotationSPIKE();
        loadOrientationFromPersistentStore.observe(new RotateWatchFace(core));
        loadOrientationFromPersistentStore.observe(new BindRotationMessageAdapter(context, core));
        loadOrientationFromPersistentStore.observe(new BindRotationPersistence(context, core));
        //loadOrientationFromPersistentStore.addListener(new BindTickTock(core));
        new GoogleWearApiConnection(context, loadOrientationFromPersistentStore);

    }

    private static class RotateWatchFace implements RestoreRotationSPIKE.Restored {
        private final Core core;

        public RotateWatchFace(Core core) {
            this.core = core;
        }

        @Override
        public void to(Orientation orientation) {
            core.canBeRotated.to(orientation);
        }
    }

    private static class BindRotationMessageAdapter implements RestoreRotationSPIKE.Restored {
        private final Context context;
        private final Core core;

        public BindRotationMessageAdapter(Context context, Core core) {
            this.context = context;
            this.core = core;
        }

        @Override
        public void to(Orientation orientation) {
            new GoogleWearApiConnection(context, new RotationMessage(core.canBeRotated));
        }
    }

    private static class BindRotationPersistence implements RestoreRotationSPIKE.Restored {
        private final Context context;
        private final Core core;

        public BindRotationPersistence(Context context, Core core) {
            this.context = context;
            this.core = core;
        }

        @Override
        public void to(Orientation orientation) {
            new GoogleWearApiConnection(context, new OrientationPersistence(core.canBeObservedForChangesToRotation));
        }
    }

    private static class BindTickTock implements RestoreRotationSPIKE.Restored {
        private final Core core;

        public BindTickTock(Core core) {
            this.core = core;
        }

        @Override
        public void to(Orientation orientation) {
            TickTock.createTickTock(core.canBeTicked);
        }
    }
}
