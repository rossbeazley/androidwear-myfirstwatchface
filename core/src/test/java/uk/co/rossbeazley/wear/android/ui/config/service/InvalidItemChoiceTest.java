package uk.co.rossbeazley.wear.android.ui.config.service;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InvalidItemChoiceTest {

    private ConfigService configService;
    private TestConfigService testConfigService;

    @Before
    public void build() {
        testConfigService = new TestConfigService();
        configService = testConfigService.build();
    }

    @Test
    public void configServiceDosntAnnouncesSelectionIfNotAChoice() {
        CapturingConfigServiceListener listener = new CapturingConfigServiceListener();
        configService.addListener(listener);
        configService.configureItem("not in the list");
        assertThat(listener.configuredItem, is("UNKNOWN"));
    }

    @Test
    public void configServiceSharesKnowledgeAfterBadChoice() {
        CapturingConfigServiceListener listener = new CapturingConfigServiceListener();
        configService.addListener(listener);
        String noneExistentKey = "not in the list";
        configService.configureItem(noneExistentKey);
        ConfigServiceListener.KeyNotFound value = new ConfigServiceListener.KeyNotFound(noneExistentKey);
        assertThat(listener.keyNotFoundMessage, is(value));
    }


}