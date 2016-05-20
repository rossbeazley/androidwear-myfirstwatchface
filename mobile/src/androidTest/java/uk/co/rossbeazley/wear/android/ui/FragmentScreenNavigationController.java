package uk.co.rossbeazley.wear.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;

import java.io.Serializable;

import uk.co.rossbeazley.wear.ScreenNavigationController;
import uk.co.rossbeazley.wear.android.ui.config.UIFactory;
import uk.co.rossbeazley.wear.android.ui.config.UIFactoryFragment;

public class FragmentScreenNavigationController <UIFact extends Serializable & UIFactory> implements ScreenNavigationController {

    private final int leftID;
    private final int rightID;
    private final UIFactoryFragmentTransaction uiFactoryFragmentTransaction;
    private final UIFact rightFACTORY;
    private final UIFact leftFACTORY;

    public FragmentScreenNavigationController(FragmentManager fm, int test_activity_LEFT_view_id, int test_activity_RIGHT_view_id, UIFact rightFACTORY, UIFact leftFACTORY) {
        leftID = test_activity_LEFT_view_id;
        rightID = test_activity_RIGHT_view_id;
        this.rightFACTORY = rightFACTORY;
        this.leftFACTORY = leftFACTORY;
        uiFactoryFragmentTransaction = new UIFactoryFragmentTransaction(fm);
    }

    @Override
    public void showRight(Class uiPanel) {
        uiFactoryFragmentTransaction.add(this.rightFACTORY, this.rightID);
    }

    @Override
    public void showLeft(Class uiPanel) {
        uiFactoryFragmentTransaction.add(this.leftFACTORY, this.leftID);
    }

    @Override
    public void hideRight() {
        uiFactoryFragmentTransaction.remove(this.rightID);
    }

    private class UIFactoryFragmentTransaction {

        private final FragmentManager fragmentManager;

        public UIFactoryFragmentTransaction(FragmentManager fm) {
            this.fragmentManager = fm;
        }

        public <UIFact extends Serializable & UIFactory> void add(UIFact factory, int id) {
            Fragment rightFragment = UIFactoryFragment.createUIFactoryFragment(factory);
            fragmentManager.beginTransaction()
                    .replace(id,  rightFragment)
                    .commit();
        }

        public void remove(int id) {
            final Fragment fragmentById = fragmentManager.findFragmentById(id);
            fragmentManager.beginTransaction()
                    .remove(fragmentById)
                    .commit();
        }
    }
}
