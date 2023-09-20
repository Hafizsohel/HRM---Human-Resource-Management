package com.suffixit.hrm_suffix.view.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentDashboadBinding;
import com.suffixit.hrm_suffix.models.EmplyeeDetails;


public class DashboadFragment extends Fragment {

private FragmentDashboadBinding binding;

    private FirebaseAuth authProfile;
    private String  name, address,number, gender, bloodGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboadBinding.inflate(inflater, container, false);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        showEmployeeList(firebaseUser);

        return binding.getRoot();


    }


    private void showEmployeeList(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EmplyeeDetails emplyeeDetails = snapshot.getValue(EmplyeeDetails.class);
                if (emplyeeDetails != null){
                    name = emplyeeDetails.getEmplyeeName();
                    address = emplyeeDetails.getEmplyeeAdree();
                    number = emplyeeDetails.getEmplyeePhoneNumber();
                    gender = emplyeeDetails.getEmplyeeGender();
                    bloodGroup = emplyeeDetails.getEmplyeeBloodGroup();

                    binding.txtUserName.setText("Name: "+name);
                    binding.txtEmplyeeAddress.setText("Address: "+address);
                    binding.txtEmplyeePhone.setText("Phone Number: "+name);
                    binding.txtEmplyeeGender.setText("Gender: "+gender);
                    binding.txtEmplyeeBloodGroup.setText("BloodGroup: "+bloodGroup);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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