package uk.co.rossbeazley.wear;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import uk.co.rossbeazley.wear.android.gsm.GoogleWearApiConnection;
import uk.co.rossbeazley.wear.colour.Colours;

public class ColourMessage implements GoogleWearApiConnection.ConnectedApiClient {

    public static final String FACE_COLOUR_PATH = "/face/colour/";
    private CanBeColoured canBeColoured;
    private MessageApi.MessageListener rotateMessage = new MessageApi.MessageListener() {
        @Override
        public void onMessageReceived(MessageEvent messageEvent) {
            String path = messageEvent.getPath();
            if (path.startsWith(FACE_COLOUR_PATH)) {
                String colourString = path.replaceAll(FACE_COLOUR_PATH, "");
                canBeColoured.background(colourString.equals("black")? Colours.Colour.BLACK: Colours.Colour.WHITE);
            }
        }
    };

    public ColourMessage(CanBeColoured canBeColoured) {
        this.canBeColoured = canBeColoured;
    }

    public void invoke(GoogleApiClient googleApiClient) {
        Wearable.MessageApi.addListener(googleApiClient, rotateMessage);
    }
}
