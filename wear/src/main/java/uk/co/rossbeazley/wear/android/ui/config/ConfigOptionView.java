package uk.co.rossbeazley.wear.android.ui.config;

import java.util.List;

public interface ConfigOptionView {
    void showConfigOptions(List<String> configOptions);

    void addListener(Listener capturingListener);

    interface Listener {
        void itemSelected(String two);
    }
}
