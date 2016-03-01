package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

//TODO - Warning, testing UI and core here. needs split in two
//TODO - rename configOptions as config items
public class ConfigOptionsListPresenterTest {

    private CapturingConfigListView configListView;
    private ConfigService configService;
    private TestConfigService testConfigService;

    @Before
    public void build() {
        testConfigService = new TestConfigService();
        configService = testConfigService.build();

        ConfigOptionsListFragment configOptionsListFragment = new ConfigOptionsListFragment();
        configOptionsListFragment.attachConfigService(configService);


        configListView = new CapturingConfigListView();
        configOptionsListFragment.onViewCreated(configListView, null);
    }

    @Test
    public void presenterShowsTheConfigChoices() throws Exception {
        List<String> listPresented = configListView.presentedList;
        assertThat(listPresented,is(testConfigService.expectedListOfConfigItems()));
    }

    @Test
    public void configServiceAnnouncesSelection() {
        CapturingListener listener = new CapturingListener();
        configService.addListener(listener);
        String anyItem = testConfigService.anyItem();
        configListView.listener.itemSelected(anyItem);
        assertThat(listener.configuredItem, is(anyItem));
    }


    @Test
    public void configServiceDosntAnnouncesSelectionIfNotAChoice() {
        CapturingListener listener = new CapturingListener();
        configService.addListener(listener);
        configListView.listener.itemSelected("not in the list");
        assertThat(listener.configuredItem, is("UNKNOWN"));
    }

    @Test
    public void configServiceSharesKnowledgeAfterBadChoice() {
        CapturingListener listener = new CapturingListener();
        configService.addListener(listener);
        String noneExistentKey = "not in the list";
        configListView.listener.itemSelected(noneExistentKey);
        ConfigService.Listener.KeyNotFound value = new ConfigService.Listener.KeyNotFound(noneExistentKey);
        assertThat(listener.keyNotFoundMessage, is(value));
    }

    private static class CapturingConfigListView implements ConfigListView {
        public List<String> presentedList;
        public ConfigListView.Listener listener;

        @Override
        public void showConfigItems(List<String> list) {
            presentedList = list;
        }

        @Override
        public void addListener(Listener listener) {
            this.listener = listener;
        }
    }

    private static class CapturingListener implements ConfigService.Listener {
        public String configuredItem = "UNKNOWN";
        public KeyNotFound keyNotFoundMessage;

        @Override
        public void configuring(String item) {
            this.configuredItem = item;
        }

        @Override
        public void error(KeyNotFound keyNotFound) {
            keyNotFoundMessage = keyNotFound;
        }
    }

    public static class TestConfigService {
        private List<String> expectedList;
        private List<String> twoList;
        public StubStringPersistence stubStringPersistence;

        public ConfigService configService;

        public ConfigService build() {
            expectedList = Arrays.asList("one", "two", "three");
            twoList = Arrays.asList("twoOne","twoTwo","twoThree","twoFour");
            HashMap<String, List<String>> configItems = new HashMap<String, List<String>>() {{
                put("configItems", expectedListOfConfigItems());
                put("two", expectedOptionsListForItem("two"));
            }};

            stubStringPersistence = new StubStringPersistence(configItems);
            configService = new ConfigService(stubStringPersistence);

            return configService;
        }

        public String anyItem() {
            return "two";
        }

        public List<String> expectedListOfConfigItems() {
            return expectedList;
        }

        public List<String> expectedOptionsListForItem(String forItem) {
            return twoList;
        }
    }
}