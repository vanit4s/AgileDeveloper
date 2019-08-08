package com.dev.sendit.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev.sendit.MainActivity;
import com.dev.sendit.Models.ChatModel;
import com.dev.sendit.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter  extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context context;
    private List<ChatModel> chats;
    private String imageurl;
    private String first_name;

    private boolean msgsender = false;

    String facebook_id;

    public MessageAdapter(Context context, List<ChatModel> chats, String imageurl, String first_name) {
        this.context = context;
        this.chats = chats;
        this.imageurl = imageurl;
        this.first_name = first_name;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.my_message, viewGroup, false);
            msgsender = true;
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.their_message, viewGroup, false);
            msgsender = false;
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {
        ChatModel chat = chats.get(i);

        viewHolder.message_body.setText(chat.getMessage());

        if (msgsender == false) {
            viewHolder.receiverName.setText(first_name);
            Glide.with(context).load(imageurl).into(viewHolder.receiverIcon);
        }

        msgsender = true;
    }

    @Override
    public int getItemCount() {
        return chats.size();
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

        if (chats.get(position).getSender().equals(facebook_id)) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}