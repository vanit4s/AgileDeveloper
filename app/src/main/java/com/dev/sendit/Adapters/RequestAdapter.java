package com.dev.sendit.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev.sendit.MainActivity;
import com.dev.sendit.Models.ChatModel;
import com.dev.sendit.Models.RequestModel;
import com.dev.sendit.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    public static final int REQUEST_TYPE_LEFT = 0;
    public static final int REQUEST_TYPE_RIGHT = 1;

    private Context context;
    private List<RequestModel> requests;
    private String imageurl;
    private String first_name;

    private boolean msgsender = false;

    String facebook_id;

    public RequestAdapter(Context context, List<RequestModel> requests, String imageurl, String first_name) {
        this.context = context;
        this.requests = requests;
        this.imageurl = imageurl;
        this.first_name = first_name;
    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == REQUEST_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.my_request, viewGroup, false);
            msgsender = true;
            return new RequestAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.their_request, viewGroup, false);
            msgsender = false;
            return new RequestAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder viewHolder, int i) {
        if (msgsender == false) {
            viewHolder.receiverName.setText(first_name);
            Glide.with(context).load(imageurl).into(viewHolder.receiverIcon);
        }

        msgsender = true;
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView message_body, receiverName;
        public CircleImageView receiverIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            message_body = itemView.findViewById(R.id.message_body);
            receiverName = itemView.findViewById(R.id.receiverName);
            receiverIcon = itemView.findViewById(R.id.receiverIcon);
        }
    }

    @Override
    public int getItemViewType(int position) {
        facebook_id = MainActivity.shared.getString("ID", null);

        if (requests.get(position).getSender().equals(facebook_id)) {
            return REQUEST_TYPE_RIGHT;
        } else {
            return REQUEST_TYPE_LEFT;
        }
    }
}
