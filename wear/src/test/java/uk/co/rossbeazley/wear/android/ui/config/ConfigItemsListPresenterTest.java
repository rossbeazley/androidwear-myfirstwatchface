package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import uk.co.rossbeazley.wear.config.CapturingConfigServiceListener;
import uk.co.rossbeazley.wear.TestWorld;
import uk.co.rossbeazley.wear.config.ConfigService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigItemsListPresenterTest {

    private CapturingConfigItemsListView configListView;
    private ConfigService configService;
    private TestWorld testWorld;

    @Before
    public void build() {
        testWorld = new TestWorld();
        configService = testWorld.build();

        configListView = new CapturingConfigItemsListView();
        ConfigItemsListFragmentFactory.ConfigItemsListUIFactory.FACTORY.createPresenters(configService, configListView);
    }

    @Test
    public void presenterShowsTheConfigChoices() throws Exception {
        List<String> listPresented = configListView.presentedList;
        assertThat(listPresented,is(testWorld.listOfConfigItems()));
    }

    @Test
    public void configServiceAnnouncesSelection() {
        CapturingConfigServiceListener listener = new CapturingConfigServiceListener();
        configService.addListener(listener);
        String anyItem = testWorld.anyItemID();
        configListView.listener.itemSelected(anyItem);
        assertThat(listener.configuredItem, is(anyItem));
    }

    private static class CapturingConfigItemsListView implements SelectableItemListView {
        public List<String> presentedList;
        public SelectableItemListView.Listener listener;

        @Override
        public void showItems(List<String> items) {
            presentedList = items;
        }

        @Override
        public void addListener(Listener listener) {
            this.listener = listener;
        }
    }

}