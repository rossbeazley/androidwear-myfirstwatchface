package uk.co.rossbeazley.wear.android.ui.config.service;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

public class CapturingConfigServiceListener implements ConfigService.Listener {
    public String configuredItem = "UNKNOWN";
    public KeyNotFound keyNotFoundMessage;

    @Override
    public void configuring(String item) {
        this.configuredItem = item;
    }

    @Override
    public void error(KeyNotFound keyNotFound) {
        keyNotFoundMessage = keyNotFound;
    }
}
