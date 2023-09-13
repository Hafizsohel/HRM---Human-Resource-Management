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

                Fragment fragment=new EmployeeFragment();

                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
              //  fragmentTransaction.replace(R.id.continer, fragment).commit();

            }
        });
    }
}