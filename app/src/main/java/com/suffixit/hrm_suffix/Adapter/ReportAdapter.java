package com.suffixit.hrm_suffix.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.models.ReportModel;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private List<ReportModel> reportModelList;

    public ReportAdapter(List<ReportModel> reportModelList) {
        this.reportModelList = reportModelList;
    }

    @NonNull
        @Override
        public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.scrum_list, parent, false);
            return new ViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportModel report = reportModelList.get(position);
        holder.txtName.setText(report.getName());
        holder.txtID.setText(report.getUserId());
        holder.txtDate.setText(report.getDate());
    }


        @Override
        public int getItemCount() {
            return reportModelList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView txtID, txtName,txtDate, txtStatus;

            public ViewHolder(View itemView) {
                super(itemView);
                txtID = itemView.findViewById(R.id.txtID);
                txtName = itemView.findViewById(R.id.txtName);
                txtDate = itemView.findViewById(R.id.txtDate);
            }
        }
    }
