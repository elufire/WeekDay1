package com.example.weekday1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weekday1.repository.Item;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ArrayList<Item> itemsArrayList;
    String TAG;

    public RecyclerViewAdapter(ArrayList<Item> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
         Item item = itemsArrayList.get(position);

        if (item != null) {
            String nodeId = item.getNodeId();
            String name = item.getName();
            String fullName = item.getFullName();
            viewHolder.setitem(item);
            viewHolder.tvNodeId.setText(nodeId);
            viewHolder.tvName.setText(name);
            viewHolder.tvFullName.setText(fullName);
        }
    }

    @Override
    public int getItemCount() {
        return itemsArrayList != null ? itemsArrayList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNodeId;
        TextView tvName;
        TextView tvFullName;

        Item itemitem;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvNodeId = itemView.findViewById(R.id.tvNodeId);
            tvName = itemView.findViewById(R.id.tvName);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            setitem(itemitem);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   EventBus.getDefault().post(new itemEvent(itemitem));
//                }
//            });
        }


        public void setitem(Item itemitem){ this.itemitem = itemitem;}
    }

    public void additem(Item item){
        //System.out.println(name);
        itemsArrayList.add(item);
        notifyDataSetChanged();
    }
}