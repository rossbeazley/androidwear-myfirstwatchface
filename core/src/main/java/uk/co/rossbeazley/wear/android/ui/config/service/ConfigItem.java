package uk.co.rossbeazley.wear.android.ui.config.service;

import java.util.ArrayList;
import java.util.List;

public interface ConfigItem {

    abstract public String itemId();

    abstract public List<String> options();


    public abstract String defaultOption();
}
