package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ConfigOptionsListPresenterTest {

    private List<String> expectedList;
    private CapturingConfigListView configListView;
    private StubConfigService configService;
    private StubStringPersistence stubStringPersistence;

    @Before
    public void build() {
        expectedList = Arrays.asList("one", "two", "three");

        configListView = new CapturingConfigListView();
        stubStringPersistence = new StubStringPersistence(expectedList);
        configService = new StubConfigService(stubStringPersistence);
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

    private static class StubConfigService implements ConfigService {

        private StringPersistence persistence;

        public StubConfigService(StringPersistence persistence) {

            this.persistence = persistence;
        }

        @Override
        public void configure(String item) {
            persistence.hasKey(item);
        }

        @Override
        public List<String> configItemsList() {
            return persistence.stringsForKey("");
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

    private interface ConfigService {
        void configure(String item);
        List<String> configItemsList();
    }

    private interface StringPersistence {

        public List<String> stringsForKey(String key);

        void hasKey(String key);
    }

    private class StubStringPersistence implements StringPersistence {

        public String queriedKey;
        private List<String> expectedList;

        public StubStringPersistence(List<String> expectedList) {

            this.expectedList = expectedList;
        }

        @Override
        public List<String> stringsForKey(String key) {
            return expectedList;
        }

        @Override
        public void hasKey(String key){
            this.queriedKey = key;
        }
    }
}