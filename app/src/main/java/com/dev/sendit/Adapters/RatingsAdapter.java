package com.dev.sendit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dev.sendit.ListDetails;
import com.dev.sendit.Models.ListModel;
import com.dev.sendit.Models.RatingModel;
import com.dev.sendit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RatingsAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<RatingModel> modellist;
    ArrayList<RatingModel> arrayList;

    String name, content;
    int rating;

    public RatingsAdapter(Context context, List<RatingModel> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<RatingModel>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder {
        TextView mName, mContent;
        RatingBar mRating;
        ImageView mIcon;
    }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.ratings_row, null);

            holder.mName = view.findViewById(R.id.ratingsName);
            holder.mRating = view.findViewById(R.id.ratingBar);
            holder.mContent = view.findViewById(R.id.ratingsContent);
            holder.mIcon = view.findViewById(R.id.ratingsIcon);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.mName.setText(modellist.get(pos).getName());
        holder.mRating.setRating(modellist.get(pos).getRating());
        holder.mContent.setText(modellist.get(pos).getContent());
        holder.mIcon.setImageResource(modellist.get(pos).getIcon());

        name = modellist.get(pos).getName();
        rating = modellist.get(pos).getRating();
        content = modellist.get(pos).getContent();

        return view;
    }
}
