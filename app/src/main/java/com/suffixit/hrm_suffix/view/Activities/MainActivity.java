package com.suffixit.hrm_suffix.view.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.ActivityMainBinding;
import com.suffixit.hrm_suffix.view.Fragment.EmployeeFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgEmplyee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigateToEmployeeFragment();

            }
        });
    }

        private void navigateToEmployeeFragment() {
            // Create a new instance of the EmployeeFragment
            EmployeeFragment employeeFragment = new EmployeeFragment();

            // Get the fragment manager
            FragmentManager fragmentManager = getSupportFragmentManager();

            // Start a fragment transaction to replace the current fragment with the EmployeeFragment
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, employeeFragment); // Replace with your fragment container ID
            transaction.addToBackStack(null); // Optional: Adds the transaction to the back stack
            transaction.commit();
        }
    }