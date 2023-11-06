package com.suffixit.hrm_suffix.view.Fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.suffixit.hrm_suffix.databinding.FragmentScrumBinding;
import com.suffixit.hrm_suffix.models.ReportModel;
import com.suffixit.hrm_suffix.models.ScrumModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ReportFragment extends Fragment {
    private static final String TAG = "ReportFragment";
    FragmentReportBinding binding;
    private DatabaseReference databaseReference;
    List<ReportModel> reportModelList = new ArrayList();
    private ReportAdapter adapter;
    private TextView pleaseWaitText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setUpOnBackPressed();


        binding = FragmentReportBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.recyclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ReportAdapter(reportModelList);
        recyclerView.setAdapter(adapter);

        pleaseWaitText = binding.getRoot().findViewById(R.id.pleaseWaitText);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        fetchUserDataFromFirebase();
        return rootView;
    }

    private void setUpOnBackPressed() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isAdded()) {
                    getParentFragmentManager().popBackStack();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.reportToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new DashboardFragment()).commit();
            }
        });
    }

  /*  private void fetchUserIdsFromFirebase() {
        pleaseWaitText.setVisibility(View.VISIBLE);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        DatabaseReference usersReference = databaseReference.child("Users").child("username");
        Query query = usersReference.orderByChild("date").equalTo(currentDate);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pleaseWaitText.setVisibility(View.GONE);
                reportModelList.clear();


                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    String userId = userSnapshot.child("userId").getValue(String.class);
                    String name = userSnapshot.child("name").getValue(String.class);

                    if (userId != null && name != null) {
                        ReportModel report = new ReportModel(userId, name);
                        reportModelList.add(report);

                    }
                }
               // adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pleaseWaitText.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    } */
  private void fetchUserDataFromFirebase() {
      pleaseWaitText.setVisibility(View.VISIBLE);
      String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

      DatabaseReference usersReference = databaseReference.child("Users").child("username");

      Query query = usersReference.orderByChild("date").equalTo(currentDate);

      query.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              pleaseWaitText.setVisibility(View.GONE);
              reportModelList.clear();

              for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                  String userId = userSnapshot.child("userId").getValue(String.class);
                  String name = userSnapshot.child("name").getValue(String.class);
                  String date = userSnapshot.child("date").getValue(String.class);

                  if (userId != null && name != null && date != null) {
                      ReportModel report = new ReportModel(userId, name, date);
                      reportModelList.add(report);
                  }
              }

              adapter.notifyDataSetChanged();
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
              pleaseWaitText.setVisibility(View.GONE);
              Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
              Log.e(TAG, "Failed to fetch data: " + databaseError.getMessage());
          }
      });
  }

}