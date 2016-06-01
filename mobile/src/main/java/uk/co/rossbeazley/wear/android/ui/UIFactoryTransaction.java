package uk.co.rossbeazley.wear.android.ui;

import java.io.Serializable;

import uk.co.rossbeazley.wear.android.ui.config.UIFactory;

public interface UIFactoryTransaction {
    <FragmentUIFactory extends Serializable & UIFactory> void add(FragmentUIFactory factory, int id);

    void remove(int id);
}
