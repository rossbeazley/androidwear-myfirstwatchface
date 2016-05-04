package uk.co.rossbeazley.wear.android.ui.config;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;

import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.wear.ui.config.UIEvents;

public class ConfigOptionSelectedFragment extends Fragment implements RaisesUIEvents {
    private UIEvents uiEvents;

    private Map<Integer, Runnable> activityResults = new HashMap<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showConfirmationTick(new Runnable(){
            @Override
            public void run() {
                System.out.println("TICKFINISHED TICKFINISHED TICKFINISHED TICKFINISHED TICKFINISHED ");
                uiEvents.optionSelectedFinished();
            }
        });
    }

    private void showConfirmationTick(Runnable resultCallback) {
        Intent intent = new Intent(getActivity(), ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                "Saved");
        final int requestCode = generateRequestCode();
        activityResults.put(requestCode,resultCallback);
        startActivityForResult(intent, requestCode);
    }

    private int generateRequestCode() {
        return 666;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        activityResults.get(requestCode).run();
    }

    @Override
    public void injectUIEventsDispatcher(UIEvents uiEvents) {
        this.uiEvents = uiEvents;
    }
}
