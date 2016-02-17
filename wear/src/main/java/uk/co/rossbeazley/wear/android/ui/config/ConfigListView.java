package uk.co.rossbeazley.wear.android.ui.config;

import java.util.List;

interface ConfigListView {
    void showConfigItems(List<String> list);

    void addListener(Listener listener);

    public interface Listener {
        void itemSelected(String two);
    }
}
