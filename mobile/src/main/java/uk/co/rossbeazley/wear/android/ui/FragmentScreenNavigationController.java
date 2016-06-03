package uk.co.rossbeazley.wear.android.ui;

import java.io.Serializable;
import java.util.Map;

import uk.co.rossbeazley.wear.ScreenNavigationController;
import uk.co.rossbeazley.wear.android.ui.config.UIFactory;

public class FragmentScreenNavigationController <FragmentUIFactory extends Serializable & UIFactory> implements ScreenNavigationController {

    private final int leftID;
    private final int rightID;
    private final UIFactoryTransaction uiFactoryTransaction;
    private final FragmentUIFactory rightFACTORY;
    private final FragmentUIFactory leftFACTORY;
    private final Map<Class, UIFactory> rightHandFactories;


    public FragmentScreenNavigationController(int test_left, int test_right, Map<Class, UIFactory> rightHandFactories, FragmentUIFactory factory, UIFactoryTransaction uiFactoryTransaction) {

        this.leftID = test_left;
        this.rightID = test_right;
        this.rightHandFactories = rightHandFactories;
        this.leftFACTORY = factory;
        this.rightFACTORY = null;
        this.uiFactoryTransaction = uiFactoryTransaction;
    }

    @Override
    public void showRight(Class uiPanel) {
        FragmentUIFactory rightFACTORY = (FragmentUIFactory) rightHandFactories.get(uiPanel);
        uiFactoryTransaction.add(rightFACTORY, this.rightID);
    }

    @Override
    public void showLeft() {
        uiFactoryTransaction.add(this.leftFACTORY, this.leftID);
    }

    @Override
    public void hideRight() {
        uiFactoryTransaction.remove(this.rightID);
    }

}
