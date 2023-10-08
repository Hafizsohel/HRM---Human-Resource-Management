package com.suffixit.hrm_suffix.view.Fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentDashboadBinding;
import com.suffixit.hrm_suffix.models.EmplyeeModel;

import com.suffixit.hrm_suffix.view.Activities.LoginActivity;


public class DashboadFragment extends Fragment {
    private FragmentDashboadBinding binding;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String PREFS_NAME = "MyPrefsFile";
    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboadBinding.inflate(inflater, container, false);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (currentUser != null) {
            String uid = currentUser.getUid();

            CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("Users");
            Query userQuery = usersCollection.whereEqualTo("userId", uid); // Assuming there's a field 'userId' in your documents

            //   userQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
                    Log.e("DashboadFragment", "Failed to fetch data: " + e.getMessage());
                }
            });
        }

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cardViewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new EmployeeFragment()).commit();
            }
        });


        binding.cardViewLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirmationDialog();
            }
        });

    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                navigateToLogoutPage();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void navigateToLogoutPage() {
        // Clear saved username and password
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_PASSWORD);
        editor.apply();

        Intent intent = new Intent(requireContext(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
}