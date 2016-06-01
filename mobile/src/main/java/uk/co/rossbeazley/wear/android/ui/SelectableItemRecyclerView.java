package uk.co.rossbeazley.wear.android.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.ui.config.SelectableItemListView;

public class SelectableItemRecyclerView extends FrameLayout implements SelectableItemListView {


    private List<Listener> listeners;
    private RecyclerView recyclerView;

    public SelectableItemRecyclerView(Context context) {
        super(context);
        __ListRecylcerView();
    }

    public SelectableItemRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        __ListRecylcerView();
    }

    public SelectableItemRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        __ListRecylcerView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SelectableItemRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        __ListRecylcerView();
    }

    private void __ListRecylcerView() {
        listeners = new ArrayList<>(1);
        recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setId(R.id.list);
        addView(recyclerView);
    }

    @Override
    public void showItems(List<String> items) {
        Adapter adapter = new Adapter(items, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void addListener(Listener capturingListener) {
        this.listeners.add(capturingListener);
    }

    private void announce(String item) {
        for (Listener listener : listeners) {
            listener.itemSelected(item);
        }

    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        public ListViewHolder(TextView textview) {
            super(textview);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    private static class Adapter extends RecyclerView.Adapter<ListViewHolder> {
        private final List<String> configOptions;
        private final SelectableItemRecyclerView selectableItemRecyclerView;

        public Adapter(List<String> configOptions, SelectableItemRecyclerView selectableItemRecyclerView) {

            this.configOptions = configOptions;
            this.selectableItemRecyclerView = selectableItemRecyclerView;
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            super.onDetachedFromRecyclerView(recyclerView);

            // free resources here? maybe not, end up with island for gc
        }

        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textview = new TextView(parent.getContext());
            textview.setText("---");
            textview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textview.setGravity(Gravity.CENTER);
            textview.setTextSize(24f);
            Typeface sansSerifThin = Typeface.create("sans-serif-thin", Typeface.BOLD);
            textview.setTypeface(sansSerifThin);
            ListViewHolder holder = new ListViewHolder(textview);
            return holder;
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            final String text = configOptions.get(position);
            View itemView = holder.itemView;
            ((TextView) itemView).setText(text);
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectableItemRecyclerView.announce(text);
                }
            });
        }

        @Override
        public void onViewRecycled(ListViewHolder holder) {
            ((TextView) holder.itemView).setText("===");
        }

        @Override
        public int getItemCount() {
            return configOptions.size();
        }


    }
}
