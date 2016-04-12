package uk.co.rossbeazley.wear.android.ui.config.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.ui.config.ui.ConfigItemsListView;

/**
 * Created by beazlr02 on 19/02/16.
 */
public class ConfigItemsListWearView extends FrameLayout implements ConfigItemsListView {

    private WearableListView wearableListView;
    private CopyOnWriteArrayList<Listener> listeners;

    private void _ConfigOptionsListWearView() {
        setBackgroundColor(0xaa000000);

        listeners = new CopyOnWriteArrayList<>();
        LayoutInflater.from(getContext()).inflate(R.layout.list_wear_view, this);
        wearableListView = (WearableListView) findViewById(R.id.wearable_list);
        wearableListView.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                for (Listener listener : listeners) {
                    TextView textView = (TextView) viewHolder.itemView;
                    listener.itemSelected(String.valueOf(textView.getText()));
                }
            }

            @Override
            public void onTopEmptyRegionClick() {

            }
        });
    }


    public ConfigItemsListWearView(Context context) {
        super(context);
        _ConfigOptionsListWearView();
    }

    public ConfigItemsListWearView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _ConfigOptionsListWearView();
    }

    public ConfigItemsListWearView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _ConfigOptionsListWearView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ConfigItemsListWearView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        _ConfigOptionsListWearView();
    }

    @Override
    public void showConfigItems(List<String> list) {
        wearableListView.setAdapter(new Adapter(list));

    }

    @Override
    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public static final class Adapter extends WearableListView.Adapter {

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textview = new TextView(parent.getContext());
            textview.setText("---");
            textview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textview.setGravity(Gravity.CENTER);
            textview.setTextSize(30f);
            return new WearableListView.ViewHolder(textview);
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(list.get(position));
        }

        @Override
        public void onViewRecycled(WearableListView.ViewHolder holder) {
            ((TextView) holder.itemView).setText("===");
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        public Adapter(List<String> list) {
            this.list = list;
        }

        private final List<String> list;

    }
}
