package com.laaptu.baking.utils;

import android.view.View;

import org.hamcrest.Matcher;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class CustomViewActions {
    public static int getTotalItemsFromRecyclerAdapter(int resId) {
        int[] counts = new int[1];
        counts[0] = -1;
        onView(withId(resId)).perform(new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return isAssignableFrom(RecyclerView.class);
            }

            @Override public String getDescription() {
                return "Getting RecyclerView Adapters total item count";
            }

            @Override public void perform(UiController uiController, View view) {
                if (view instanceof RecyclerView) {
                    RecyclerView recyclerView = (RecyclerView) view;
                    counts[0] = recyclerView.getAdapter().getItemCount();
                }
            }
        });
        return counts[0];
    }

    public static ViewAction clickRecyclerViewItemWithId(int resId) {
        return new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return isAssignableFrom(View.class);
            }

            @Override public String getDescription() {
                return "Performing click on recycler view item";
            }

            @Override public void perform(UiController uiController, View view) {
                view.findViewById(resId).performClick();
            }
        };
    }
}
