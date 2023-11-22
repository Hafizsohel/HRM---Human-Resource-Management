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
    private List<LeaveStatusModel> leaveStatusList;
    private AppPreference localStorage;
    private TextView pleaseWaitText;
    private CardView cardPending,cardApproved,cardRejected;
    private Toolbar toolbar;

    private TextView pendingCountTextView, approvedCountTextView, rejectedCountTextView;
    private int pendingCount = 0;
    private int approvedCount = 0;
    private int rejectedCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave_status, container, false);



        toolbar= view.findViewById(R.id.status_toolbar);
        cardPending = view.findViewById(R.id.cardPeding);
        cardApproved = view.findViewById(R.id.cardApproved);
        cardRejected = view.findViewById(R.id.cardRejected);
        pendingCountTextView = view.findViewById(R.id.pendingCount);
        approvedCountTextView = view.findViewById(R.id.approvedCount);
        rejectedCountTextView = view.findViewById(R.id.rejectedCount);
        cardPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLeaveStatus(localStorage.getUserName(), "Pending");
                changeCardColor(cardPending);
            }
        });

        cardApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLeaveStatus(localStorage.getUserName(), "Approved");
                changeCardColor(cardApproved);
            }
        });

        cardRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLeaveStatus(localStorage.getUserName(), "Rejected");
                changeCardColor(cardRejected);
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users").child("leave_applications");

        localStorage = new AppPreference(requireContext());
        String userName = localStorage.getUserName();
        pleaseWaitText=view.findViewById(R.id.pleaseWaitText);

        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requireActivity().onBackPressed();
                }
            });
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerStatusId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        leaveStatusList = new ArrayList<>();
        adapter = new LeaveStatusAdapter(leaveStatusList);
        recyclerView.setAdapter(adapter);

        fetchLeaveStatus(localStorage.getUserName(), "Approved");

        return view;
    }

    private void fetchLeaveStatus(String userId, String statusFilter) {
        pleaseWaitText.setVisibility(View.VISIBLE);
        Query usersReference = databaseReference.orderByChild("userId").equalTo(userId);

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pleaseWaitText.setVisibility(View.GONE);
                leaveStatusList.clear();

                approvedCount = 0;
                pendingCount = 0;
                rejectedCount = 0;

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
                    approvedCountTextView.setText(String.valueOf(approvedCount));
                    pendingCountTextView.setText(String.valueOf(pendingCount));
                    rejectedCountTextView.setText(String.valueOf(rejectedCount));

                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                pleaseWaitText.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeCardColor(CardView cardView) {

        int color = ContextCompat.getColor(requireContext(), R.color.blue);
        cardView.setCardBackgroundColor(color);

        if (cardView != cardPending) {
            cardPending.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gradiant));
        }
        if (cardView != cardApproved) {
            cardApproved.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green));
        }
        if (cardView != cardRejected) {
            cardRejected.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red));
        }
    }
}