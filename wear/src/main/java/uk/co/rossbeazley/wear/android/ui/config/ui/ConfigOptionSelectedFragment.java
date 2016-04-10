package uk.co.rossbeazley.wear.android.ui.config.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;

public class ConfigOptionSelectedFragment extends Fragment implements RaisesUIEvents {
    private UIEvents uiEvents;

    public String tag() {
        return "ConfigOptionSelectedFragment";
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = new Intent(getActivity(), ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                "Saved");
        startActivityForResult(intent,666);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==666) {
            System.out.println("TICKFINISHED TICKFINISHED TICKFINISHED TICKFINISHED TICKFINISHED ");
        }
        uiEvents.optionSelectedFinished();
    }

    @Override
    public void injectUIEventsDispatcher(UIEvents uiEvents) {

        this.uiEvents = uiEvents;
    }
}
