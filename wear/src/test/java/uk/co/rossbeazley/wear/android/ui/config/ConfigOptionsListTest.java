package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ConfigOptionsListTest {

    @Test
    public void presenterShowsTheConfigChoices() throws Exception {

        List<String> expectedList = Arrays.asList("one","two","three") ;

        CapturingConfigListView configListView = new CapturingConfigListView();
        new ConfigOptionsPresenter(new StubConfigService(expectedList), configListView);

        List<String> listPresented = configListView.presentedList;
        assertThat(listPresented,is(expectedList));
    }

    private static class StubConfigService implements ConfigService {
        private final List<String> expectedList;

        public StubConfigService(List<String> expectedList) {
            this.expectedList = expectedList;
        }

        @Override
        public List<String> configItemsList() {
            return expectedList;
        }
    }

    private static class CapturingConfigListView implements ConfigListView {
        public List<String> presentedList;

        @Override
        public void showConfigItems(List<String> list) {
            presentedList = list;
        }
    }

    private class ConfigOptionsPresenter {
        public ConfigOptionsPresenter(ConfigService configService, ConfigListView configListView) {
            configListView.showConfigItems(configService.configItemsList());
        }
    }

    private interface ConfigService {
        List<String> configItemsList();
    }

    private interface ConfigListView {
        void showConfigItems(List<String> list);
    }
}