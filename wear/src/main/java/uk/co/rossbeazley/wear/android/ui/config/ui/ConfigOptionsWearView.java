package uk.co.rossbeazley.wear.android.ui.config.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import uk.co.rossbeazley.wear.R;

public class ConfigOptionsWearView extends FrameLayout implements ConfigOptionView {

    private WearableListView wearableListView;
    private Listener listener;

    void _ConfigOptionsWearView()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.list_wear_view, this);
        wearableListView = (WearableListView) findViewById(R.id.wearable_list);
        wearableListView.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                String text = String.valueOf(((TextView) viewHolder.itemView).getText());
                listener.itemSelected(text);
            }

            @Override
            public void onTopEmptyRegionClick() {

            }
        });

    }

    public ConfigOptionsWearView(Context context) {
        super(context);
        _ConfigOptionsWearView();
    }

    public ConfigOptionsWearView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _ConfigOptionsWearView();
    }

    public ConfigOptionsWearView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _ConfigOptionsWearView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ConfigOptionsWearView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        _ConfigOptionsWearView();
    }

    @Override
    public void showConfigOptions(List<String> configOptions) {
        wearableListView.setAdapter(new ConfigItemsListWearView.Adapter(configOptions));
    }

    @Override
    public void addListener(Listener capturingListener) {
        listener = capturingListener;
    }
}
