package group.atomap.firebasedemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import group.atomap.firebasedemo.firebasechat.ConversationActivity;
import group.atomap.firebasedemo.firebasechat.User;


/**
 * Created by Tauhid on 10/18/2016.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private Context mContext;
    private static ArrayList<User> mUsers;
    private int position;

    public UserListAdapter(Context mContext, ArrayList<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_list, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
//        holder.tvName.setText(mUsers.get(position).getUserName());
        holder.tvName.setText(mUsers.get(position).getUserName());
    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putCharSequence(Util.SENDER, UserList.currentUserId());
            bundle.putString(Util.RECEIVER, (mUsers.get(getLayoutPosition()).getUserName()));

            Intent intent = new Intent(v.getContext(), ConversationActivity.class);
            intent.putExtra(Util.FROM_USER_LIST, bundle);
            v.getContext().startActivity(intent);

        }
    }


}
