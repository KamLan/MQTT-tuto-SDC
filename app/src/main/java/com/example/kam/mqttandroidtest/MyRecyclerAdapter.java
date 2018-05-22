package com.example.kam.mqttandroidtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kam on 04/01/18.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomHolder> {

    private ArrayList<ReceivedMessage> items;

    public MyRecyclerAdapter(ArrayList<ReceivedMessage> items) {
        this.items = items;
    }

    @Override public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new CustomHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomHolder holder, int position) {
        ReceivedMessage item = items.get(position);
        holder.setData(item);
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.mqtt_message;
    }

    @Override public int getItemCount() {
        return items.size();
    }

    public static class CustomHolder extends RecyclerView.ViewHolder {
        public TextView topic;
        public TextView message;


        public CustomHolder(View itemView) {
            super(itemView);
            topic = (TextView) itemView.findViewById(R.id.topic);
            message = (TextView) itemView.findViewById(R.id.message);
        }

        public void setData(ReceivedMessage receivedMessagee){
            topic.setText(receivedMessagee.getTopic());
            message.setText(receivedMessagee.getMessage().toString());
        }
    }
}
