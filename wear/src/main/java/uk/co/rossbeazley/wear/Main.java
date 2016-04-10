package uk.co.rossbeazley.wear;

import android.content.Context;

import uk.co.rossbeazley.wear.android.SharedPreferencesStringPersistence;
import uk.co.rossbeazley.wear.android.gsm.GoogleWearApiConnection;
import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.service.StringPersistence;
import uk.co.rossbeazley.wear.months.MonthFactory;
import uk.co.rossbeazley.wear.ticktock.TickTock;

public class Main {

    private static Main instance;
    public final TickTock tickTock;

    public static Main instance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new Main(context);
    }


    public Main(final Context context) {
        System.out.println("MAIN CONSTRUCT INIT");
        initialiseMonthFactoryStrings(context);
        final Core core = Core.init(new SharedPreferencesStringPersistence(context));

        //Debug.waitForDebugger();
        tickTock = TickTock.createTickTock(core.canBeTicked);

        new GoogleWearApiConnection(context, new RotationMessage(core.canBeRotated));
        new GoogleWearApiConnection(context, new ColourMessage(core.canBeColoured));
    }

    private void initialiseMonthFactoryStrings(Context context) {
        String[] months = context.getResources().getStringArray(R.array.months);
        MonthFactory.registerMonthStrings(months);
    }

}
