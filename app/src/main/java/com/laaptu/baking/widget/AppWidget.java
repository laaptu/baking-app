package com.laaptu.baking.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

import com.laaptu.baking.widget.data.WidgetRecipeProvider;


public class AppWidget extends AppWidgetProvider {

    private WidgetUpdater widgetUpdater = new WidgetUpdater(new WidgetRecipeProvider());

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            widgetUpdater.updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

