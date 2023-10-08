package com.suffixit.hrm_suffix.view.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.suffixit.hrm_suffix.Adapter.EmployeeAdapter;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentDashboadBinding;
import com.suffixit.hrm_suffix.databinding.FragmentEmployeeBinding;
import com.suffixit.hrm_suffix.models.EmplyeeModel;
import com.suffixit.hrm_suffix.view.Activities.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class EmployeeFragment extends Fragment {

    private FragmentEmployeeBinding binding;
    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private List<EmplyeeModel> emplyeeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmployeeBinding.inflate(inflater, container, false);


        setUpOnBackPressed();
        adaper();
        fetchDataFromFirebase();

        return binding.getRoot();

    }


    private void setUpOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isEnabled()){
                    Toast.makeText(requireContext(), "Go back", Toast.LENGTH_SHORT).show();
                    setEnabled(false);
                    requireActivity().onBackPressed();
                }

            }
        });
    }

    private void fetchDataFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("Users");

        usersCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for (QueryDocumentSnapshot document : documentSnapshots) {
                    EmplyeeModel employee = document.toObject(EmplyeeModel.class);
                    emplyeeList.add(employee);
                }
                Collections.sort(emplyeeList, new Comparator<EmplyeeModel>() {
                    @Override
                    public int compare(EmplyeeModel employee1, EmplyeeModel employee2) {
                        return employee1.getUsername().compareTo(employee2.getUsername());
                    }
                });

                employeeAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("EmployeeFragment", "Failed to fetch data: " + e.getMessage());
            }
        });
    }

    private void adaper() {
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emplyeeList = new ArrayList<>();

        employeeAdapter = new EmployeeAdapter(emplyeeList, getContext());
        recyclerView.setAdapter(employeeAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.employeeToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new DashboadFragment()).commit();
                Toast.makeText(getActivity(), "Go Back", Toast.LENGTH_SHORT).show();
            }
        });

    }
}


