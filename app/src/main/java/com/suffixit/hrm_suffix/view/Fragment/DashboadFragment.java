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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.suffixit.hrm_suffix.Adapter.EmployeeAdapter;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentDashboadBinding;
import com.suffixit.hrm_suffix.models.EmplyeeModel;

import java.util.List;

public class DashboadFragment extends Fragment {
    private FragmentDashboadBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboadBinding.inflate(inflater, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userID = user.getUid();

            DocumentReference userRef = db.collection("Users").document(userID);
            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");
                        String name = documentSnapshot.getString("name");
                        String Designation = documentSnapshot.getString("Designation");
                        String Email = documentSnapshot.getString("Email");
                        String PhoneNumber = documentSnapshot.getString("PhoneNumber");
                        String Gender = documentSnapshot.getString("Gender");
                        String BloodGroup = documentSnapshot.getString("BloodGroup");

                        updateUI(username, name, Designation, Email, PhoneNumber, Gender, BloodGroup);
                    } else {
                        Log.e("DashboardFragment", "Document does not exist");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("DashboardFragment", "Error fetching document: " + e.getMessage());
                }
            });
        } else {
            Log.e("DashboardFragment", "User is not authenticated");
        }
        return binding.getRoot();
    }

    private void updateUI(String username, String name, String designation, String email,
                          String phoneNumber, String gender, String bloodGroup) {
        binding.txtEmployeeId.setText(username);
        binding.txtEmployeeName.setText(name);
        binding.txtEmployeeDesignation.setText(designation);
        binding.txtEmployeeMail.setText(email);
        binding.txtEmployeePhone.setText(phoneNumber);
        binding.txtEmployeeGender.setText(gender);
        binding.txtEmployeeBloodGroup.setText(bloodGroup);
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
