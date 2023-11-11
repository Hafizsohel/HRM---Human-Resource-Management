package com.suffixit.hrm_suffix.view.Fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.FragmentLeaveApplicationBinding;
import com.suffixit.hrm_suffix.models.LeaveApplicationModel;
import com.suffixit.hrm_suffix.preference.AppPreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaveApplicationFragment extends Fragment {
    FragmentLeaveApplicationBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private AppPreference localStorage;
    private EditText editTextDateOfApplication, editTextFromDate, editTextToDate, editTextName, editTextEmployeeID, editTextDesignation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLeaveApplicationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        editTextDateOfApplication = binding.editTextDateOfApplication;
        editTextName = binding.editTextName;
        editTextToDate = binding.editTextToDate;
        editTextFromDate = binding.editTextFromDate;
        editTextEmployeeID = binding.editTextEmployeeID;
        editTextDesignation = binding.editTextDesignation;


        CheckBox openingBalanceCLCheckbox = binding.openingBalanceCLCheckbox;
        CheckBox openingBalanceMLCheckbox = binding.openingBalanceMLCheckbox;
        CheckBox requestForCLCheckbox = binding.requestForCLCheckbox;
        CheckBox requestForMLCheckbox = binding.requestForMLCheckbox;
        CheckBox balanceCLCheckbox = binding.balanceCLCheckbox;
        CheckBox balanceMLCheckbox = binding.balanceMLCheckbox;


        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String formattedDate = dateFormat.format(currentDate);

        editTextDateOfApplication.setText(formattedDate);
        editTextDateOfApplication.setEnabled(false);

        localStorage = new AppPreference(requireContext());
        String userId = localStorage.getUserName();

        editTextEmployeeID.setText( userId);
        editTextEmployeeID.setEnabled(false);

        CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("Users");
        Query query = usersCollection.whereEqualTo("username", userId);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    boolean userFound = false;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String name = document.getString("name");
                        String designation = document.getString("Designation");

                        binding.editTextName.setText(name);
                        editTextName.setEnabled(false);

                        binding.editTextDesignation.setText(designation);
                        editTextDesignation.setEnabled(false);

                        break;
                    }

                    if (!userFound) {
                    }
                } else {
                    task.getException().printStackTrace();
                }
            }
        });

        binding.editTextFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextFromDate);
            }
        });

        binding.editTextToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextToDate);
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String leaveReason = binding.editTextLeaveReason.getText().toString();
                String fromDate = binding.editTextFromDate.getText().toString();
                String toDate = binding.editTextToDate.getText().toString();
                String contactNumber = binding.editTextContactNumber.getText().toString();
                String dateOfApplication = binding.editTextDateOfApplication.getText().toString();
                String name = binding.editTextName.getText().toString();
                String employeeId = binding.editTextEmployeeID.getText().toString();
                String designation = binding.editTextDesignation.getText().toString();


                boolean openingBalanceCLCheckbox = binding.openingBalanceCLCheckbox.isChecked();
                boolean openingBalanceMLCheckbox = binding.openingBalanceMLCheckbox.isChecked();
                boolean requestForCLCheckbox = binding.requestForCLCheckbox.isChecked();
                boolean requestForMLCheckbox = binding.requestForMLCheckbox.isChecked();
                boolean balanceCLCheckbox = binding.balanceCLCheckbox.isChecked();
                boolean balanceMLCheckbox = binding.balanceMLCheckbox.isChecked();



                if (TextUtils.isEmpty(leaveReason)) {
                    Toast.makeText(getContext(), "Please enter your Leave Reason", Toast.LENGTH_SHORT).show();
                    binding.editTextLeaveReason.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(fromDate)) {
                    Toast.makeText(getContext(), "Please enter from date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(toDate)) {
                    Toast.makeText(getContext(), "Please enter to date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(contactNumber)) {
                    binding.editTextContactNumber.requestFocus();
                    Toast.makeText(getContext(), "Please enter alternative contact number", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("leave_applications");


                if (userId == null) {
                    Toast.makeText(getActivity(), "User not authenticated", Toast.LENGTH_SHORT).show();
                    return;
                }

                LeaveApplicationModel leaveApplication = new LeaveApplicationModel(
                        dateOfApplication, name, employeeId, designation, leaveReason,
                        fromDate, toDate, contactNumber,
                        openingBalanceCLCheckbox, openingBalanceMLCheckbox,
                        requestForCLCheckbox, requestForMLCheckbox,
                        balanceCLCheckbox, balanceMLCheckbox
                );

                leaveApplication.setStatus("Pending");
                databaseReference.push().setValue(leaveApplication)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Submit successfully", Toast.LENGTH_SHORT).show();

                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutID, new SuccessFragment()).commit();

                            }


                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to update data: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });

        return view;
    }

    private void showDatePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, month, dayOfMonth);

                        if (selectedCalendar.before(calendar)) {
                            Toast.makeText(requireContext(), "Please select a future or current date", Toast.LENGTH_SHORT).show();
                        } else {
                            String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                            editText.setText(selectedDate);
                        }
                    }
                },
                currentYear,
                currentMonth,
                currentDay
        );
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }

}