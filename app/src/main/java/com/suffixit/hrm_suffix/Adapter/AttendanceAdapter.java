package com.suffixit.hrm_suffix.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.models.AttendanceModel;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
        private Context context;
        private List<AttendanceModel> checkinCheckoutList;

        public AttendanceAdapter(Context context, List<AttendanceModel> checkinCheckoutList) {
            this.context = context;
            this.checkinCheckoutList = checkinCheckoutList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.checkin_checkout_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            AttendanceModel item = checkinCheckoutList.get(checkinCheckoutList.size() - 1 - position);

            holder.txtDate.setText(item.getDate());
            holder.txtDay.setText(item.getDay());
            holder.txtCheckIn.setText(item.getCheckInTime());
            holder.txtCheckOut.setText(item.getCheckoutTime());
            holder.txtTotalHrs.setText(item.getTotalHrs());
            holder.txtTotalHrs.setText(formatTotalHrsString(Double.parseDouble(item.getTotalHrs())));

        }

    private String formatTotalHrsString(double totalHrs) {
        int hours = (int) totalHrs;
        int minutes = (int) ((totalHrs - hours) * 60);  // Extract minutes

        return String.format("%d:%02d %s", hours, minutes, (hours >= 12 ? "h" : "m"));
    }


    @Override
        public int getItemCount() {
        return Math.min(checkinCheckoutList.size(), 30);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtDate, txtDay, txtCheckIn, txtCheckOut, txtTotalHrs;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtDate = itemView.findViewById(R.id.txtDate);
                txtDay = itemView.findViewById(R.id.txtDay);
                txtCheckIn = itemView.findViewById(R.id.txtCheckIn);
                txtCheckOut = itemView.findViewById(R.id.txtCheckOut);
                txtTotalHrs = itemView.findViewById(R.id.txtTotalHrs);
            }
        }
    }
