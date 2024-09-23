
package com.suffixit.hrm_suffix.view.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.suffixit.hrm_suffix.Adapter.LeaveStatusAdapter;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentLeaveStatusBinding;
import com.suffixit.hrm_suffix.models.LeaveStatusModel;
import com.suffixit.hrm_suffix.preference.AppPreference;
import com.suffixit.hrm_suffix.viewModel.LeaveStatusViewModel;
import java.util.List;

public class LeaveStatusFragment extends Fragment {
    FragmentLeaveStatusBinding binding;
    private LeaveStatusAdapter adapter;
    private AppPreference localStorage;
    private LeaveStatusViewModel leaveStatusViewModel;
    private int pendingCount = 0;
    private int approvedCount = 0;
    private int rejectedCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLeaveStatusBinding.inflate(getLayoutInflater());
        leaveStatusViewModel = new ViewModelProvider(this).get(LeaveStatusViewModel.class);

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

        setupViews();
        observeLeaveStatus();
        changeCardColor(binding.cardApproved);

        return binding.getRoot();
    }



    private void setupViews() {
        binding.recyclerStatusId.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LeaveStatusAdapter();
        binding.recyclerStatusId.setAdapter(adapter);
        fetchLeaveStatus(localStorage.getUserName(), "Approved");

        binding.cardPeding.setOnClickListener(v -> {
            fetchLeaveStatus(localStorage.getUserName(), "Pending");
            changeCardColor(binding.cardPeding);
        });
        binding.cardApproved.setOnClickListener(v -> {
            fetchLeaveStatus(localStorage.getUserName(), "Approved");
            changeCardColor(binding.cardApproved);
        });
        binding.cardRejected.setOnClickListener(v -> {
            fetchLeaveStatus(localStorage.getUserName(), "Rejected");
            changeCardColor(binding.cardRejected);
        });
    }
    private void observeLeaveStatus() {
        leaveStatusViewModel.getUserResponse.observe(getViewLifecycleOwner(), leaveStatusModels -> {
            if (leaveStatusModels != null) {
                if (!leaveStatusModels.isEmpty()) {
                    adapter.setData(leaveStatusModels);
                    binding.pleaseWaitText.setVisibility(View.GONE);
                    binding.noDataTextView.setVisibility(View.GONE);

                    countStatuses(leaveStatusModels);
                } else {
                    showNoDataMessage();
                }
            } else {
                showNoDataMessage();
            }
        });
    }

    private void fetchLeaveStatus(String userId, String statusFilter) {
        leaveStatusViewModel.fetchLeaveStatus(userId, statusFilter);
        binding.pleaseWaitText.setVisibility(View.VISIBLE);

    }

    private void countStatuses(List<LeaveStatusModel> leaveStatusModels) {
        pendingCount = 0;
        approvedCount = 0;
        rejectedCount = 0;

        for (LeaveStatusModel statusModel : leaveStatusModels) {
            String status = statusModel.getStatus();

            if (status != null) {
                switch (status) {
                    case "Approved":
                        approvedCount++;
                        break;
                    case "Pending":
                        pendingCount++;
                        break;
                    case "Rejected":
                        rejectedCount++;
                        break;
                    default:
                        break;
                }
            }
        }

        binding.approvedCount.setText(String.valueOf(approvedCount));
        binding.pendingCount.setText(String.valueOf(pendingCount));
        binding.rejectedCount.setText(String.valueOf(rejectedCount));

        if (leaveStatusModels != null) {
            adapter.setData(leaveStatusModels);
        } else {
            showNoDataMessage();
        }
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
        binding.pleaseWaitText.setVisibility(View.GONE);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

