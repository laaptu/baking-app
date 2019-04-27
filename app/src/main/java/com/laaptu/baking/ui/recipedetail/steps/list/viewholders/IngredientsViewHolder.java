package com.laaptu.baking.ui.recipedetail.steps.list.viewholders;

import android.view.View;
import android.widget.TextView;

import com.laaptu.baking.R;
import com.laaptu.baking.common.ui.BaseViewHolder;
import com.laaptu.baking.data.models.Ingredient;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import butterknife.BindView;

public class IngredientsViewHolder extends BaseViewHolder {
    @BindView(R.id.txt_ingredients) TextView txtIngredients;

    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bindView(List<Ingredient> ingredients) {
        StringBuilder ingredientsStr = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            ingredientsStr.append(String.format(Locale.getDefault(), "=> %s (%d %s)",
                    ingredient.name, (long)ingredient.quantity, ingredient.measure));
            ingredientsStr.append("\n");
        }
        txtIngredients.setText(ingredientsStr.toString());
    }
}
