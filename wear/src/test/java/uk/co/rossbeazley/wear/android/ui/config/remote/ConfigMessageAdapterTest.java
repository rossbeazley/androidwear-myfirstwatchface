package uk.co.rossbeazley.wear.android.ui.config.remote;

import com.google.android.gms.wearable.MessageEvent;

import org.junit.Test;

import uk.co.rossbeazley.wear.TestWorld;
import uk.co.rossbeazley.wear.config.CapturingConfigServiceListener;
import uk.co.rossbeazley.wear.config.ConfigService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ConfigMessageAdapterTest {

    @Test
    public void testOnMessageReceived() throws Exception {

        TestWorld testWorld = new TestWorld();
        ConfigService configService = testWorld.build();

        final String itemName = testWorld.anyItemID();
        final String option = testWorld.aDifferentOptionForItem(itemName);

        ConfigMessageAdapter configMessageAdapter = new ConfigMessageAdapter(configService);
        configMessageAdapter.onMessageReceived(new MessageEvent() {
            @Override
            public int getRequestId() {
                return 0;
            }

            @Override
            public String getPath() {
                return "/face/configure/"+itemName+"/to/"+option;
            }

            @Override
            public byte[] getData() {
                return new byte[0];
            }

            @Override
            public String getSourceNodeId() {
                return null;
            }
        });


        assertThat(configService.currentOptionForItem(itemName),is(option));
    }
}