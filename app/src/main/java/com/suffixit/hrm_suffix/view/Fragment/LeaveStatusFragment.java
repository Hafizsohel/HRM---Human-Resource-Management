package com.suffixit.hrm_suffix.view.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.suffixit.hrm_suffix.Adapter.LeaveStatusAdapter;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.models.LeaveStatusModel;
import com.suffixit.hrm_suffix.models.ReportModel;
import com.suffixit.hrm_suffix.preference.AppPreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LeaveStatusFragment extends Fragment {
    private static final String TAG = "LeaveStatusFragment";
    private DatabaseReference databaseReference;
    private LeaveStatusAdapter adapter;
    private List<LeaveStatusModel> leaveStatusList;
    private AppPreference localStorage;
    private TextView pleaseWaitText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave_status, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users").child("leave_applications");

        localStorage = new AppPreference(requireContext());
        String userId = localStorage.getUserName();

        // Initialize RecyclerView and adapter
        RecyclerView recyclerView = view.findViewById(R.id.recyclerStatusId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        leaveStatusList = new ArrayList<>();
        adapter = new LeaveStatusAdapter(leaveStatusList);
        recyclerView.setAdapter(adapter);

        // Fetch data from Firebase
        fetchLeaveStatus();


        return view;
    }

    private void fetchLeaveStatus() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                leaveStatusList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LeaveStatusModel leaveStatus = snapshot.getValue(LeaveStatusModel.class);

                    Log.d(TAG, "onDataChange: " + dataSnapshot);
                    if (leaveStatus != null) {
                        leaveStatusList.add(leaveStatus);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}