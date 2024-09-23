package com.suffixit.hrm_suffix.view.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.ActivityMainBinding;

import com.suffixit.hrm_suffix.view.Fragment.DashboadFragment;
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.FrameLayoutID, new DashboadFragment()).commit();
        }
    }
}
