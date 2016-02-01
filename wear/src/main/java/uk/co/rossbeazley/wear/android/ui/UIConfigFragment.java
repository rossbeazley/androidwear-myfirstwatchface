package uk.co.rossbeazley.wear.android.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.colour.CanReceiveColourUpdates;
import uk.co.rossbeazley.wear.colour.Colours;

public class UIConfigFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.rotate, container, false);


        Main.instance().tickTock.start();

        final Core core = Core.instance();

        core.canBeObservedForChangesToColour.addListener(new CanReceiveColourUpdates() {
            @Override
            public void colourUpdate(Colours to) {
                view.findViewById(R.id.rotate_container).setBackgroundColor(to.background().toInt());
            }
        });

        view.findViewById(R.id.rotate_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                core.canBeRotated.right();
            }
        });

        view.findViewById(R.id.black_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                core.canBeColoured.background(Colours.Colour.BLACK);
            }
        });

        view.findViewById(R.id.white_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                core.canBeColoured.background(Colours.Colour.WHITE);
            }
        });


        return view;
    }
}
