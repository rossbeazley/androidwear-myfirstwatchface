package uk.co.rossbeazley.wear.hours;

import java.util.Arrays;
import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;

public class HoursBaseConfigItem implements ConfigItem {
    public static final Object HR_24 = new Object(){
        @Override
        public String toString() {
            return "24 Hour";
        }
    };

    public static final Object HR_12 = new Object(){
        @Override
        public String toString() {
            return "12 Hour";
        }
    };
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
        return Arrays.asList(HR_12.toString(), HR_24.toString());
    }

    @Override
    public String defaultOption() {
        return optionForMode(defaultHR());
    }

    private String optionForMode(Object o) {
        return o.toString();
    }

    public Object defaultHR() {
        return hr;
    }

    public Object hoursModeFromOption(String currentOptionForItem) {
        return currentOptionForItem.equals(HR_12.toString()) ? HR_12 : HR_24;
    }
}
