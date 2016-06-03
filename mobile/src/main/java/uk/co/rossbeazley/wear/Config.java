package uk.co.rossbeazley.wear;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.wear.android.ui.ChosenOptionMobileUIFactory;
import uk.co.rossbeazley.wear.android.ui.ConfigItemOptionsMobileUIFactory;
import uk.co.rossbeazley.wear.android.ui.ConfigItemsMobileUIFactory;
import uk.co.rossbeazley.wear.android.ui.FragmentScreenNavigationController;
import uk.co.rossbeazley.wear.android.ui.SelectAnItemMobileUIFactory;
import uk.co.rossbeazley.wear.android.ui.SetTextOnMainThread;
import uk.co.rossbeazley.wear.android.ui.UIFactoryFragmentTransaction;
import uk.co.rossbeazley.wear.android.ui.config.NeedsConfigService;
import uk.co.rossbeazley.wear.android.ui.config.SelectableItemListView;
import uk.co.rossbeazley.wear.android.ui.config.UIFactory;
import uk.co.rossbeazley.wear.config.ConfigService;
import uk.co.rossbeazley.wear.ticktock.TickTock;

public class Config extends Activity {


    private Broadcast broadcast = null;
    private TickTock tickTock;
    private MobileUINavigation mobileUINavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcast = new Nodes(this);
        tickTock = TickTock.createTickTock(Core.instance().canBeTicked);
        SetTextOnMainThread.strategy = new SetTextOnMainThread.PostingStrategy();
        Core.instance().configService.addListener(new WearBroadcastConfigServiceListener(broadcast));
        createView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tickTock.stop();
    }

    private void createView() {
        setContentView(R.layout.config);
        UIFactoryFragmentTransaction uiFactoryTransaction = new UIFactoryFragmentTransaction(getFragmentManager());
        Serializable leftFactory = ConfigItemsMobileUIFactory.FACTORY;
        Map<Class, UIFactory> factories = new HashMap<>();
        factories.put(SelectableItemListView.class, ConfigItemOptionsMobileUIFactory.FACTORY);
        factories.put(SelectAnItemView.class, SelectAnItemMobileUIFactory.FACTORY);
        factories.put(ChosenOptionView.class, ChosenOptionMobileUIFactory.FACTORY);

        FragmentScreenNavigationController fragmentScreenNavigationController = new FragmentScreenNavigationController(R.id.left_menu, R.id.right_menu, factories, leftFactory, uiFactoryTransaction);

        ConfigService configService = Core.instance().configService;
        mobileUINavigation = new MobileUINavigation(fragmentScreenNavigationController, configService);
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof NeedsConfigService) {
            ((NeedsConfigService) fragment).attachConfigService(Core.instance().configService);
        }
    }

}
