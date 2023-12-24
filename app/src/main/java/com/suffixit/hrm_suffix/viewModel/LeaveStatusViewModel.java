package com.suffixit.hrm_suffix.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.suffixit.hrm_suffix.models.LeaveStatusModel;
import com.suffixit.hrm_suffix.repository.LeaveStatusRepository;
import java.util.List;


public class LeaveStatusViewModel extends ViewModel {
    private LeaveStatusRepository leaveStatusRepository;
    private final DatabaseReference databaseReference;

    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        leaveStatusRepository = new LeaveStatusRepository(databaseReference);
    }

    public LiveData<List<LeaveStatusModel>> getUserResponse = leaveStatusRepository.getLeaveStatusList();

    public void fetchLeaveStatus(String userId, String statusFilter) {
        leaveStatusRepository.getLeaveStatus(userId, statusFilter);
    }
}


