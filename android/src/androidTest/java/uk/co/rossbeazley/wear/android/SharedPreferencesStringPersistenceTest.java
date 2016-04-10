package uk.co.rossbeazley.wear.android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class SharedPreferencesStringPersistenceTest {


    private SharedPreferencesStringPersistence sharedPreferencesStringPersistence;
    private String sharedPrefsName;
    private Context targetContext;
    private String itemId;

    @Test
    public void Create() {

        List<String> valuePersisted= Arrays.asList("TWO","ONE");
        sharedPreferencesStringPersistence.storeStringsForKey(itemId,valuePersisted);
        assertThat("Key not persisted",sharedPreferencesStringPersistence.hasKey(itemId),is(true));
    }

    @Test
    public void Retreive() {

        List<String> valuePersisted= Arrays.asList("TWO","ONE");
        sharedPreferencesStringPersistence.storeStringsForKey(itemId,valuePersisted);

        List<String> valueLoaded = sharedPreferencesStringPersistence.stringsForKey(itemId);
        assertThat("saved values not matching, order unimportant",valueLoaded,hasItems(valuePersisted.toArray(new String[]{})));
    }


    @Test
    public void RetreiveUnpersistedValue() {

        List<String> valueLoaded = sharedPreferencesStringPersistence.stringsForKey(itemId);
        assertThat("unsaved value to be loaded as null",valueLoaded,is(nullValue()));
    }



    @Test
    public void Update() {

        List<String> valuePersisted = Arrays.asList("123","ASD");
        sharedPreferencesStringPersistence.storeStringsForKey(itemId,valuePersisted);

        valuePersisted = Arrays.asList("TWO","ONE");
        sharedPreferencesStringPersistence.storeStringsForKey(itemId,valuePersisted);

        List<String> valueLoaded = sharedPreferencesStringPersistence.stringsForKey(itemId);
        assertThat("saved values not matching, order unimportant",valueLoaded,hasItems(valuePersisted.toArray(new String[]{})));
    }



    @Before
    public void buildStringPersistence() {
        sharedPrefsName = UUID.randomUUID().toString();
        targetContext = InstrumentationRegistry.getContext();
        sharedPreferencesStringPersistence =  new SharedPreferencesStringPersistence(targetContext, sharedPrefsName);

        itemId = "ANYID";
        assertThat("Key already exists before test run!", sharedPreferencesStringPersistence.hasKey(itemId),is(false));

    }

    @After
    public void clearPrefs() {
        sharedPreferencesStringPersistence.destroy();
    }

}
