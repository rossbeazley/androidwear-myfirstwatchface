package uk.co.rossbeazley.wear.android.ui.config;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

/**
 * Created by beazlr02 on 02/03/16.
 */
public class TestConfigService {
    private List<String> expectedList;

    private List<String> oneList;
    private List<String> twoList;
    private List<String> threeList;

    public StubStringPersistence stubStringPersistence;
    public ConfigService configService;
    private HashMap<String, List<String>> configItems;

    private Random random;
    private String oneChosen;
    private String twoChosen;
    private String threeChosen;

    public ConfigService build() {


/**
 * I need to change this to a list of domain objects.
 * Then have a way of persisting the default options in the system
 * it might seem more sensible to to introduce a item repo rather than a KV store
 * Going to try not doing this thus keeping the persistence mechanism a secret
 *
 */
        expectedList = asList("one", "two", "three");
        oneList = asList("oneOne", "oneone", "oneThree", "oneFour");
        twoList = asList("twoOne", "twoTwo", "twoThree", "twoFour");
        threeList = asList("threeOne", "threethree", "threeThree", "threeFour");
        oneChosen = "oneone";
        twoChosen = "twotwo";
        threeChosen = "threethree";




        configItems = new HashMap<String, List<String>>() {{
            put("configItems", expectedListOfConfigItems());
            put("one",oneList);
            put("two", twoList);
            put("three", threeList);
            put("oneChoice",asList(oneChosen));
            put("twoChoice",asList(twoChosen));
            put("threeChoice", asList(threeChosen));
        }};

        stubStringPersistence = new StubStringPersistence(configItems);
        configService = new ConfigService(stubStringPersistence);


        random = new Random();

        return configService;
    }

    public String anyItem() {

        return expectedList.get(random.nextInt(expectedList.size()));
    }

    public List<String> expectedListOfConfigItems() {
        return expectedList;
    }

    public List<String> expectedOptionsListForItem(String forItem) {
        return configItems.get(forItem);
    }

    public String expectedOptionListForItem(String anyItem) {
        List<String> strings = expectedOptionsListForItem(anyItem);
        String expectedOption = strings.get(random.nextInt(strings.size()));
        return expectedOption;
    }

    public String aDifferentItem(String anyItem) {
        String item = anyItem;
        do {
            item = anyItem();
        }
        while(anyItem.equals(item));
        return item;
    }
}
