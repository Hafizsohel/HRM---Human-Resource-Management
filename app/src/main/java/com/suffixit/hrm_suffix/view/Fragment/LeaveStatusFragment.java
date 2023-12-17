package com.suffixit.hrm_suffix.view.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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
import com.suffixit.hrm_suffix.databinding.FragmentAttendanceBinding;
import com.suffixit.hrm_suffix.databinding.FragmentLeaveStatusBinding;
import com.suffixit.hrm_suffix.models.LeaveStatusModel;
import com.suffixit.hrm_suffix.preference.AppPreference;

import java.util.ArrayList;
import java.util.List;

public class LeaveStatusFragment extends Fragment {
    private static final String TAG = "LeaveStatusFragment";
    FragmentLeaveStatusBinding binding;
    private DatabaseReference databaseReference;
    private LeaveStatusAdapter adapter;
    private List<LeaveStatusModel> leaveStatusList = new ArrayList<>();;
    private AppPreference localStorage;
    private int pendingCount = 0;
    private int approvedCount = 0;
    private int rejectedCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLeaveStatusBinding.inflate(inflater, container, false);

        binding.cardPeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLeaveStatus(localStorage.getUserName(), "Pending");
                changeCardColor(binding.cardPeding);
            }
        });

        binding.cardApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLeaveStatus(localStorage.getUserName(), "Approved");
                changeCardColor(binding.cardApproved);
            }
        });

        binding.cardRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLeaveStatus(localStorage.getUserName(), "Rejected");
                changeCardColor(binding.cardRejected);
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users").child("leave_applications");

        localStorage = new AppPreference(requireContext());
        String userName = localStorage.getUserName();
        if (binding.statusToolbar != null) {
            binding.statusToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requireActivity().onBackPressed();
                }
            });
        }

        binding.recyclerStatusId.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LeaveStatusAdapter();
        binding.recyclerStatusId.setAdapter(adapter);

        fetchLeaveStatus(localStorage.getUserName(), "Approved");

        return binding.getRoot();
    }

    private void fetchLeaveStatus(String userId, String statusFilter) {
        binding.pleaseWaitText.setVisibility(View.VISIBLE);
        Query usersReference = databaseReference.orderByChild("userId").equalTo(userId);

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                binding.pleaseWaitText.setVisibility(View.GONE);
                leaveStatusList.clear();

                approvedCount = 0;
                pendingCount = 0;
                rejectedCount = 0;

                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String status = userSnapshot.child("status").getValue(String.class);

                    if (status != null) {
                        if (status.equals("Approved")) {
                            approvedCount++;
                        } else if (status.equals("Pending")) {
                             pendingCount++;
                        } else if (status.equals("Rejected")) {
                            rejectedCount++;
                        }
                    }

                    if (!statusFilter.isEmpty() && status != null && status.equals(statusFilter)) {
                        String fetchedUserId = userSnapshot.child("userId").getValue(String.class);
                        String dateOfApplication = userSnapshot.child("dateOfApplication").getValue(String.class);

                        if (fetchedUserId != null && dateOfApplication != null) {
                            LeaveStatusModel statusModel = new LeaveStatusModel(fetchedUserId, dateOfApplication, status);
                            leaveStatusList.add(statusModel);

                        }
                    }
                    binding.approvedCount.setText(String.valueOf(approvedCount));
                    binding.pendingCount.setText(String.valueOf(pendingCount));
                    binding.rejectedCount.setText(String.valueOf(rejectedCount));

                    if (leaveStatusList != null) {
                        adapter.setData(leaveStatusList);
                    }else {
                        showNoDataMessage();
                    }
                }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                binding.pleaseWaitText.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeCardColor(CardView cardView) {

            int color = ContextCompat.getColor(requireContext(), R.color.blue);
            cardView.setCardBackgroundColor(color);

        if (cardView != binding.cardPeding) {
            binding.cardPeding.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gradiant));
        }
        if (cardView != binding.cardApproved) {
            binding.cardApproved.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green));
        }
        if (cardView != binding.cardRejected) {
            binding.cardRejected.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red));
        }
    }
    private void showNoDataMessage() {
        binding.noDataTextView.setVisibility(View.VISIBLE);
    }
}