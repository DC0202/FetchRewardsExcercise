package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> items;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.tvListId.setText("ListId: " + item.getListId());
        holder.tvName.setText("Name: " + item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvListId, tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListId = itemView.findViewById(R.id.tv_listId);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
