package com.suffixit.hrm_suffix.Adapter;

import static android.view.Gravity.CENTER;

import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.models.EmplyeeModel;

import java.util.Arrays;
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
                .inflate(R.layout.emplyee_item, parent, false);
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

        String username = clickedItem.getUsername();
        String name = clickedItem.getName();
        String designation = clickedItem.getDesignation();
        String phoneNumber = clickedItem.getPhoneNumber();
        String email = clickedItem.getEmail();
        String gender = clickedItem.getGender();
        String bloodGroup = clickedItem.getBloodGroup();

        StringBuilder detailsBuilder = new StringBuilder();
        detailsBuilder.append("User Name: ").append(username).append("\n");
        detailsBuilder.append("Name: ").append(name).append("\n");
        detailsBuilder.append("Designation: ").append(designation).append("\n");
        detailsBuilder.append("Phone Number: ").append(phoneNumber).append("\n");
        detailsBuilder.append("Email: ").append(email).append("\n");
        detailsBuilder.append("Gender: ").append(gender).append("\n");
        detailsBuilder.append("Blood Group: ").append(bloodGroup).append("\n");

        // Create a layout to hold the details and buttons
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.setPadding(20, 20, 20, 20);



        // Create TextView to display details
        TextView detailsTextView = new TextView(context);
        detailsTextView.setText(detailsBuilder.toString());
        detailsTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.addView(detailsTextView);
        LinearLayout iconsLayout = new LinearLayout(context);
        iconsLayout.setOrientation(LinearLayout.HORIZONTAL);
        iconsLayout.setGravity(Gravity.CENTER_HORIZONTAL);


        float density = context.getResources().getDisplayMetrics().density;
        int iconSizeDp = 30;
        int iconSizePx = (int) (iconSizeDp * density);


        ImageButton callButton = createImageButton(R.drawable.call_icon, iconSizePx);
        ImageButton smsButton = createImageButton(R.drawable.message, iconSizePx);
       // ImageButton whatsappButton = createImageButton(R.drawable.whatsapp_icon, iconSizePx);
        ImageButton emailButton = createImageButton(R.drawable.gmail, iconSizePx);


        // Add margins between the ImageButtons
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(iconSizePx, iconSizePx);
        params.setMargins(0, 0, 25, 35);
        callButton.setLayoutParams(params);

        params = new LinearLayout.LayoutParams(iconSizePx, iconSizePx);
        params.setMargins(0, 0, 25, 35);
        smsButton.setLayoutParams(params);

        /*params = new LinearLayout.LayoutParams(iconSizePx, iconSizePx);
        params.setMargins(0, 0, 25, 0);  // Adjust the right margin as needed
        whatsappButton.setLayoutParams(params);*/

        iconsLayout.addView(callButton);
        iconsLayout.addView(smsButton);
        // iconsLayout.addView(whatsappButton);
        iconsLayout.addView(emailButton);

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
        builder.setCustomTitle(customTitleView);
        builder.setView(layout);
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }
    private ImageButton createImageButton(int drawableRes, int sizePx) {
        ImageButton imageButton = new ImageButton(context);
        imageButton.setImageResource(drawableRes);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizePx, sizePx);
        imageButton.setLayoutParams(params);
        return imageButton;
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

