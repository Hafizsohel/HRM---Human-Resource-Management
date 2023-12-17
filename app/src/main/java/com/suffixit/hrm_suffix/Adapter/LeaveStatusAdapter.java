package com.suffixit.hrm_suffix.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.ScrumListBinding;
import com.suffixit.hrm_suffix.databinding.StatusListBinding;
import com.suffixit.hrm_suffix.models.LeaveStatusModel;
import com.suffixit.hrm_suffix.models.ReportModel;

import java.util.List;

public class LeaveStatusAdapter extends RecyclerView.Adapter<LeaveStatusAdapter.ViewHolder> {
    private List<LeaveStatusModel> leaveStatusModelList;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        StatusListBinding statusListBinding = StatusListBinding.inflate(layoutInflater, parent, false);
        return new LeaveStatusAdapter.ViewHolder(statusListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaveStatusModel status = leaveStatusModelList.get(position);
        holder.statusListBinding.setLeave(status);
    }

    public void setData(List<LeaveStatusModel> leaveStatusModelList) {
        this.leaveStatusModelList = leaveStatusModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (leaveStatusModelList != null) {
            return Math.min(leaveStatusModelList.size(), 30);
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        StatusListBinding statusListBinding;

        public ViewHolder(StatusListBinding statusListBinding) {
            super(statusListBinding.getRoot());
            this.statusListBinding = statusListBinding;
        }
    }
}
