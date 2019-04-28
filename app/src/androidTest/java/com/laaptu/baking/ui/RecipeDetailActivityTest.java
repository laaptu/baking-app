package com.laaptu.baking.ui;

import com.laaptu.baking.R;
import com.laaptu.baking.data.models.Recipe;
import com.laaptu.baking.utils.BaseActivityTest;
import com.laaptu.baking.utils.CustomViewActions;
import com.laaptu.baking.widget.data.WidgetRecipeProvider;

import org.junit.Test;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

public class RecipeDetailActivityTest extends BaseActivityTest {

    @Test
    public void verifyRecipeStepsIsShownForFirstItem() {
        clickFirstRecipe();

        onView(withId(R.id.rv_recipe_steps))
                .check(matches(isDisplayed()
                ));
    }


    @Test
    public void verifyRecipeIsAddedToWidget() {
        clickFirstRecipe();

        onView(withId(R.id.action_add_to_widget))
                .check(matches(isDisplayed()))
                .perform(click());

        WidgetRecipeProvider widgetRecipeProvider = new WidgetRecipeProvider();
        Recipe recipe = widgetRecipeProvider.getRecipe();

        assertNotNull(recipe);
    }

    private void clickFirstRecipe() {
        registerIdlingResource(5);

        onView(withId(R.id.rv_recipes_list))
                .check(matches(isDisplayed()
                ));
        onView(withId(R.id.rv_recipes_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        CustomViewActions.clickRecyclerViewItemWithId(R.id.container)));
    }

}
