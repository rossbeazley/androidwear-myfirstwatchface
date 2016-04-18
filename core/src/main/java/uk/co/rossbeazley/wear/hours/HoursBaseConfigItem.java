package uk.co.rossbeazley.wear.hours;

import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;

public class HoursBaseConfigItem implements ConfigItem {
    public static final Object HR_24 = new Object();
    public static final Object HR_12 = new Object();
    private final Object hr;

    public HoursBaseConfigItem(Object hr12) {

        hr = hr12;
    }

    @Override
    public String itemId() {
        return null;
    }

    @Override
    public List<String> options() {
        return null;
    }

    @Override
    public String defaultOption() {
        return null;
    }

    public boolean is24Hour() {
        return hr==HR_24;
    }
}
