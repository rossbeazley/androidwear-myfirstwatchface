package uk.co.rossbeazley.wear.android.ui.config;

import java.util.List;

public interface SelectableItemListView {
    void showItems(List<String> items);

    void addListener(Listener capturingListener);

    interface Listener {
        void itemSelected(String two);
    }
}
