package com.dev.sendit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev.sendit.EditListingActivity;
import com.dev.sendit.ListDetails;
import com.dev.sendit.Models.ListModel;
import com.dev.sendit.Models.UserModel;
import com.dev.sendit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<ListModel> modellist;
    ArrayList<ListModel> arrayList;
    int code;

    String title, name, location, description, authorid, price;

    public ListViewAdapter(Context context, List<ListModel> modellist, int code) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<ListModel>();
        this.arrayList.addAll(modellist);
        this.code = code;
    }

    public class ViewHolder {
        TextView mTitle, mLocation, mDescription;
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
            view = inflater.inflate(R.layout.row, null);

            holder.mTitle = view.findViewById(R.id.listTitle);
            holder.mLocation = view.findViewById(R.id.ratingsName);
            holder.mDescription = view.findViewById(R.id.ratingsContent);
            holder.mIcon = view.findViewById(R.id.listingIcon);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.mTitle.setText(modellist.get(pos).getType());
        holder.mLocation.setText(modellist.get(pos).getLocation());
        holder.mDescription.setText(modellist.get(pos).getDescription());

        title = modellist.get(pos).getType();
        authorid = modellist.get(pos).getAuthor();
        name = modellist.get(pos).getName();
        location = modellist.get(pos).getLocation();
        description = modellist.get(pos).getDescription();
        price = modellist.get(pos).getPrice();

        final String image_URL = "https://graph.facebook.com/" + modellist.get(pos).getAuthor() + "/picture?type=normal";

        Glide.with(mContext).load(image_URL).into(holder.mIcon);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (code == 0) {
                    Intent intent = new Intent(mContext, ListDetails.class);
                    intent.putExtra("Type", title);
                    intent.putExtra("Author", authorid);
                    intent.putExtra("Name", name);
                    intent.putExtra("Location", location);
                    intent.putExtra("Description", description);
                    intent.putExtra("Price", price);
                    intent.putExtra("Image_URL", image_URL);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, EditListingActivity.class);
                    intent.putExtra("Type", title);
                    intent.putExtra("Author", authorid);
                    intent.putExtra("Name", name);
                    intent.putExtra("Location", location);
                    intent.putExtra("Description", description);
                    intent.putExtra("Price", price);
                    intent.putExtra("Image_URL", image_URL);
                    mContext.startActivity(intent);
                }

            }
        });

        return view;
    }

    public void filter(String text) {
        text = text.toLowerCase(Locale.getDefault());
        modellist.clear();

        if (text.length() == 0) {
            modellist.addAll(arrayList);
        } else {
            for (ListModel model : arrayList) {
                if (model.getType().toLowerCase(Locale.getDefault()).contains(text) ||
                    model.getLocation().toLowerCase(Locale.getDefault()).contains(text) ||
                    model.getDescription().toLowerCase(Locale.getDefault()).contains(text)) {
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }
}
