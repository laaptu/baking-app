package com.laaptu.baking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.laaptu.baking.R;
import com.laaptu.baking.widget.data.WidgetRecipeProvider;
import com.laaptu.baking.data.models.Recipe;
import com.laaptu.baking.ui.recipedetail.RecipeDetailActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WidgetUpdater {

    private WidgetRecipeProvider widgetRecipeProvider;

    @Inject
    public WidgetUpdater(WidgetRecipeProvider widgetRecipeProvider) {
        this.widgetRecipeProvider = widgetRecipeProvider;
    }

    public void updateWidget(Context context, Recipe recipe) {
        widgetRecipeProvider.saveRecipe(recipe);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds =
                appWidgetManager.getAppWidgetIds(new ComponentName(context, AppWidget.class));
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Recipe recipe = widgetRecipeProvider.getRecipe();
        if (recipe != null) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    RecipeDetailActivity.getLaunchingIntent(context, recipe), 0);

            RemoteViews views =
                    new RemoteViews(context.getPackageName(), R.layout.widget_recipe_view);

            views.setTextViewText(R.id.txt_recipe_name, recipe.name);
            views.setOnClickPendingIntent(R.id.txt_recipe_name, pendingIntent);

            Intent intent = new Intent(context, AppWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            views.setRemoteAdapter(R.id.lv_recipe_steps, intent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager
                    .notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_recipe_steps);
        }
    }
}
