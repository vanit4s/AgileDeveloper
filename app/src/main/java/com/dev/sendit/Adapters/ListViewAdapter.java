package com.dev.sendit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.sendit.ListDetails;
import com.dev.sendit.Models.ListModel;
import com.dev.sendit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<ListModel> modellist;
    ArrayList<ListModel> arrayList;

    String title, location, description;

    public ListViewAdapter(Context context, List<ListModel> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<ListModel>();
        this.arrayList.addAll(modellist);
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
            holder.mIcon = view.findViewById(R.id.ratingsIcon);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.mTitle.setText(modellist.get(pos).getTitle());
        holder.mLocation.setText(modellist.get(pos).getLocation());
        holder.mDescription.setText(modellist.get(pos).getDescription());
        holder.mIcon.setImageResource(modellist.get(pos).getIcon());

        title = modellist.get(pos).getTitle();
        location = modellist.get(pos).getLocation();
        description = modellist.get(pos).getDescription();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ListDetails.class);
                intent.putExtra("title", title);
                intent.putExtra("location", location);
                intent.putExtra("description", description);
                mContext.startActivity(intent);
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
                if (model.getTitle().toLowerCase(Locale.getDefault()).contains(text)) {
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }
}
