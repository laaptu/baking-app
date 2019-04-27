package com.laaptu.baking.ui.recipeslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laaptu.baking.R;
import com.laaptu.baking.common.ui.BaseViewHolder;
import com.laaptu.baking.common.image.ImageLoadOptions;
import com.laaptu.baking.common.image.ImageLoader;
import com.laaptu.baking.data.models.Recipe;
import com.laaptu.baking.ui.recipeslist.RecipesListAdapter.RecipeViewHolder;
import com.squareup.otto.Bus;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private List<Recipe> recipes;
    private Bus eventBus;
    private ImageLoader imageLoader;
    private ImageLoadOptions.Builder imageLoadOptionsBuilder = new ImageLoadOptions.Builder();

    public RecipesListAdapter(List<Recipe> recipes, ImageLoader imageLoader, Bus eventBus) {
        this.recipes = recipes;
        this.imageLoader = imageLoader;
        this.eventBus = eventBus;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view, eventBus);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.setRecipe(recipe);

        Context context = holder.ivRecipe.getContext();
        holder.txtRecipeName.setText(recipe.name);
        holder.txtServings.setText(context.getString(R.string.total_servings, recipe.servings));
        holder.txtTotalIngredients.setText(context.getString(R.string.total_ingredients, recipe.ingredients
                .size()));
        holder.txtTotalSteps.setText(context.getString(R.string.total_steps, recipe.steps.size()));

        imageLoader.loadImage(imageLoadOptionsBuilder.setImageUrl(
            recipe.image
        ).build(), holder.ivRecipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class RecipeViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_recipe)
        ImageView ivRecipe;
        @BindView(R.id.txt_recipe_name)
        TextView txtRecipeName;
        @BindView(R.id.txt_servings)
        TextView txtServings;
        @BindView(R.id.txt_total_ingredients)
        TextView txtTotalIngredients;
        @BindView(R.id.txt_total_steps)
        TextView txtTotalSteps;

        private Bus eventBus;
        private Recipe recipe;


        public RecipeViewHolder(@NonNull View itemView, Bus eventBus) {
            super(itemView);
            this.eventBus = eventBus;
        }

        public void setRecipe(Recipe recipe) {
            this.recipe = recipe;
        }

        @OnClick(R.id.container)
        public void onClick() {
            eventBus.post(recipe);
        }
    }
}
