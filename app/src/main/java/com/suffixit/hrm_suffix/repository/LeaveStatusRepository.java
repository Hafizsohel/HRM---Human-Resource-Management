package com.suffixit.hrm_suffix.repository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.suffixit.hrm_suffix.models.LeaveStatusModel;
import java.util.ArrayList;
import java.util.List;

public class LeaveStatusRepository {

    private MutableLiveData<List<LeaveStatusModel>> leaveStatusModelList;
    private DatabaseReference databaseReference;

    public LeaveStatusRepository(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
        leaveStatusModelList = new MutableLiveData<>();
    }
    public LiveData<List<LeaveStatusModel>> getLeaveStatusList() {
        return leaveStatusModelList;
    }

    public void getLeaveStatus(String userId, String statusFilter) {

        List<LeaveStatusModel> leaveStatusList = new ArrayList<>();
        DatabaseReference usersReference = databaseReference.child("Users").child("leave_applications");
        Query query = usersReference.orderByChild("userId").equalTo(userId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String status = userSnapshot.child("status").getValue(String.class);

                        if (status != null && status.equals(statusFilter)) {
                            String fetchedUserId = userSnapshot.child("userId").getValue(String.class);
                            String dateOfApplication = userSnapshot.child("dateOfApplication").getValue(String.class);

                            if (fetchedUserId != null && dateOfApplication != null) {
                                LeaveStatusModel statusModel = new LeaveStatusModel(fetchedUserId, dateOfApplication, status);
                                leaveStatusList.add(statusModel);
                            }
                        }
                    }
                }
                leaveStatusModelList.setValue(leaveStatusList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                leaveStatusModelList.setValue(null);
            }
        });
    }
}
