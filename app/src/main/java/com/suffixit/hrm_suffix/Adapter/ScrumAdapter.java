package com.suffixit.hrm_suffix.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.models.ScrumModel;
import java.util.List;

public class ScrumAdapter extends RecyclerView.Adapter<ScrumAdapter.ScrumViewHolder> {
    private List<ScrumModel> dataList;

    public ScrumAdapter(List<ScrumModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ScrumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scrum_list, parent, false);
        return new ScrumViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ScrumViewHolder holder, int position) {
        ScrumModel data = dataList.get(position);
        holder.txtID.setText(data.getUserId());
        holder.txtName.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class ScrumViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtName;

        public ScrumViewHolder(View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtName=itemView.findViewById(R.id.txtName);
        }
    }
}

