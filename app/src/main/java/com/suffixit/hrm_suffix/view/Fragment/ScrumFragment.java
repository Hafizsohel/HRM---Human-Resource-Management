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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.suffixit.hrm_suffix.Adapter.ScrumAdapter;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentScrumBinding;
import com.suffixit.hrm_suffix.models.ScrumModel;
import com.suffixit.hrm_suffix.view.Activities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        countTotalMembers();
        fetchUserIdsFromFirebase();

        recyclerView = binding.recyclerId;
        adapter = new ScrumAdapter(scrumModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

       // String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


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
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        binding.scrum.setText("SCRUM - " + currentDate);

        DatabaseReference usersReference = databaseReference.child("Users").child("username");
        Query query = usersReference.orderByChild("date").equalTo(currentDate);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pleaseWaitText.setVisibility(View.GONE);
                scrumModelList.clear();

                int checkedInUsersCount = 0;

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.child("userId").getValue(String.class);
                    String name = userSnapshot.child("name").getValue(String.class);

                    if (userId != null && name != null) {
                        ScrumModel scrum = new ScrumModel(userId, name);
                        scrumModelList.add(scrum);
                        checkedInUsersCount++;

                    }
                }
                updateCheckedInUsersCountUI(checkedInUsersCount);
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
    private void countTotalMembers() {
        CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("Users");

        usersCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int totalMembers = task.getResult().size();
                    updateTotalMembersUI(totalMembers);
                } else {
                    Toast.makeText(getActivity(), "Failed to count total members", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updateTotalMembersUI(int totalMembers) {
        binding.totalMember.setText(String.valueOf("TOTAL MEMBERS: " +totalMembers));
    }

    private void updateCheckedInUsersCountUI(int checkedInUsersCount) {
        binding.totalPresent.setText("TOTAL PRESENT: " + checkedInUsersCount);
    }

}
