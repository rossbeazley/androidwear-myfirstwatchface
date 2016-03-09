package uk.co.rossbeazley.wear.android.ui.config.ui;

import java.util.List;

public interface ConfigItemsListView {
    void showConfigItems(List<String> list);

    void addListener(Listener listener);

    public interface Listener {
        void itemSelected(String two);
    }
}
