package com.suffixit.hrm_suffix.view.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.suffixit.hrm_suffix.Adapter.ScrumAdapter;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentScrumBinding;
import com.suffixit.hrm_suffix.models.ScrumModel;
import com.suffixit.hrm_suffix.view.Activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ScrumFragment extends Fragment {
    private static final String TAG = "ScrumFragment";
    FragmentScrumBinding binding;
    private RecyclerView recyclerView;
    private ScrumAdapter adapter;
    List<ScrumModel> scrumModelList = new ArrayList<>();
    private DatabaseReference databaseReference;
    private TextView pleaseWaitText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpOnBackPressed();

        binding = FragmentScrumBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        pleaseWaitText = binding.getRoot().findViewById(R.id.pleaseWaitText);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        fetchUserIdsFromFirebase();
        recyclerView = binding.recyclerId;
        adapter = new ScrumAdapter(scrumModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return rootView;
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

    private void fetchUserIdsFromFirebase() {
        pleaseWaitText.setVisibility(View.VISIBLE);
        databaseReference.child("Users").child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pleaseWaitText.setVisibility(View.GONE);
                scrumModelList.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.child("userId").getValue(String.class);
                    String name = userSnapshot.child("name").getValue(String.class);

                    Log.d(TAG, "onDataChange: " + userId +  name);
                    if (userId != null) {
                        ScrumModel scrum = new ScrumModel(userId, name);
                        scrumModelList.add(scrum);
                    }

                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pleaseWaitText.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.scrumToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new DashboardFragment()).commit();
            }
        });
    }
}
