package com.suffixit.hrm_suffix.Adapter;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.START;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.models.EmplyeeModel;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<EmplyeeModel> emplyeeList;
    private Context context;

    public EmployeeAdapter(List<EmplyeeModel> emplyeeList, Context context) {
        this.emplyeeList = emplyeeList;
        this.context = context;
    }
    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_item, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.EmployeeViewHolder holder, int position) {
        EmplyeeModel employee = emplyeeList.get(position);
        holder.username.setText(employee.getUsername());
        holder.nameText.setText(employee.getName());

        holder.itemView.setOnClickListener(v -> {
            showDetailsForItem(position);
        });
    }

    private void showDetailsForItem(int position) {
        EmplyeeModel clickedItem = emplyeeList.get(position);

        String detailsBuilder = "User Name: " + clickedItem.getUsername() + "\n" +
                "Name: " + clickedItem.getName() + "\n" +
                "Designation: " + clickedItem.getDesignation() + "\n" +
                "Phone Number: " + clickedItem.getPhoneNumber() + "\n" +
                "Email: " + clickedItem.getEmail() + "\n" +
                "Gender: " + clickedItem.getGender() + "\n" +
                "Blood Group: " + clickedItem.getBloodGroup();

        LinearLayout layout = new LinearLayout(context);
        View customBottomView = LayoutInflater.from(context).inflate(R.layout.custom_dialog_bottom, null);
        layout.setOrientation(LinearLayout.VERTICAL);  // Set orientation to vertical
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setPadding(20, 20, 20, 20);

        // Create a horizontal layout to hold the bar and text
        LinearLayout horizontalLayout = new LinearLayout(context);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.addView(horizontalLayout);

        // Create TextView to display details
        TextView detailsTextView = new TextView(context);
        detailsTextView.setText(detailsBuilder);
        detailsTextView.setGravity(START);
        horizontalLayout.addView(detailsTextView);

        // Access the buttons from the inflated layout
        ImageButton callButton = customBottomView.findViewById(R.id.callButton);
        ImageButton smsButton = customBottomView.findViewById(R.id.smsButton);
        ImageButton emailButton = customBottomView.findViewById(R.id.emailButton);

        LinearLayout iconsLayout = new LinearLayout(context);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmplyeeModel employee = emplyeeList.get(position);
                String phoneNumber = String.valueOf(employee.getPhoneNumber());
                initiatePhoneDial(phoneNumber);
            }
        });

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmplyeeModel employee = emplyeeList.get(position);
                String phoneNumber = String.valueOf(employee.getPhoneNumber());
                initiateSms(phoneNumber);
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmplyeeModel employee = emplyeeList.get(position);
                String email = String.valueOf(employee.getEmail());
                initiateEmail(email);
            }
        });

        layout.addView(iconsLayout);
        LayoutInflater inflater = LayoutInflater.from(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialogStyle);
        View customTitleView = inflater.inflate(R.layout.custom_alert_dialog_title, null);

        builder.setView(customBottomView);
        builder.setView(layout);
        builder.setCustomTitle(customTitleView);
        layout.addView(customBottomView);
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }

    private void initiatePhoneDial(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    private void initiateSms(String phoneNumber) {
        Uri smsUri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsUri);
        context.startActivity(intent);
    }

    private void initiateEmail(String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.setPackage("com.google.android.gm");
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return emplyeeList.size();
    }
    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView nameText;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.empID);
            nameText = itemView.findViewById(R.id.empName);
        }
    }
}

