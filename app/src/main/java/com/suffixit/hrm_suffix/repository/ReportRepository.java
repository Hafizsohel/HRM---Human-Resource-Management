package com.suffixit.hrm_suffix.repository;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.suffixit.hrm_suffix.Adapter.ReportAdapter;
import com.suffixit.hrm_suffix.databinding.FragmentReportBinding;
import com.suffixit.hrm_suffix.models.ReportModel;
import com.suffixit.hrm_suffix.preference.AppPreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


   /* public LiveData<List<ReportModel>> getReportData() {

        localStorage = new AppPreference(requireContext());
        String userId = localStorage.getUserName();

        DatabaseReference usersReference = databaseReference.child("Users").child("username");

        Query query = usersReference.orderByChild("userId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pleaseWaitText.setVisibility(View.GONE);
                reportModelList.clear();

                List<String> existingDates = new ArrayList<>();

                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.child("userId").getValue(String.class);
                        String name = userSnapshot.child("name").getValue(String.class);
                        String date = userSnapshot.child("date").getValue(String.class);

                        if (userId != null && name != null && date != null) {

                            ReportModel report = new ReportModel(userId, name, date, "P");
                            reportModelList.add(report);
                            existingDates.add(date);
                        }
                    }

                    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    Calendar calendar = Calendar.getInstance();
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String defaultName = userSnapshot.child("name").getValue(String.class);

                        for (int i = 0; i < 30; i++) {
                            calendar.add(Calendar.DAY_OF_MONTH, -1);
                            String pastDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
                            if (!existingDates.contains(pastDate)) {
                                ReportModel absentReport = new ReportModel(userId, defaultName, pastDate, "A");
                                reportModelList.add(absentReport);
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    showNoDataMessage();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pleaseWaitText.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                //Log.e(TAG, "Failed to fetch data: " + databaseError.getMessage());
            }
        });
    }*/
/*
    private void showNoDataMessage() {
        noDataText.setVisibility(View.VISIBLE);
    }

        return reportData;
    }*/

    public class ReportRepository {
        private final MutableLiveData<List<ReportModel>> reportData = new MutableLiveData<>();

        public LiveData<List<ReportModel>> getReportData() {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("Users").child("username");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<ReportModel> reports = new ArrayList<>();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String userId = snapshot.child("userId").getValue(String.class);
                        String name = snapshot.child("name").getValue(String.class);
                        String date = snapshot.child("date").getValue(String.class);
                        String status = snapshot.child("status").getValue(String.class);

                        ReportModel report = new ReportModel(userId, name, date, status);
                        reports.add(report);
                    }

                    reportData.setValue(reports);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            return reportData;
        }
    }
