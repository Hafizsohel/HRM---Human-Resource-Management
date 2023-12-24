package com.suffixit.hrm_suffix.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.suffixit.hrm_suffix.models.ReportModel;
import com.suffixit.hrm_suffix.repository.ReportRepository;

import java.util.List;

public class ReportViewModel extends ViewModel {
    private ReportRepository reportRepository;
    private final DatabaseReference databaseReference;

    //init
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        reportRepository = new ReportRepository(databaseReference);
    }
    public LiveData<List<ReportModel>> getUserResponse = reportRepository.getReportModelList();

    public void getUserReports(String userId) {
        reportRepository.getUserReports(userId);
    }
}
