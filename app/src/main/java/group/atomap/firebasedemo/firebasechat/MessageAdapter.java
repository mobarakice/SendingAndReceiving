package group.atomap.firebasedemo.firebasechat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import group.atomap.firebasedemo.R;
import group.atomap.firebasedemo.Util;

/**
 * Created by Tauhid on 10/14/2016.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MVHolder> {

    private Context mContext;
    private ArrayList<Message> mDataset;

    public MessageAdapter(Context context, ArrayList<Message> mDataset) {
        mContext = context;
        this.mDataset = mDataset;

    }

    @Override
    public MVHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new MVHolder(view);
    }

    @Override
    public void onBindViewHolder(MVHolder holder, int position) {

        if (mDataset.get(position).getmSender().equalsIgnoreCase(ConversationActivity.mSender)) {
            holder.tvSend.setText(mDataset.get(position).getmText());
            holder.tvSendTime.setText(mDataset.get(position).getTime());
            holder.receiveLayout.setVisibility(View.GONE);
        } else {
            holder.tvReceive.setText(mDataset.get(position).getmText());
            holder.tvReceiveTime.setText(mDataset.get(position).getTime());
            holder.sendLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class MVHolder extends RecyclerView.ViewHolder {

        TextView tvSend, tvReceive, tvSendTime, tvReceiveTime;
        LinearLayout sendLayout, receiveLayout;

        public MVHolder(View itemView) {
            super(itemView);
            tvSend = (TextView) itemView.findViewById(R.id.send_message);
            tvReceive = (TextView) itemView.findViewById(R.id.receive_message);
            tvSendTime = (TextView) itemView.findViewById(R.id.send_time);
            tvReceiveTime = (TextView) itemView.findViewById(R.id.receive_time);
            sendLayout = (LinearLayout) itemView.findViewById(R.id.send);
            receiveLayout = (LinearLayout) itemView.findViewById(R.id.receive);
        }
    }
}
