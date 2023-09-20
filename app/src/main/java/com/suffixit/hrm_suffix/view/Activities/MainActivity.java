package com.suffixit.hrm_suffix.view.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.ActivityMainBinding;
<<<<<<< HEAD
import com.suffixit.hrm_suffix.view.Fragment.DashboadFragment;

=======
>>>>>>> 4d199935ce4833381b804345fbd90dea35815609

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

<<<<<<< HEAD
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new DashboadFragment()).commit();
        }
=======
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
>>>>>>> 4d199935ce4833381b804345fbd90dea35815609

    }
}

<<<<<<< HEAD

}
=======
>>>>>>> 4d199935ce4833381b804345fbd90dea35815609
