package uk.co.rossbeazley.wear.hours;

import java.util.Arrays;
import java.util.List;

import uk.co.rossbeazley.wear.config.ConfigItem;

public class HoursModeConfigItem implements ConfigItem {
    public static final Object HR_24 = new HoursMode("24 Hour");
    public static final Object HR_12 = new HoursMode("12 Hour");

    private final Object hr;

    public HoursModeConfigItem(Object hr12) {
        hr = hr12;
    }

    @Override
    public String itemId() {
        return "Hours Mode";
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

    public String optionFromHoursMode(Object hr24) {
        return hr24.toString(); //TODO inline into the private version of this method
    }

    public static final class HoursMode {
        private final String optionString;

        public HoursMode(String optionString) {
            this.optionString = optionString;
        }

        @Override
        public String toString() {
            return optionString;
        }
    }
}
