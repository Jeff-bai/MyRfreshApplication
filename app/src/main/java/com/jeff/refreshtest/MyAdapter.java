package com.jeff.refreshtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author:baijianfeng
 * date:2017/7/7
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> implements MyItemTouchCallback.ItemTouchAdapter{
    private List<String> date = new ArrayList<>();
    private Context context;

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void setDate(List<String> list){
        date.addAll(list);
        notifyDataSetChanged();
    }

    public void setRefreshDate(List<String> list){
        date.clear();
        date.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.button.setText(date.get(position));
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
//        if (fromPosition==date.size()-1 || toPosition==date.size()-1){
//            return;
//        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(date, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(date, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        date.remove(position);
        notifyItemRemoved(position);
    }

    class MyHolder extends RecyclerView.ViewHolder{
        public MyHolder(View itemView) {
            super(itemView);
        }
        TextView button = (TextView)itemView.findViewById(R.id.btn);
    }
}
