package com.laaptu.baking.ui.recipedetail.steps;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.laaptu.baking.R;
import com.laaptu.baking.common.BaseViewHolder;
import com.laaptu.baking.data.models.Ingredient;
import com.laaptu.baking.data.models.Step;
import com.laaptu.baking.ui.recipedetail.steps.viewholders.IngredientsViewHolder;
import com.laaptu.baking.ui.recipedetail.steps.viewholders.StepViewHolder;
import com.squareup.otto.Bus;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeStepsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Step> steps;
    private List<Ingredient> ingredients;
    private Bus eventBus;

    private static final int TYPE_INGREDIENT = 0;
    private static final int TYPE_STEP = 1;

    public RecipeStepsAdapter(List<Step> steps, List<Ingredient> ingredients, Bus eventBus) {
        this.steps = steps;
        this.ingredients = ingredients;
        this.eventBus = eventBus;
    }

    @NonNull @Override public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_INGREDIENT) {
            return new IngredientsViewHolder(layoutInflater
                    .inflate(R.layout.ingredients_view, parent, false));
        }
        return new StepViewHolder(layoutInflater
                .inflate(R.layout.step_item, parent, false), eventBus);
    }

    @Override public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        if (holder instanceof IngredientsViewHolder) {
            ((IngredientsViewHolder) holder).bindView(ingredients);
        } else {
            int stepPosition = position - 1;
            Step step = steps.get(stepPosition);
            ((StepViewHolder) holder).bindView(step.shortDescription, stepPosition);
        }
    }

    @Override public int getItemCount() {
        return ingredients.size() + 1;
    }

    @Override public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_INGREDIENT;
        } else {
            return TYPE_STEP;
        }
    }
}
