package com.suffixit.hrm_suffix.view.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      /*  binding.cardViewEmplyee.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EmployeeFragment.class);
           // intent.putExtra(AppConstants.LOAD_FRAGMENT, AppConstants.FLAT_FRAGMENT);
            startActivity(intent);*/


        binding.cardViewEmplyee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //  FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            //   fragmentTransaction.replace(R.id.fragment_continer, new BlankFragment()).commit();
            }
        });

    }
}

