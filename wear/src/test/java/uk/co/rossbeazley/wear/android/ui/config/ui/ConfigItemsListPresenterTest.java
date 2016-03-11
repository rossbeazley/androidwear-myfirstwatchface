package uk.co.rossbeazley.wear.android.ui.config.ui;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.CapturingConfigServiceListener;
import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

//TODO - Warning, testing UI and core here. needs split in two
//TODO - rename configOptions as config items
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
        configItemsListFragment.onViewCreated(configListView, null);
    }

    @Test
    public void presenterShowsTheConfigChoices() throws Exception {
        List<String> listPresented = configListView.presentedList;
        assertThat(listPresented,is(testConfigService.expectedListOfConfigItems()));
    }

    @Test
    public void configServiceAnnouncesSelection() {
        CapturingConfigServiceListener listener = new CapturingConfigServiceListener();
        configService.addListener(listener);
        String anyItem = testConfigService.anyItemID();
        configListView.listener.itemSelected(anyItem);
        assertThat(listener.configuredItem, is(anyItem));
    }


    @Test
    public void configServiceDosntAnnouncesSelectionIfNotAChoice() {
        CapturingConfigServiceListener listener = new CapturingConfigServiceListener();
        configService.addListener(listener);
        configListView.listener.itemSelected("not in the list");
        assertThat(listener.configuredItem, is("UNKNOWN"));
    }

    @Test
    public void configServiceSharesKnowledgeAfterBadChoice() {
        CapturingConfigServiceListener listener = new CapturingConfigServiceListener();
        configService.addListener(listener);
        String noneExistentKey = "not in the list";
        configListView.listener.itemSelected(noneExistentKey);
        ConfigService.Listener.KeyNotFound value = new ConfigService.Listener.KeyNotFound(noneExistentKey);
        assertThat(listener.keyNotFoundMessage, is(value));
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