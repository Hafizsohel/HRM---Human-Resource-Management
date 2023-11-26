package com.suffixit.hrm_suffix.view.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentDashboadBinding;
import com.suffixit.hrm_suffix.models.UserModel;
import com.suffixit.hrm_suffix.preference.AppPreference;
import com.suffixit.hrm_suffix.view.Activities.LoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboadFragment extends Fragment {
    private static final String TAG = "DashboadFragment";
    private FragmentDashboadBinding binding;
    private AppPreference localStorage;
    private CircleImageView profileImageView;
    private DrawerLayout drawerLayout;
    TextView name;
    private String imageUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboadBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        localStorage = new AppPreference(requireContext());
        String userId = localStorage.getUserName();
        Log.d(TAG, "userName: " + userId);

        CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("Users");
        Query query = usersCollection.whereEqualTo("username", userId);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                boolean userFound = false;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String name = document.getString("name");
                    String designation = document.getString("Designation");
                    String imageUrl = document.getString("profileImg");

                    UserModel user = new UserModel(userId, name, designation);
                    binding.setUser(user);


                    if (isAdded() && getActivity() != null && imageUrl != null && !imageUrl.isEmpty()) {
                        Uri imageUrlUri = Uri.parse(imageUrl);
                        Glide.with(this)
                                .load(imageUrlUri)
                                .into(binding.imgEmployeeProfile);
                    } else {
                        binding.imgEmployeeProfile.setImageResource(R.drawable.img);
                    }
                    
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Picasso.get().load(imageUrl).into(profileImageView);
                    }
                    if (getActivity() != null) {
                        TextView drawerName = getActivity().findViewById(R.id.name);
                        CircleImageView drawerProfileImage = getActivity().findViewById(R.id.profileImg);
                        drawerName.setText(name);
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Picasso.get().load(imageUrl).into(drawerProfileImage);
                        }
                    }
                    break;
                }

                if (!userFound) {
                }
            } else {
                task.getException().printStackTrace();
            }
        });

        profileImageView = binding.imgEmployeeProfile;
         return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawerLayout = view.findViewById(R.id.drawer_layer);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(profileImageView);
        }

        binding.cardViewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new EmployeeFragment()).commit();
            }
        });

        binding.cardViewLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new ApplicationDashboardFragment()).commit();

            }
        });

        binding.cardViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new AttendanceFragment()).commit();
            }
        });
        binding.imgScrumReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new ReportFragment()).commit();
            }
        });

        binding.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        binding.menuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
    }


    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Logout", (dialog, which) -> navigateToLogoutPage());
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void navigateToLogoutPage() {
        // Clear saved username and password
        localStorage.putLoginResponse(false);
        localStorage.clearUsername();
        localStorage.clearPassword();

        Intent intent = new Intent(requireContext(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }


    private void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
}
