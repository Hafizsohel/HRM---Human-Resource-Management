package com.suffixit.hrm_suffix.Adapter;
/*

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.models.EmplyeeModel;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<EmplyeeModel> userList;
    private Context context;

    public EmployeeAdapter(List<EmplyeeModel> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.EmployeeViewHolder holder, int position) {
        EmplyeeModel user = userList.get(position);
        holder.usernameTextView.setText(user.getUsername());
        holder.nameTextView.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTextView;
        public TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }

    public class EmployeeViewHolder {
    }
}
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.models.EmplyeeModel;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<EmplyeeModel> emplyeeList;
    private Context context;

    public EmployeeAdapter(List<EmplyeeModel> emplyeeList, Context context) {
        this.emplyeeList = emplyeeList;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emplyee_item, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.EmployeeViewHolder holder, int position) {
        EmplyeeModel emplyee = emplyeeList.get(position);
        holder.username.setText(emplyee.getUsername());
        holder.nameText.setText(emplyee.getName());
    }

    @Override
    public int getItemCount() {
        return emplyeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView nameText;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.empID);
            nameText = itemView.findViewById(R.id.empName);
        }
    }
}

