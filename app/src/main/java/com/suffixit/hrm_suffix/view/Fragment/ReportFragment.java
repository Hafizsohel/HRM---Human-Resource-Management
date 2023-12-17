/*
package com.suffixit.hrm_suffix.view.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
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
import com.suffixit.hrm_suffix.Adapter.ReportAdapter;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentReportBinding;
import com.suffixit.hrm_suffix.models.ReportModel;
import com.suffixit.hrm_suffix.preference.AppPreference;
import com.suffixit.hrm_suffix.view.Activities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ReportFragment extends Fragment {
    private static final String TAG = "ReportFragment";
   private FragmentReportBinding binding;
    private DatabaseReference databaseReference;
    private List<ReportModel> reportModelList = new ArrayList();
    private ReportAdapter adapter;
    private AppPreference localStorage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpOnBackPressed();
        binding = FragmentReportBinding.inflate(getLayoutInflater());

        localStorage = new AppPreference(requireContext());
        String userId = localStorage.getUserName();

        binding.recyclerId.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ReportAdapter();
        binding.recyclerId.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        binding.reportToolbar.setOnClickListener(view1 -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new DashboadFragment()).commit());

        fetchUserDataFromFirebase(userId);
        return binding.getRoot();
    }

    private void setUpOnBackPressed() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isAdded()) {
                    setEnabled(false);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);

        getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            public void onDestroy() {
                onBackPressedCallback.remove();
            }
        });
    }

    private void fetchUserDataFromFirebase(String userId) {
        binding.pleaseWaitText.setVisibility(View.VISIBLE);
        DatabaseReference usersReference = databaseReference.child("Users").child("username");

        Query query = usersReference.orderByChild("userId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                binding.pleaseWaitText.setVisibility(View.GONE);
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

                    if (reportModelList != null) {
                        adapter.setData(reportModelList);
                    }
                } else {
                    showNoDataMessage();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                binding.pleaseWaitText.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to fetch data: " + databaseError.getMessage());
            }
        });
    }

    private void showNoDataMessage() {
        binding.pleaseWaitText.setVisibility(View.VISIBLE);
    }
}*/
package com.suffixit.hrm_suffix.view.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.suffixit.hrm_suffix.Adapter.ReportAdapter;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentReportBinding;
import com.suffixit.hrm_suffix.preference.AppPreference;
import com.suffixit.hrm_suffix.view.Activities.MainActivity;
import com.suffixit.hrm_suffix.viewModel.ReportViewModel;

public class ReportFragment extends Fragment {
    private ReportViewModel reportViewModel;
    private AppPreference localStorage;
    private ReportAdapter adapter;
    private FragmentReportBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReportBinding.inflate(getLayoutInflater());

        reportViewModel= new ViewModelProvider(this).get(ReportViewModel.class);

        setUpOnBackPressed();
        localStorage = new AppPreference(requireContext());
        String userId = localStorage.getUserName();

        adapter = new ReportAdapter();
        binding.recyclerId.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerId.setAdapter(adapter);

        reportViewModel.getUserReports(userId);

        reportViewModel.getUserResponse.observe(getViewLifecycleOwner(), reportModels -> {
            if (reportModels != null) {
                adapter.setData(reportModels);
                binding.pleaseWaitText.setVisibility(View.GONE);
            } else {
                showNoDataMessage();
            }
        });

        binding.reportToolbar.setOnClickListener(view1 -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new DashboadFragment()).commit());
        return binding.getRoot();
    }

    private void showNoDataMessage() {
        binding.noDataTextView.setVisibility(View.VISIBLE);
    }

    private void setUpOnBackPressed() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isAdded()) {
                    setEnabled(false);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);

        getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            public void onDestroy() {
                onBackPressedCallback.remove();
            }
        });
    }
}

