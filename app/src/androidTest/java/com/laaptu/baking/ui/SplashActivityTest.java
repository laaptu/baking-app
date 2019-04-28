package com.laaptu.baking.ui;

import com.laaptu.baking.R;
import com.laaptu.baking.utils.BaseActivityTest;
import com.laaptu.baking.utils.CustomViewActions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class SplashActivityTest extends BaseActivityTest {

    @Test
    public void verifyRecipeIsLoadedAndDisplayed() {
        registerIdlingResource(5);

        Espresso.onView(ViewMatchers.withId(R.id.rv_recipes_list))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()
                ));
        int totalRecipes = CustomViewActions.getTotalItemsFromRecyclerAdapter(R.id.rv_recipes_list);
        Assert.assertTrue(totalRecipes > 0);
    }
}
