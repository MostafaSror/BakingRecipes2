package com.example.moustafamamdouh.bakingrecipes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moustafamamdouh.bakingrecipes.storage.DBContract;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class RecipeDetailsActivity extends AppCompatActivity implements DetailsViewAdaptor.ListItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    @InjectView(R.id.Ingredients_button)
    Button ingredientsButton;

    @InjectView(R.id.Steps_button)
    Button bakingStepsButton;

    /*@InjectView(R.id.recipe_steps_list_view)
    ListView bakingStepsList;*/
    /*StepsListViewAdaptor stepsListViewAdaptor;
    StepsListViewAdaptor ingredientsListViewAdaptor;
    ListView IngredientsList;
*/
    RecyclerView IngredientsList;
    RecyclerView bakingStepsList;
    DetailsViewAdaptor stepsListViewAdaptor;
    DetailsViewAdaptor ingredientsListViewAdaptor;

    int recipe_no;
    boolean isTablet;

    private final static int SHOW_BAKING_STEPS_LOADER = 15 ;
    private final static int SHOW_INGREDIENTS_LOADER = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.inject(this);

        if (findViewById(R.id.player_fragment_container) != null){
            isTablet = true;
        }else {
            isTablet = false;
        }

        recipe_no = getIntent().getIntExtra(Intent.EXTRA_INTENT,0);

        stepsListViewAdaptor = new DetailsViewAdaptor(this, this);
        ingredientsListViewAdaptor = new DetailsViewAdaptor(this, this);

        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager ingredientsLayoutManager = new LinearLayoutManager(this);

        bakingStepsList = (RecyclerView) findViewById(R.id.recipe_steps_list_view);
        bakingStepsList.setLayoutManager(stepsLayoutManager);
        bakingStepsList.setAdapter(stepsListViewAdaptor);

        IngredientsList = (RecyclerView) findViewById(R.id.recipe_ingredients_list_view);
        IngredientsList.setLayoutManager(ingredientsLayoutManager);
        IngredientsList.setAdapter(ingredientsListViewAdaptor);

    //    ingredientsButton = (Button) findViewById(R.id.Ingredients_button);
    /*    ingredientsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IngredientsList.setVisibility(View.VISIBLE);
                bakingStepsList.setVisibility(View.INVISIBLE);
                showSteps(recipe_no,SHOW_INGREDIENTS_LOADER);
            }
        });*/
    //   bakingStepsButton = (Button) findViewById(R.id.Steps_button);
        /*bakingStepsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IngredientsList.setVisibility(View.INVISIBLE);
                bakingStepsList.setVisibility(View.VISIBLE);
                showSteps(recipe_no , SHOW_BAKING_STEPS_LOADER);
            }
        });*/
        /*bakingStepsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    if(cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_VIDEOURL)) != null){
                        if(isTablet){
                            Bundle args = new Bundle();
                            args.putString(DBContract.StepsEntries.COLUMN_VIDEOURL,
                                    cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_VIDEOURL)));
                            args.putString(DBContract.StepsEntries.COLUMN_DESCRIPTION,
                                    cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_DESCRIPTION)));
                            PlayerFragment fragment = new PlayerFragment();
                            fragment.setArguments(args);
                            getSupportFragmentManager().beginTransaction().add(R.id.player_fragment_container,fragment).commit();

                        }else{
                            Intent intent = new Intent(RecipeDetailsActivity.this , PlayerActivity.class);
                            intent.putExtra(DBContract.StepsEntries.COLUMN_VIDEOURL,
                                    cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_VIDEOURL)));
                            startActivity(intent);
                        }
                    }else if(cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_THUMBNAILURL)) != null){
                        if(isTablet){
                            Bundle args = new Bundle();
                            args.putString(DBContract.StepsEntries.COLUMN_VIDEOURL,
                                    cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_VIDEOURL)));
                            args.putString(DBContract.StepsEntries.COLUMN_DESCRIPTION,
                                    cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_DESCRIPTION)));
                            PlayerFragment fragment = new PlayerFragment();
                            fragment.setArguments(args);
                            getSupportFragmentManager().beginTransaction().add(R.id.player_fragment_container,fragment).commit();
                        }else {
                            Intent intent = new Intent(RecipeDetailsActivity.this, PlayerActivity.class);
                            intent.putExtra(DBContract.StepsEntries.COLUMN_VIDEOURL,
                                    cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_THUMBNAILURL)));
                            startActivity(intent);
                        }
                    }else{
                        Toast.makeText(RecipeDetailsActivity.this, "This step has no video", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });*/
        showSteps(recipe_no, SHOW_BAKING_STEPS_LOADER);
    }

    @OnClick(R.id.Ingredients_button)
    public void doIngredientsButton(){
        IngredientsList.setVisibility(View.VISIBLE);
        bakingStepsList.setVisibility(View.INVISIBLE);
        showSteps(recipe_no,SHOW_INGREDIENTS_LOADER);
    }
    @OnClick(R.id.Steps_button)
    public void doStepsButton(){
        IngredientsList.setVisibility(View.INVISIBLE);
        bakingStepsList.setVisibility(View.VISIBLE);
        showSteps(recipe_no , SHOW_BAKING_STEPS_LOADER);
    }
    /*@OnItemClick(R.id.recipe_steps_list_view)
    public void onRecipeItemClick(AdapterView<?> adapterView, int position) {
        Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
        if (cursor != null) {
            if(cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_VIDEOURL)) != null){
                    if(isTablet){
                        Bundle args = new Bundle();
                        args.putString(DBContract.StepsEntries.COLUMN_VIDEOURL,
                                cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_VIDEOURL)));
                        args.putString(DBContract.StepsEntries.COLUMN_DESCRIPTION,
                                cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_DESCRIPTION)));
                        PlayerFragment fragment = new PlayerFragment();
                        fragment.setArguments(args);
                        getSupportFragmentManager().beginTransaction().add(R.id.player_fragment_container,fragment).commit();

                    }else{
                        Intent intent = new Intent(RecipeDetailsActivity.this , PlayerActivity.class);
                        intent.putExtra(DBContract.StepsEntries.COLUMN_VIDEOURL,
                                cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_VIDEOURL)));
                        startActivity(intent);
                    }
            }else if(cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_THUMBNAILURL)) != null){
                if(isTablet){
                    Bundle args = new Bundle();
                    args.putString(DBContract.StepsEntries.COLUMN_VIDEOURL,
                            cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_VIDEOURL)));
                    args.putString(DBContract.StepsEntries.COLUMN_DESCRIPTION,
                            cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_DESCRIPTION)));
                    PlayerFragment fragment = new PlayerFragment();
                    fragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().add(R.id.player_fragment_container,fragment).commit();
                }else {
                    Intent intent = new Intent(RecipeDetailsActivity.this, PlayerActivity.class);
                    intent.putExtra(DBContract.StepsEntries.COLUMN_VIDEOURL,
                            cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_THUMBNAILURL)));
                    startActivity(intent);
                }
            }else{
                Toast.makeText(RecipeDetailsActivity.this, "This step has no video", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String temp = Integer.toString(args.getInt(DBContract.StepsEntries.COLUMN_RECIPE_ID));
        if(id == SHOW_BAKING_STEPS_LOADER) {
            return new CursorLoader(this,
                    DBContract.StepsEntries.CONTENT_URI,
                    null,
                    DBContract.StepsEntries.COLUMN_RECIPE_ID + "=?",
                    new String[]{temp},
                    null);
        }else {
            return new CursorLoader(this,
                    DBContract.IngredientsEntries.CONTENT_URI,
                    null,
                    DBContract.IngredientsEntries.COLUMN_RECIPE_ID + "=?",
                    new String[]{temp},
                    null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(loader.getId() == SHOW_BAKING_STEPS_LOADER)
            stepsListViewAdaptor.changeCursor(data);
        else
            ingredientsListViewAdaptor.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(loader.getId() == SHOW_BAKING_STEPS_LOADER)
            stepsListViewAdaptor.changeCursor(null);
        else
            ingredientsListViewAdaptor.changeCursor(null);
    }

    private void showSteps( int recipeNo , int loader_id ){
        Bundle args = new Bundle();
        args.putInt(DBContract.StepsEntries.COLUMN_RECIPE_ID,recipeNo);
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<ContentValues[]> queryStepsLoader = loaderManager.getLoader(SHOW_BAKING_STEPS_LOADER);
        Loader<ContentValues[]> queryIngredientsLoader = loaderManager.getLoader(SHOW_INGREDIENTS_LOADER);

        if(loader_id == SHOW_BAKING_STEPS_LOADER){
            if (queryStepsLoader == null){
                loaderManager.initLoader(SHOW_BAKING_STEPS_LOADER,args,this);
            }else {
                loaderManager.restartLoader(SHOW_BAKING_STEPS_LOADER,args,this);
            }
        } else {
            if (queryIngredientsLoader == null){
                loaderManager.initLoader(SHOW_INGREDIENTS_LOADER, args,this).forceLoad();
            }else {
                loaderManager.restartLoader(SHOW_INGREDIENTS_LOADER, args,this).forceLoad();
            }
        }
    }

    @Override
    public void onListItemClick(ContentValues contentValues) {
        if(contentValues.getAsString(DBContract.StepsEntries.COLUMN_VIDEOURL) != null){
            if(isTablet){
                Bundle args = new Bundle();
                args.putString(DBContract.StepsEntries.COLUMN_VIDEOURL,
                        contentValues.getAsString(DBContract.StepsEntries.COLUMN_VIDEOURL));
                args.putString(DBContract.StepsEntries.COLUMN_DESCRIPTION,
                        contentValues.getAsString(DBContract.StepsEntries.COLUMN_DESCRIPTION));
                PlayerFragment fragment = new PlayerFragment();
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction().add(R.id.player_fragment_container,fragment).commit();

            }else{
                Intent intent = new Intent(RecipeDetailsActivity.this , PlayerActivity.class);
                intent.putExtra(DBContract.StepsEntries.COLUMN_VIDEOURL,
                        contentValues.getAsString(DBContract.StepsEntries.COLUMN_VIDEOURL));
                startActivity(intent);
            }
        }else {
            Toast.makeText(RecipeDetailsActivity.this, "This step has no video", Toast.LENGTH_SHORT).show();
        }

    }
}
