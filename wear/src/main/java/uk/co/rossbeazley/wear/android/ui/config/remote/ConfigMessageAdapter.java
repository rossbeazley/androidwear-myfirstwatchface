package uk.co.rossbeazley.wear.android.ui.config.remote;

import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;

import uk.co.rossbeazley.wear.config.ConfigService;

public class ConfigMessageAdapter implements MessageApi.MessageListener {
    private final ConfigService configService;

    public ConfigMessageAdapter(ConfigService configService) {

        this.configService = configService;
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String path = messageEvent.getPath();
        String configureMessagePrefix = "/face/configure/";
        if(path.startsWith(configureMessagePrefix)) {
            String[] split = path.replace(configureMessagePrefix, "").split("/to/");
            configService.persistItemChoice(split[0], split[1]);
        }
    }
}
