package com.example.moustafamamdouh.bakingrecipes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moustafamamdouh.bakingrecipes.storage.DBContract;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Moustafa.Mamdouh on 6/17/2017.
 */

public class DetailsViewAdaptor extends CursorAdaptor<DetailsViewAdaptor.ItemsViewHolder> {

    final private DetailsViewAdaptor.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(ContentValues contentValues);
    }

    public DetailsViewAdaptor(Context context, DetailsViewAdaptor.ListItemClickListener listener) {
        super(context);
        mOnClickListener = listener;
    }


    @Override
    public DetailsViewAdaptor.ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.baking_step_layout, parent, false);
        DetailsViewAdaptor.ItemsViewHolder viewHolder = new DetailsViewAdaptor.ItemsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailsViewAdaptor.ItemsViewHolder viewHolder, Cursor cursor) {
        viewHolder.bind(cursor);
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView bakingStep;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            bakingStep = (TextView) itemView.findViewById(R.id.baking_step_text_item);
            itemView.setOnClickListener(this);
        }

        void bind(Cursor cursor) {
            if(cursor.getColumnCount() == 7) {

                int tempInt = cursor.getInt(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_STEP_NO));
                String no = Integer.toString(tempInt);
                String temp = no + ". " +
                        cursor.getString(cursor.getColumnIndex(DBContract.StepsEntries.COLUMN_SHORT_DESCRIPTION));
                bakingStep.setText(temp);
            }else if (cursor.getColumnCount() == 5){
                int tempInt = cursor.getInt(cursor.getColumnIndex(DBContract.IngredientsEntries.COLUMN_QUANTITY));
                String no = Integer.toString(tempInt);
                String measure = cursor.getString(cursor.getColumnIndex(DBContract.IngredientsEntries.COLUMN_MEASURE));
                String ingredient = cursor.getString(cursor.getColumnIndex(DBContract.IngredientsEntries.COLUMN_INGREDIENT));
                String temp = no + " " + measure + " " + ingredient ;
                bakingStep.setText(temp);
            }
        }

        @Override
        public void onClick(View v) {

            int clickedPosition = getAdapterPosition();
            mCursor.moveToPosition(clickedPosition);
            ContentValues cv = new ContentValues();
            if(mCursor.getString(mCursor.getColumnIndex(DBContract.StepsEntries.COLUMN_VIDEOURL)) != null){
                cv.put(DBContract.StepsEntries.COLUMN_VIDEOURL,
                        mCursor.getString(mCursor.getColumnIndex(DBContract.StepsEntries.COLUMN_VIDEOURL)));
                cv.put(DBContract.StepsEntries.COLUMN_DESCRIPTION,
                        mCursor.getString(mCursor.getColumnIndex(DBContract.StepsEntries.COLUMN_DESCRIPTION)));

            }else if(mCursor.getString(mCursor.getColumnIndex(DBContract.StepsEntries.COLUMN_THUMBNAILURL)) != null){
                cv.put(DBContract.StepsEntries.COLUMN_VIDEOURL,
                        mCursor.getString(mCursor.getColumnIndex(DBContract.StepsEntries.COLUMN_THUMBNAILURL)));
                cv.put(DBContract.StepsEntries.COLUMN_DESCRIPTION,
                        mCursor.getString(mCursor.getColumnIndex(DBContract.StepsEntries.COLUMN_DESCRIPTION)));
            }
            mOnClickListener.onListItemClick(cv);
        }
    }
}