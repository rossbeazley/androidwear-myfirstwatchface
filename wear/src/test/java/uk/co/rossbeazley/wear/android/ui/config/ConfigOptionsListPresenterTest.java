package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ConfigOptionsListPresenterTest {

    private List<String> expectedList;
    private CapturingConfigListView configListView;
    private ConfigService configService;
    private StubStringPersistence stubStringPersistence;

    @Before
    public void build() {
        expectedList = Arrays.asList("one", "two", "three");

        HashMap<String, List<String>> configItems = new HashMap<String, List<String>>() {{
            put("configItems", expectedList);
        }};

        configListView = new CapturingConfigListView();
        stubStringPersistence = new StubStringPersistence(configItems);
        configService = new ConfigService(stubStringPersistence);
        new ConfigOptionsPresenter(configService, configListView);
    }

    @Test
    public void presenterShowsTheConfigChoices() throws Exception {
        List<String> listPresented = configListView.presentedList;
        assertThat(listPresented,is(expectedList));
    }

    @Test
    public void presenterConfiguresSelectedChoice() {
        configListView.listener.itemSelected("two");
        assertThat(stubStringPersistence.queriedKey,is("two"));
    }

    private static class ConfigService {

        private StringPersistence persistence;

        public ConfigService(StringPersistence persistence) {
            this.persistence = persistence;
        }

        public void configure(String item) {
            persistence.hasKey(item);
        }

        public List<String> configItemsList() {
            return persistence.stringsForKey("configItems");
        }
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

    private class ConfigOptionsPresenter {
        public ConfigOptionsPresenter(final ConfigService configService, ConfigListView configListView) {
            configListView.addListener(new ConfigListView.Listener() {
                @Override
                public void itemSelected(String two) {
                    configService.configure(two);
                }
            });
            configListView.showConfigItems(configService.configItemsList());
        }
    }

    private interface StringPersistence {

        public List<String> stringsForKey(String key);

        void hasKey(String key);
    }

    private class StubStringPersistence implements StringPersistence {

        public String queriedKey;

        private HashMap<String, List<String>> map;

        public StubStringPersistence(HashMap<String, List<String>> map) {
            this.map = map;
        }

        @Override
        public List<String> stringsForKey(String key) {
            return map.get(key);
        }

        @Override
        public void hasKey(String key){
            this.queriedKey = key;
        }
    }
}