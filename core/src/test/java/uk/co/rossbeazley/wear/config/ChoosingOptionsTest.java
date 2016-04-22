package uk.co.rossbeazley.wear.config;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import uk.co.rossbeazley.wear.TestWorld;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class ChoosingOptionsTest {

    private TestWorld testWorld;
    private uk.co.rossbeazley.wear.config.ConfigService configService;
    private String anyItem;
    private CapturingConfigServiceListener capturingConfigServiceListener;

    @Before
    public void buildTestWorld() {

        testWorld = new TestWorld();
        configService = testWorld.build();

        anyItem = testWorld.anyItemID();

        configService.configureItem(anyItem);
        capturingConfigServiceListener = new CapturingConfigServiceListener();
        configService.addListener(capturingConfigServiceListener);
    }
    
    @Test
    public void
    theOneWeRetrieveTheSelectedConfigOption() {
        List<String> selectedConfigOptions = configService.selectedConfigOptions();
        String[] allTheOnes = testWorld.optionsListForItem(anyItem).toArray(new String[]{});
        assertThat(selectedConfigOptions,hasItems(allTheOnes));
    }


    @Test
    public void
    theOneWhereWeChooseAConfigItemOption() {

        String expectedOption = testWorld.anyOptionForItem(anyItem);
        configService.chooseOption(expectedOption);

        String optionForItem = configService.currentOptionForItem(anyItem);

        assertThat(optionForItem,is(expectedOption));
    }

    @Test
    public void
    theOneWhereWeChooseADifferentConfigItemOption() {

        String expectedOption = testWorld.anyOptionForItem(anyItem);
        configService.chooseOption(expectedOption);

        String optionForItem = configService.currentOptionForItem(testWorld.aDifferentItem(anyItem));

        assertThat(optionForItem,is(not(expectedOption)));
    }


    @Test
    public void
    configChoiceIsAnnounced() {
        String expectedOption = testWorld.anyOptionForItem(anyItem);
        configService.chooseOption(expectedOption);

        configService.currentOptionForItem(anyItem);

        assertThat(capturingConfigServiceListener.configuredOption, is(expectedOption));
    }
}
