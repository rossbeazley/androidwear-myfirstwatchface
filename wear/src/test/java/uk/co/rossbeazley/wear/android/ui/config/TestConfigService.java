package uk.co.rossbeazley.wear.android.ui.config;

import java.util.Arrays;
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

    public ConfigService build() {



        expectedList = asList("one", "two", "three");
        oneList = asList("oneOne", "oneone", "oneThree", "oneFour");
        twoList = asList("twoOne", "twoTwo", "twoThree", "twoFour");
        threeList = asList("threeOne", "threethree", "threeThree", "threeFour");

        configItems = new HashMap<String, List<String>>() {{
            put("configItems", expectedListOfConfigItems());
            put("one",oneList);
            put("two", twoList);
            put("three", threeList);
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
}
