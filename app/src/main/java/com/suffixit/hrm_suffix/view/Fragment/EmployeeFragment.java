package com.suffixit.hrm_suffix.view.Fragment;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.suffixit.hrm_suffix.Adapter.EmployeeAdapter;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentEmployeeBinding;
import com.suffixit.hrm_suffix.models.EmplyeeModel;
import com.suffixit.hrm_suffix.view.Activities.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class EmployeeFragment extends Fragment {
    private FragmentEmployeeBinding binding;
    private EmployeeAdapter adapter;
    private List<EmplyeeModel> employeeList = new ArrayList<>();;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmployeeBinding.inflate(getLayoutInflater());

        setUpOnBackPressed();
        fetchDataFromFirebase();


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new EmployeeAdapter(employeeList, getContext());
        binding.recyclerView.setAdapter(adapter);

        binding.employeeToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new DashboadFragment()).commit();
            }
        });

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


    private void fetchDataFromFirebase() {
        binding.pleaseWaitText.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("Users");

        usersCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                binding.pleaseWaitText.setVisibility(View.GONE);
                employeeList.clear();
                for (QueryDocumentSnapshot document : documentSnapshots) {
                    EmplyeeModel employee = document.toObject(EmplyeeModel.class);
                    employeeList.add(employee);
                }
                Collections.sort(employeeList, new Comparator<EmplyeeModel>() {
                    @Override
                    public int compare(EmplyeeModel employee1, EmplyeeModel employee2) {
                        return employee1.getUsername().compareTo(employee2.getUsername());
                    }
                });
               adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.pleaseWaitText.setVisibility(View.GONE);
                Log.e("EmployeeFragment", "Failed to fetch data: " + e.getMessage());
            }
        });
    }
}


