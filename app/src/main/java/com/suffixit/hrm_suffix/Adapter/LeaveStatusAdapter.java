package com.suffixit.hrm_suffix.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.models.LeaveStatusModel;

import java.util.List;

public class LeaveStatusAdapter extends RecyclerView.Adapter<LeaveStatusAdapter.ViewHolder> {
    private List<LeaveStatusModel> leaveStatusModelList;

    public LeaveStatusAdapter(List<LeaveStatusModel> leaveStatusModelList) {
        this.leaveStatusModelList = leaveStatusModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaveStatusModel status = leaveStatusModelList.get(position);

        if (status != null) {
            holder.txtDateOfApplication.setText(status.getDateOfApplication());
            holder.txtUserId.setText(status.getUserId());
        }
    }

    @Override
    public int getItemCount() {
        return leaveStatusModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDateOfApplication,txtUserId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDateOfApplication = itemView.findViewById(R.id.txtApplicationDate);
            txtUserId = itemView.findViewById(R.id.txtUserID);
        }
    }
}
