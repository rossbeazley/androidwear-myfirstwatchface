package uk.co.rossbeazley.wear.android.ui.config.ui;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.service.CapturingConfigServiceListener;
import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigItemsListPresenterTest {

    private CapturingConfigItemsListView configListView;
    private ConfigService configService;
    private TestConfigService testConfigService;

    @Before
    public void build() {
        testConfigService = new TestConfigService();
        configService = testConfigService.build();

        ConfigItemsListFragment configItemsListFragment = new ConfigItemsListFragment();
        configItemsListFragment.attachConfigService(configService);


        configListView = new CapturingConfigItemsListView();
        configItemsListFragment.buildPresenters(configListView, null);
    }

    @Test
    public void presenterShowsTheConfigChoices() throws Exception {
        List<String> listPresented = configListView.presentedList;
        assertThat(listPresented,is(testConfigService.listOfConfigItems()));
    }

    @Test
    public void configServiceAnnouncesSelection() {
        CapturingConfigServiceListener listener = new CapturingConfigServiceListener();
        configService.addListener(listener);
        String anyItem = testConfigService.anyItemID();
        configListView.listener.itemSelected(anyItem);
        assertThat(listener.configuredItem, is(anyItem));
    }

    private static class CapturingConfigItemsListView implements ConfigItemsListView {
        public List<String> presentedList;
        public ConfigItemsListView.Listener listener;

        @Override
        public void showConfigItems(List<String> list) {
            presentedList = list;
        }

        @Override
        public void addListener(Listener listener) {
            this.listener = listener;
        }
    }

}