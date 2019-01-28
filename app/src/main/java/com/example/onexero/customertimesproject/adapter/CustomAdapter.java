package com.example.onexero.customertimesproject.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onexero.customertimesproject.R;

public class CustomAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public CustomAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        mCursor.moveToPosition(i);
        itemViewHolder.textView1.setText(mCursor.getString(mCursor.getColumnIndex("ConnectionReceivedId")));
        itemViewHolder.textView2.setText(mCursor.getString(mCursor.getColumnIndex("ConnectionSentId")));
        itemViewHolder.textView3.setText(mCursor.getString(mCursor.getColumnIndex("CreatedById")));
        itemViewHolder.textView4.setText(mCursor.getString(mCursor.getColumnIndex("CreatedDate")));
        itemViewHolder.textView5.setText(mCursor.getString(mCursor.getColumnIndex("CurrencyIsoCode")));
        itemViewHolder.textView6.setText(mCursor.getString(mCursor.getColumnIndex("Id")));
        itemViewHolder.textView7.setText(mCursor.getString(mCursor.getColumnIndex("IsDeleted")));
        itemViewHolder.textView8.setText(mCursor.getString(mCursor.getColumnIndex("LastActivityDate")));
        itemViewHolder.textView9.setText(mCursor.getString(mCursor.getColumnIndex("LastModifiedById")));
        itemViewHolder.textView10.setText(mCursor.getString(mCursor.getColumnIndex("LastModifiedDate")));
        itemViewHolder.textView11.setText(mCursor.getString(mCursor.getColumnIndex("LastReferencedDate")));
        itemViewHolder.textView12.setText(mCursor.getString(mCursor.getColumnIndex("LastViewedDate")));
        itemViewHolder.textView13.setText(mCursor.getString(mCursor.getColumnIndex("Name")));
        itemViewHolder.textView14.setText(mCursor.getString(mCursor.getColumnIndex("OwnerId")));
        itemViewHolder.textView15.setText(mCursor.getString(mCursor.getColumnIndex("RecordTypeId")));
        itemViewHolder.textView16.setText(mCursor.getString(mCursor.getColumnIndex("SystemModstamp")));
    }

}
