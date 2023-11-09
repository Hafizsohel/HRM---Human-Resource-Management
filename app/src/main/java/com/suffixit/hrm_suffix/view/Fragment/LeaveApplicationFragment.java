package com.suffixit.hrm_suffix.view.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.suffixit.hrm_suffix.databinding.FragmentLeaveApplicationBinding;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LeaveApplicationFragment extends Fragment {
    FragmentLeaveApplicationBinding binding;
    private EditText editTextDateOfApplication, editTextFromDate, editTextToDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLeaveApplicationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

       editTextDateOfApplication = binding.editTextDateOfApplication;
        editTextFromDate = binding.editTextFromDate;
        editTextToDate= binding.editTextToDate;

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String formattedDate = dateFormat.format(currentDate);

        editTextDateOfApplication.setText(formattedDate);
        editTextDateOfApplication.setEnabled(false);

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
