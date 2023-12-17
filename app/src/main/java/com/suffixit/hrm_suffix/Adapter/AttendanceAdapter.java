package com.suffixit.hrm_suffix.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.CheckinCheckoutListBinding;
import com.suffixit.hrm_suffix.databinding.ScrumListBinding;
import com.suffixit.hrm_suffix.models.AttendanceModel;
import com.suffixit.hrm_suffix.models.ReportModel;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    private Context context;
    private List<AttendanceModel> attendanceModelList;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CheckinCheckoutListBinding checkinCheckoutListBinding = CheckinCheckoutListBinding.inflate(layoutInflater, parent, false);
        return new AttendanceAdapter.ViewHolder(checkinCheckoutListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        AttendanceModel attendance = attendanceModelList.get(attendanceModelList.size() - 1 - position);
        holder.checkinCheckoutListBinding.setAttendance(attendance);

    }

    private String formatTotalHrsString(double totalHrs) {
        int hours = (int) totalHrs;
        int minutes = (int) ((totalHrs - hours) * 60);  // Extract minutes

        return String.format("%d:%02d %s", hours, minutes, (hours >= 12 ? "h" : "m"));
    }

    public void setData(List<AttendanceModel> attendanceModelList) {
        this.attendanceModelList = attendanceModelList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (attendanceModelList != null) {
            return Math.min(attendanceModelList.size(), 30);
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckinCheckoutListBinding checkinCheckoutListBinding;

        public ViewHolder(@NonNull CheckinCheckoutListBinding checkinCheckoutListBinding) {
            super(checkinCheckoutListBinding.getRoot());
            this.checkinCheckoutListBinding=checkinCheckoutListBinding;
        }
    }
}
