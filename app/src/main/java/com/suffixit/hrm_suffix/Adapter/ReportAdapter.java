package com.suffixit.hrm_suffix.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.ScrumListBinding;
import com.suffixit.hrm_suffix.models.ReportModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private List<ReportModel> reportModelList;


    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ScrumListBinding scrumListBinding = ScrumListBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(scrumListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportModel report = reportModelList.get(position);
        holder.scrumListBinding.setReportModel(report);
    }


    public void setData(List<ReportModel> reportModelList) {
        this.reportModelList = reportModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (reportModelList != null) {
            return Math.min(reportModelList.size(), 30);
        } else {
            return 0;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ScrumListBinding scrumListBinding;

        public ViewHolder(ScrumListBinding scrumListBinding) {
            super(scrumListBinding.getRoot());
            this.scrumListBinding = scrumListBinding;

        }
    }
}
