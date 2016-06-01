package uk.co.rossbeazley.wear;

import uk.co.rossbeazley.wear.config.ConfigServiceListener;

class WearBroadcastConfigServiceListener implements ConfigServiceListener {
    private final Broadcast broadcast;
    private String itemName;

    public WearBroadcastConfigServiceListener(Broadcast broadcast) {

        this.broadcast = broadcast;
    }

        @Override
    public void configuring(String item) {

            itemName = item;
        }

    @Override
    public void error(KeyNotFound keyNotFound) {

    }

    @Override
    public void chosenOption(String option) {
        broadcast.sendMessage("/face/configure/"+itemName+"/to/"+option);
    }
}
