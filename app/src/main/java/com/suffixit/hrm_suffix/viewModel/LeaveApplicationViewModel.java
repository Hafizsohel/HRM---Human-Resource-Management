//package com.suffixit.hrm_suffix.viewModel;
//
//import androidx.lifecycle.LiveData;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.suffixit.hrm_suffix.models.LeaveStatusModel;
//import com.suffixit.hrm_suffix.repository.LeaveApplicationRepository;
//
//import java.util.List;
//
//public class LeaveApplicationViewModel {
//    private LeaveApplicationRepository leaveApplicationRepository;
//    private final DatabaseReference databaseReference;
//
//    {
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        leaveApplicationRepository = new LeaveApplicationRepository();
//    }
//
//    public LiveData<List<LeaveStatusModel>> getUserResponse = leaveApplicationRepository.getApplicationList();
//
//
//    public void fetchLeaveStatus(String userId, String statusFilter) {
//        leaveApplicationRepository.getApplication(userId, statusFilter);
//    }
//}
