package uk.co.rossbeazley.wear.android.ui.config;

import java.util.List;

interface ConfigItemsListView {
    void showConfigItems(List<String> list);

    void addListener(Listener listener);

    public interface Listener {
        void itemSelected(String two);
    }
}
