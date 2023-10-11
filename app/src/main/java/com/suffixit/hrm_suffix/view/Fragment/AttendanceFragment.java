package com.suffixit.hrm_suffix.view.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentAttendanceBinding;
import com.suffixit.hrm_suffix.databinding.FragmentEmployeeBinding;
import com.suffixit.hrm_suffix.view.Activities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AttendanceFragment extends Fragment {
    private FragmentAttendanceBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAttendanceBinding.inflate(inflater, container, false);

        setUpOnBackPressed();

        // Create a handler to update the clock every second
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateClock();
                handler.postDelayed(this, 1000); // 1000ms = 1s
                updateDateAndDay();

            }
        });
        binding.cardViewCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cardViewCheckIn.setVisibility(View.GONE);
                binding.cardViewCheckout.setVisibility(View.VISIBLE);

            }

        });

        binding.cardViewCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cardViewCheckIn.setVisibility(View.VISIBLE);
                binding.cardViewCheckout.setVisibility(View.GONE);

            }

        });
        return binding.getRoot();
    }

    private void updateDateAndDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());

        String currentDate = dateFormat.format(new Date());
        String currentDay = dayFormat.format(new Date());

        binding.dateTextView.setText(currentDate);
        binding.dayTextView.setText(currentDay);
    }

    private void updateClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        binding.clockTextView.setText(currentTime);
    }

    private void setUpOnBackPressed() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isAdded()) {
                    setEnabled(false);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);

        getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            public void onDestroy() {
                onBackPressedCallback.remove();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.Toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new DashboadFragment()).commit();
            }
        });
    }
}