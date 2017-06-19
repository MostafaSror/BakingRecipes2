package com.example.moustafamamdouh.bakingrecipes.Services;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.moustafamamdouh.bakingrecipes.ListProvider;

/**
 * Created by Moustafa.Mamdouh on 6/19/2017.
 */

public class WidgetService extends RemoteViewsService {
/*
* So pretty simple just defining the Adapter of the listview
* here Adapter is ListProvider
* */

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        return (new ListProvider(this.getApplicationContext(), intent));
    }

    public static void changeRecipeOnWidget(Context context){
        Intent intent = new Intent(context , WidgetService.class);
        context.startService(intent);
    }

}

