package uk.co.rossbeazley.wear.android.ui.config.service;

import java.util.ArrayList;
import java.util.List;

public class ConfigItem {
    private final String itemId;
    private List<String> options;
    private String defaultOption;

    public ConfigItem(String expectedItem) {
        itemId = expectedItem;
        options = new ArrayList<>();
    }

    public String itemId() {
        return itemId;
    }

    protected void addOption(String optionOne) {
        this.options.add(optionOne);
    }

    public List<String> options() {
        return options;
    }

    protected ConfigItem defaultOption(String optionTwo) {
        defaultOption = optionTwo;
        return this;
    }

    public String defaultOption() {
        return defaultOption;
    }

    protected ConfigItem addOptions(String... options) {
        for(String option : options) {
            addOption(option);
        }
        return this;
    }
}
