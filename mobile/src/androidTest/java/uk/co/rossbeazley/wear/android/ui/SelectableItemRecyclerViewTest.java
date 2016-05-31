package uk.co.rossbeazley.wear.android.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.ui.config.SelectableItemListView;
import uk.co.rossbeazley.wear.android.ui.config.UIFactory;
import uk.co.rossbeazley.wear.config.ConfigService;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.rossbeazley.wear.android.ui.DepthFirstChildCount.hasNumberOfChildrenMatching;

@RunWith(AndroidJUnit4.class)
public class SelectableItemRecyclerViewTest {

    private SelectableItemListView configOptionsWearView;

    @Rule
    public ActivityTestRule<TestActivity> activityTestRule = new ActivityTestRule<>(TestActivity.class);

    @Rule
    public UiThreadTestRule uiThreadTest = new UiThreadTestRule();

    @Before
    public void startTestActivityWithUIUnderTestrrs() throws Throwable {
        TestActivity activity = activityTestRule.getActivity();
        createUI(activity);
    }

    @Test
    public void showsOneConfigOption() throws Throwable {
        configOptionsWearView.showItems(Collections.singletonList("ANY"));
        Espresso.onView(withId(R.id.view_under_test))
                .check(hasNumberOfChildrenMatching(1, withText("ANY")));
    }

    @Test
    public void showsTwoConfigOption() throws Throwable {
        configOptionsWearView.showItems(Arrays.asList("ANY", "ANY"));
        Espresso.onView(withId(R.id.view_under_test))
                .check(hasNumberOfChildrenMatching(2, withText("ANY")));
    }

    @Test
    public void showsTwoDifferentConfigOption() throws Throwable {
        configOptionsWearView.showItems(Arrays.asList("ANY", "OLD"));
        Espresso.onView(withId(R.id.view_under_test))
                .check(hasNumberOfChildrenMatching(1, withText("ANY")))
                .check(hasNumberOfChildrenMatching(1, withText("OLD")));
    }


    @Test
    public void showslOTSDifferentConfigOption() throws Throwable {
        List<String> list = new ArrayList<>(6);
        list.add("ANY1");
        list.add("ANY2");
        list.add("ANY3");
        list.add("ANY4");
        list.add("ANY5");
        list.add("ANY6");

        configOptionsWearView.showItems(list);
        Espresso.onView(withId(R.id.list))
                .perform(RecyclerViewActions.scrollTo(withText("ANY4")));
    }


    @Test
    public void theOneWhereWeSelectAnItem() {
        configOptionsWearView.showItems(Arrays.asList("ANY1", "ANY2", "ANY3"));
        CapturingListener capturingListener = new CapturingListener();
        configOptionsWearView.addListener(capturingListener);
        Espresso.onView(withText("ANY1"))
                .perform(ViewActions.click());

        assertThat(capturingListener.itemSelected, is("ANY1"));
    }

    @Test
    public void theOneWhereWeSelectTheSecondItem() {
        configOptionsWearView.showItems(Arrays.asList("ANY1", "ANY2", "ANY3"));


        CapturingListener capturingListener = new CapturingListener();
        configOptionsWearView.addListener(capturingListener);

        Espresso.onView(withId(R.id.list))
                .perform(RecyclerViewActions.scrollTo(withText("ANY2")));
        Espresso.onView(withText("ANY2"))
                .perform(ViewActions.click());

        assertThat(capturingListener.itemSelected, is("ANY2"));
    }


    private void createUI(final TestActivity activity) throws Throwable {

        uiThreadTest.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View view = ConfigItemsMobileUIFactory.FACTORY.createView(activity.rootView);
                view.setId(R.id.view_under_test);
                activity.rootView.addView(view);
                configOptionsWearView = (SelectableItemListView) view;
            }
        });
    }

    public enum ConfigItemsMobileUIFactory implements UIFactory<SelectableItemListView> {
        FACTORY {
            @Override
            public View createView(ViewGroup container) {
                SelectableItemRecyclerView result = new SelectableItemRecyclerView(container.getContext());
                result.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                return result;
            }

            @Override
            public void createPresenters(ConfigService configService, SelectableItemListView view) {

            }
        };


    }

    public static class SelectableItemRecyclerView extends FrameLayout implements SelectableItemListView {


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
            recyclerView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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

    private class CapturingListener implements SelectableItemListView.Listener {
        public String itemSelected;

        public void itemSelected(String text) {
            this.itemSelected = text;
        }
    }


}
