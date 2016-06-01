package uk.co.rossbeazley.wear;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;

public class WearBroadcastConfigServiceListenerTest {

    @Test
    public void testChosenOption() throws Exception {

        BroadcastJournal broadcastJournal = new BroadcastJournal();
        WearBroadcastConfigServiceListener wearBroadcastConfigServiceListener = new WearBroadcastConfigServiceListener(broadcastJournal);

        String item1 = "item1";
        String option1 = "option1";

        wearBroadcastConfigServiceListener.configuring(item1);
        wearBroadcastConfigServiceListener.chosenOption(option1);

        assertThat(broadcastJournal.journal,hasItems("/face/configure/"+item1+"/to/"+option1));

    }

    private static class BroadcastJournal implements Broadcast {

        public List<String> journal = new ArrayList<>();

        @Override
        public void sendMessage(String messagePathString) {
            journal.add(messagePathString);
        }
    }
}