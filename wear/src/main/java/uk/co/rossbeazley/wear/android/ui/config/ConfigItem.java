package uk.co.rossbeazley.wear.android.ui.config;

public class ConfigItem {
    private final String itemId;

    public ConfigItem(String expectedItem) {
        itemId = expectedItem;
    }

    public String itemId() {
        return itemId;
    }
}
