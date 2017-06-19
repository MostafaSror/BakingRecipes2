package com.example.moustafamamdouh.bakingrecipes;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.moustafamamdouh.bakingrecipes.storage.DBContract;

import java.util.ArrayList;

/**
 * Created by Moustafa.Mamdouh on 6/19/2017.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    SharedPreferences sharedPreferences ;
    private ArrayList listItemList = new ArrayList();
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        sharedPreferences = context.getSharedPreferences("file" , Context.MODE_PRIVATE);

        populateListItem();
    }

    private void populateListItem() {
        Cursor recipesCursor = context.getContentResolver().query(DBContract.RecipesEntries.CONTENT_URI,
                null,
                null,
                null,
                null);

        int i = sharedPreferences.getInt("recipe_no" , 1);

        {
            Cursor cursor = context.getContentResolver().query(DBContract.IngredientsEntries.CONTENT_URI,
                    null,
                    DBContract.StepsEntries.COLUMN_RECIPE_ID + "=?",
                    new String[]{Integer.toString(i++)},
                    null);
            for (int y = 0 ; y < cursor.getCount() ; y++) {
                cursor.moveToPosition(y);

                ListItem listItem = new ListItem();
                listItem.heading = cursor.getString(cursor.getColumnIndex(DBContract.IngredientsEntries.COLUMN_INGREDIENT));

                listItemList.add(listItem);
            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(i>recipesCursor.getCount())
            i = 1;
        editor.putInt("recipe_no", i);
        editor.commit();

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /*
    *Similar to getView of Adapter where instead of View
    *we return RemoteViews
    *
    */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.recipe_widget_provider);
        ListItem listItem = (ListItem) listItemList.get(position);
        remoteView.setTextViewText(R.id.appwidget_text, listItem.heading);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }
}