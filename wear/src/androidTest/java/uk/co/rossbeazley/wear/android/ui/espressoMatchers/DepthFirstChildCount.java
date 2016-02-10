package uk.co.rossbeazley.wear.android.ui.espressoMatchers;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.util.HumanReadables;
import android.view.View;
import android.view.ViewGroup;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

public class DepthFirstChildCount {

    public static ViewAssertion hasNumberOfChildrenMatching(final int numberOfChildren, final Matcher<View> matching) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (view == null) {
                    throw new AssertionFailedError("No view to matched to count children of");
                }

                List<View> kids = new ArrayList<>();
                if (view instanceof ViewGroup) {
                    depthFirstSearch(view, kids);
                }

                if (kids.size() != numberOfChildren) {
                    String msg = HumanReadables.getViewHierarchyErrorMessage(view, kids, "Expected " + numberOfChildren + " children matching " + matching + " but found " + kids.size(), " ###matching### ");
                    throw new AssertionFailedError(msg);
                }
            }

            private void depthFirstSearch(View view, List<View> kids) {

                ViewGroup rootView = (ViewGroup) view;
                int childCount = rootView.getChildCount();
                for (int i = childCount; i > 0; i--) {
                    View childAt = rootView.getChildAt(i - 1);
                    if (childAt instanceof ViewGroup) {
                        depthFirstSearch(childAt, kids);
                    } else if (matching.matches(childAt)) {
                        kids.add(childAt);
                    }
                }

            }
        };
    }
}
