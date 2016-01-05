package uk.co.rossbeazley.wear;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import uk.co.rossbeazley.wear.android.gsm.GoogleWearApiConnection;

public class DataRepository extends WearableListenerService {

    private OrientationPersistence savesOrientation;

    @Override
    public void onCreate() {
        System.out.println("MSG SERVICE CREATED");
        super.onCreate();
        savesOrientation = new OrientationPersistence(Core.instance().canBeObservedForChangesToRotation);
        new GoogleWearApiConnection(this, savesOrientation);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        System.out.println("MSG RECEIVED" + messageEvent.toString());
        if ("/face/rotate/right".equals(messageEvent.getPath())) {
            Core.instance().canBeRotated.right();
        }
    }
}
