package com.suffixit.hrm_suffix.view.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.suffixit.hrm_suffix.Adapter.EmployeeAdapter;

import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentDashboadBinding;
import com.suffixit.hrm_suffix.models.EmplyeeModel;
import com.suffixit.hrm_suffix.view.Activities.MainActivity;

import java.util.List;

public class DashboadFragment extends Fragment {
    private FragmentDashboadBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboadBinding.inflate(inflater, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();


            CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("Users");
            Query userQuery = usersCollection.whereEqualTo("Users", uid);

           // userQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            usersCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot documentSnapshots) {
                    for (QueryDocumentSnapshot document : documentSnapshots) {
                        EmplyeeModel employee = document.toObject(EmplyeeModel.class);

                        if (employee != null) {
                            String FullName = employee.getName();
                            String UserID = employee.getUsername();
                            String Designation = employee.getDesignation();
                            String PhoneNumber = String.valueOf(employee.getPhoneNumber());
                            String BloodGroup = employee.getBloodGroup();
                            String Email = employee.getEmail();
                            String Gender = employee.getGender();

                            binding.txtEmployeeName.setText("Name: " + FullName);
                            binding.txtEmployeeId.setText("ID: " + UserID);
                            binding.txtEmployeeDesignation.setText("Designation: " + Designation);
                            binding.txtEmployeePhone.setText("Phone: " + PhoneNumber);
                            binding.txtEmployeeGender.setText("Gender: " + Gender);
                            binding.txtEmployeeBloodGroup.setText("Blood Group: " + BloodGroup);
                            binding.txtEmployeeMail.setText("Email: " + Email);
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("DashboardFragment", "Failed to fetch data: " + e.getMessage());
                }
            });
        } else {
            // Handle the case where the user is not logged in
            // You may want to redirect them to the login screen or show a message
        }


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cardViewEmplyee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new EmployeeFragment()).commit();
            }
        });

    }
}
