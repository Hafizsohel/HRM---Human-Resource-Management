package com.suffixit.hrm_suffix.view.Fragment;

import android.content.SharedPreferences;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentDashboadBinding;
import com.suffixit.hrm_suffix.models.EmplyeeModel;


public class DashboadFragment extends Fragment {

    private FragmentDashboadBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference usersRef;

    private FirebaseFirestore db;
    private SharedPreferences sharedPreferences;
    private static final String KEY_USERNAME = "username";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboadBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emplyeeDetails();

        return binding.getRoot();

    }
    private void emplyeeDetails() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();

            CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("Users");
            Query userQuery = usersCollection.whereEqualTo("Users", uid); // Assuming there's a field 'userId' in your documents

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

                            binding.txtEmplyeeName.setText("Name: " + FullName);
                            binding.txtEmplyeeId.setText("ID: " + UserID);
                            binding.txtEmplyeeDesignation.setText("Designation: " + Designation);
                            binding.txtEmplyeePhone.setText("Phone: " + PhoneNumber);
                            binding.txtEmplyeeGender.setText("Gender: " + Gender);
                            binding.txtEmplyeeBloodGroup.setText("Blood Group: " + BloodGroup);
                            binding.txtEmplyeeMail.setText("Email: " + Email);
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
    }



    private void setDataInUI(String username) {

        // Set the data
        binding.txtEmplyeeName.setText(username);
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