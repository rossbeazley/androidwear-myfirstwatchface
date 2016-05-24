package uk.co.rossbeazley.wear.android.ui;

import java.io.Serializable;

import uk.co.rossbeazley.wear.ScreenNavigationController;
import uk.co.rossbeazley.wear.android.ui.config.UIFactory;

public class FragmentScreenNavigationController <FragmentUIFactory extends Serializable & UIFactory> implements ScreenNavigationController {

    private final int leftID;
    private final int rightID;
    private final UIFactoryTransaction uiFactoryTransaction;
    private final FragmentUIFactory rightFACTORY;
    private final FragmentUIFactory leftFACTORY;

    public FragmentScreenNavigationController(int test_activity_LEFT_view_id, int test_activity_RIGHT_view_id, FragmentUIFactory rightFACTORY, FragmentUIFactory leftFACTORY, UIFactoryTransaction uiFactoryTransaction) {
        leftID = test_activity_LEFT_view_id;
        rightID = test_activity_RIGHT_view_id;
        this.rightFACTORY = rightFACTORY;
        this.leftFACTORY = leftFACTORY;
        this.uiFactoryTransaction = uiFactoryTransaction;
    }

    @Override
    public void showRight(Class uiPanel) {
        uiFactoryTransaction.add(this.rightFACTORY, this.rightID);
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
