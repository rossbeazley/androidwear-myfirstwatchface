package uk.co.rossbeazley.wear.config;

public class CapturingConfigServiceListener implements uk.co.rossbeazley.wear.config.ConfigServiceListener {
    public String configuredItem = "UNKNOWN";
    public KeyNotFound keyNotFoundMessage;
    public String configuredOption = "UNKNOWN";

    @Override
    public void configuring(String item) {
        this.configuredItem = item;
    }

    @Override
    public void error(KeyNotFound keyNotFound) {
        keyNotFoundMessage = keyNotFound;
    }

    @Override
    public void chosenOption(String option) {
        configuredOption = option;

    }
}
