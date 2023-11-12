package com.suffixit.hrm_suffix.view.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
        String userName = localStorage.getUserName();
        pleaseWaitText=view.findViewById(R.id.pleaseWaitText);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerStatusId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        leaveStatusList = new ArrayList<>();
        adapter = new LeaveStatusAdapter(leaveStatusList);
        recyclerView.setAdapter(adapter);


        Toolbar toolbar = view.findViewById(R.id.Toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        fetchLeaveStatus(userName);
        return view;
    }

 private void fetchLeaveStatus(String userId) {
     pleaseWaitText.setVisibility(View.VISIBLE);
     DatabaseReference usersReference = databaseReference;
     Query query = usersReference.orderByChild("userId").equalTo(userId);
     query.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             pleaseWaitText.setVisibility(View.GONE);
             leaveStatusList.clear();
             for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                 Log.d(TAG, "Snapshot: " + userSnapshot);

                 String userId = userSnapshot.child("userId").getValue(String.class);
                 String dateOfApplication = userSnapshot.child("dateOfApplication").getValue(String.class);
                 String status = userSnapshot.child("status").getValue(String.class);

                 if (userId != null && dateOfApplication != null && status != null) {
                     LeaveStatusModel statusModel = new LeaveStatusModel(userId, dateOfApplication, status);
                     leaveStatusList.add(statusModel);
                 }
                 Log.d(TAG, "onDataChange" + userSnapshot);
             }
             adapter.notifyDataSetChanged();
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {
             pleaseWaitText.setVisibility(View.GONE);
             Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
             Log.e(TAG, "Failed to fetch data: " + databaseError.getMessage());
         }
     });
 }
}