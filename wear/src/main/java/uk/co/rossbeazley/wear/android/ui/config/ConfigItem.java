package uk.co.rossbeazley.wear.android.ui.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigItem {
    private final String itemId;
    private List<String> options;

    public ConfigItem(String expectedItem) {
        itemId = expectedItem;
        options = new ArrayList<>();
    }

    public String itemId() {
        return itemId;
    }

    public void addOption(String optionOne) {
        this.options.add(optionOne);
    }

    public List<String> options() {
        return options;
    }
}
