package uk.co.rossbeazley.wear.hours;

import java.util.Arrays;
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
        return "24 Hours Mode";
    }

    @Override
    public List<String> options() {
        return Arrays.asList("12 Hour", "24 Hour");
    }

    @Override
    public String defaultOption() {
        return optionForMode(defaultHR());
    }

    private String optionForMode(Object o) {
        return o==HR_12 ? "12 Hour" : "24 Hour";
    }

    public boolean is24Hour() {
        return hr==HR_24;
    }

    public Object defaultHR() {
        return hr;
    }

    public Object hoursModeFromOption(String currentOptionForItem) {
        return currentOptionForItem.equals("12 Hour") ? HR_12 : HR_24;
    }
}
