
package com.laaptu.baking.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.laaptu.baking.R;
import com.laaptu.baking.widget.data.WidgetRecipeProvider;
import com.laaptu.baking.data.models.Recipe;


public class WidgetListRemoteViews implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private Recipe recipe;
    private WidgetRecipeProvider widgetRecipeProvider = new WidgetRecipeProvider();

    public WidgetListRemoteViews(Context context) {
        this.context = context;
        updateRecipe();
    }

    private void updateRecipe() {
        recipe = widgetRecipeProvider.getRecipe();
    }

    @Override
    public void onDataSetChanged() {
        updateRecipe();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(context
                .getPackageName(), R.layout.widget_list_item);
        row.setTextViewText(R.id.txt_recipe_step, recipe.steps.get(position).shortDescription);
        return row;
    }

    @Override
    public int getCount() {
        return recipe.steps.size();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() { }

    @Override
    public void onDestroy() { }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }
}
