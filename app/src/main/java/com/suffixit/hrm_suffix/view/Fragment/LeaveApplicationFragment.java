package com.suffixit.hrm_suffix.view.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
    private EditText editTextDateOfApplication, editTextFromDate, editTextToDate, editTextName,editTextEmployeeID, editTextDesignation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLeaveApplicationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("leave_applications");

       editTextDateOfApplication = binding.editTextDateOfApplication;
        editTextFromDate = binding.editTextFromDate;
        editTextToDate= binding.editTextToDate;
        editTextName= binding.editTextName;
        editTextEmployeeID= binding.editTextEmployeeID;
        editTextDesignation= binding.editTextDesignation;



        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String formattedDate = dateFormat.format(currentDate);

        editTextDateOfApplication.setText(formattedDate);
        editTextDateOfApplication.setEnabled(false);

        localStorage = new AppPreference(requireContext());
        String userId = localStorage.getUserName();

        editTextEmployeeID.setText(userId);
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
                // Get data from UI elements
                String dateOfApplication = "";
                String name = "";
                String employeeId = "";
                String designation = "";
                String leaveReason = "";
                String fromDate = "";
                String toDate = "";
                String contactNumber = "";

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
                    Toast.makeText(getContext(), "Please enter alternative contact number", Toast.LENGTH_SHORT).show();
                    return;
                }


                Toast.makeText(getActivity(), "Button Click", Toast.LENGTH_SHORT).show();
                LeaveApplicationModel leaveApplication = new LeaveApplicationModel(dateOfApplication, name, employeeId, designation, leaveReason, fromDate, toDate, contactNumber);

                if (currentUser != null) {
                    databaseReference.push().setValue(leaveApplication);
                    Toast.makeText(getActivity(), "Data Update", Toast.LENGTH_SHORT).show();
                }
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