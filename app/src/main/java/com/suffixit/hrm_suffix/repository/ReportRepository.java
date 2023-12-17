package com.suffixit.hrm_suffix.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.suffixit.hrm_suffix.models.ReportModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ReportRepository {

    private MutableLiveData<List<ReportModel>> reportModelList;
    private DatabaseReference databaseReference;
    public ReportRepository(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
        reportModelList = new MutableLiveData<>();
    }

    public LiveData<List<ReportModel>> getReportModelList() {
        return reportModelList;
    }
    public void getUserReports(String userId) {

       List<ReportModel> reportsData = new ArrayList<>();
        DatabaseReference usersReference = databaseReference.child("Users").child("username");
        Query query = usersReference.orderByChild("userId").equalTo(userId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> existingDates = new ArrayList<>();

                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.child("userId").getValue(String.class);
                        String name = userSnapshot.child("name").getValue(String.class);
                        String date = userSnapshot.child("date").getValue(String.class);

                        if (userId != null && name != null && date != null) {
                            ReportModel report = new ReportModel(userId, name, date, "P");
                            reportsData.add(report);
                            existingDates.add(date);
                        }
                    }
                    Calendar calendar = Calendar.getInstance();
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String defaultName = userSnapshot.child("name").getValue(String.class);

                        for (int i = 0; i < 30; i++) {
                            calendar.add(Calendar.DAY_OF_MONTH, -1);
                            String pastDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
                            if (!existingDates.contains(pastDate)) {
                                ReportModel report = new ReportModel(userId, defaultName, pastDate, "A");
                                reportsData.add(report);
                            }
                        }
                    }

                }
                reportModelList.setValue(reportsData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                reportModelList.setValue(null);
            }
        });
    }
}


