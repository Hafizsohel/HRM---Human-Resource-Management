package com.suffixit.hrm_suffix.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suffixit.hrm_suffix.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> dataList; // Replace 'String' with the type of data you want to display
    private Context context;

    // Constructor to initialize the adapter with data
    public MyAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    // Create a ViewHolder class to hold your item views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.recyclerView); // Replace with your item view's ID
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your item layout here
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_employee, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind your data to the views here
        String item = dataList.get(position);
        holder.itemTextView.setText(item);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

