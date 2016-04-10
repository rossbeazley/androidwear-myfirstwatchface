package uk.co.rossbeazley.wear.android;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.co.rossbeazley.wear.android.ui.config.service.StringPersistence;

public class SharedPreferencesStringPersistence implements StringPersistence {

    public SharedPreferencesStringPersistence(Context targetContext) {
        this(targetContext, "SharedPreferencesStringPersistence");
    }

    public SharedPreferencesStringPersistence(Context targetContext, String sharedPrefsName) {
        this.context = targetContext.getApplicationContext();
        this.sharedPrefsName = sharedPrefsName;
        this.sharedPreferences = targetContext.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
    }


    @Override
    public List<String> stringsForKey(String key) {
        Set<String> stringSet = sharedPreferences.getStringSet(key, null);
        ArrayList<String> rtn = stringSet == null ? null : new ArrayList<>(stringSet);
        log("stringsForKey(" + key + ") ->" + (rtn));
        return rtn;
    }

    private void log(String message) {
        System.out.println("SharedPreferencesStringPersistence " + message);
    }

    @Override
    public boolean hasKey(String key) {
        boolean contains = sharedPreferences.contains(key);
        log("hasKey("+key+") -> " +  contains);
        return contains;
    }

    @Override
    public void storeStringsForKey(String currentItemId, List<String> strings) {
        // using a set for persistence, whereas api demands a list. will need to reimplement if the system demands duplicate values
        Set<String> values = new HashSet<>(strings);
        sharedPreferences.edit()
                .putStringSet(currentItemId, values)
                .commit();

        log("storeStringsForKey("+currentItemId+", "+strings+")");
    }

    public void destroy() {
        sharedPreferences.edit().clear().apply();
        String packageName = context.getPackageName();
        String sharedPrefsPath = "/data/data/" + packageName + "/shared_prefs/" + sharedPrefsName + ".xml";
        if(new File(sharedPrefsPath).delete()) System.out.println("DELETED " + sharedPrefsPath);
    }

    private final SharedPreferences sharedPreferences;
    private final String sharedPrefsName;
    private final Context context;

}
