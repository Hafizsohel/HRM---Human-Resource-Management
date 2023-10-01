package com.suffixit.hrm_suffix.view.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.suffixit.hrm_suffix.Adapter.EmployeeAdapter;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.models.EmplyeeModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class EmployeeFragment extends Fragment {
    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private List<EmplyeeModel> emplyeeList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_employee, container, false);


        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emplyeeList = new ArrayList<>();

        employeeAdapter = new EmployeeAdapter(emplyeeList, getContext());
        recyclerView.setAdapter(employeeAdapter);
        fetchDataFromFirebase();
        return rootView;
    }


    private void fetchDataFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("Users");

        usersCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                emplyeeList.clear();
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
}

