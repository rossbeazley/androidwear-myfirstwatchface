package uk.co.rossbeazley.wear.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;

import java.io.Serializable;

import uk.co.rossbeazley.wear.android.ui.config.UIFactory;
import uk.co.rossbeazley.wear.android.ui.config.UIFactoryFragment;

public class UIFactoryFragmentTransaction implements UIFactoryTransaction {

    private final FragmentManager fragmentManager;

    public UIFactoryFragmentTransaction(FragmentManager fm) {
        this.fragmentManager = fm;
    }

    @Override
    public <FragmentUIFactory extends Serializable & UIFactory> void add(FragmentUIFactory factory, int id) {
        Fragment rightFragment = UIFactoryFragment.createUIFactoryFragment(factory);
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out,android.R.animator.fade_in,android.R.animator.fade_out)
                .replace(id,  rightFragment)
                .commit();
    }

    @Override
    public void remove(int id) {
        final Fragment fragmentById = fragmentManager.findFragmentById(id);
        fragmentManager.beginTransaction()
                .remove(fragmentById)
                .commit();
    }
}
