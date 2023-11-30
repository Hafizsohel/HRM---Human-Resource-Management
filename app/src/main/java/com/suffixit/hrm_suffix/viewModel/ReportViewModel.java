package com.suffixit.hrm_suffix.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.suffixit.hrm_suffix.models.ReportModel;
import com.suffixit.hrm_suffix.repository.ReportRepository;

import java.util.List;

public class ReportViewModel extends ViewModel {
    private final ReportRepository reportRepository;
    private LiveData<List<ReportModel>> reportLiveData;

    public ReportViewModel() {
        reportRepository = new ReportRepository();
        reportLiveData = reportRepository.getReportData();
    }

    public LiveData<List<ReportModel>> getReportLiveData() {
        return reportLiveData;
    }
}

