package uk.co.rossbeazley.wear.config;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.TestWorld;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InvalidItemChoiceTest {

    private uk.co.rossbeazley.wear.config.ConfigService configService;
    private TestWorld testWorld;

    @Before
    public void build() {
        testWorld = new TestWorld();
        configService = testWorld.build();
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
        uk.co.rossbeazley.wear.config.ConfigServiceListener.KeyNotFound value = new ConfigServiceListener.KeyNotFound(noneExistentKey);
        assertThat(listener.keyNotFoundMessage, is(value));
    }


}