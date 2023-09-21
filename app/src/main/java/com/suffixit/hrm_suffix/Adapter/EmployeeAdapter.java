package com.suffixit.hrm_suffix.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
                .inflate(R.layout.emplyee_item, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.EmployeeViewHolder holder, int position) {
        EmplyeeModel emplyee = emplyeeList.get(position);
        holder.username.setText(emplyee.getUsername());
        holder.nameText.setText(emplyee.getName());

        holder.itemView.setOnClickListener(v -> {
            showDetailsForItem(position);
        });

    }

    private void showDetailsForItem(int position) {
        EmplyeeModel clickedItem = emplyeeList.get(position);

        // Retrieve all fields associated with the clicked item
        String username = clickedItem.getUsername();
        String name = clickedItem.getName();
        String designation = clickedItem.getDesignation();
<<<<<<< HEAD
        String  phoneNumber = clickedItem.getPhoneNumber();
=======
        String phoneNumber = clickedItem.getPhoneNumber();
>>>>>>> 14c6aa5a48ae74b706a45c8f205c2fe7c55ff6f1
        String email = clickedItem.getEmail();
        String gender = clickedItem.getGender();
        String bloodGroup = clickedItem.getBloodGroup();


        // Create a string with all the details
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
        layout.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        layout.setPadding(20, 20, 20, 20);  // Adjust padding as needed

        // Create TextView to display details
        TextView detailsTextView = new TextView(context);
        detailsTextView.setText(detailsBuilder.toString());
        detailsTextView.setGravity(Gravity.CENTER);
        layout.addView(detailsTextView);

        LinearLayout iconsLayout = new LinearLayout(context);
        iconsLayout.setOrientation(LinearLayout.HORIZONTAL);
        iconsLayout.setGravity(Gravity.CENTER);

        float density = context.getResources().getDisplayMetrics().density;

        int iconSizeDp = 44;
        int iconSizePx = (int) (iconSizeDp * density);


        ImageButton callButton = createImageButton(R.drawable.call, iconSizePx);
        ImageButton smsButton = createImageButton(R.drawable.sms, iconSizePx);
       // ImageButton whatsappButton = createImageButton(R.drawable.ic_whatsapp, iconSizePx);
        ImageButton emailButton = createImageButton(R.drawable.ic_email, iconSizePx);

        // Add margins between the ImageButtons
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(iconSizePx, iconSizePx);
        params.setMargins(0, 0, 25, 0);  // Adjust the right margin as needed
        callButton.setLayoutParams(params);

        params = new LinearLayout.LayoutParams(iconSizePx, iconSizePx);
        params.setMargins(0, 0, 25, 0);  // Adjust the right margin as needed
        smsButton.setLayoutParams(params);

       /* params = new LinearLayout.LayoutParams(iconSizePx, iconSizePx);
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

            }
        });


       /* whatsappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmplyeeModel employee = emplyeeList.get(position);
                String phoneNumber = String.valueOf(employee.getPhoneNumber());
                initiateWhatsApp(phoneNumber);
            }
        });*/


        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmplyeeModel employee = emplyeeList.get(position);
                String email = employee.getEmail();

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + email));  // Specify the recipient's email
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");  // Set the subject if needed

                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show();
                }
            }
        });


        layout.addView(iconsLayout);
        LayoutInflater inflater = LayoutInflater.from(context);
        View customTitleView = inflater.inflate(R.layout.custom_alert_dialog_title, null);

        // Set the title of the AlertDialog to the custom title view
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCustomTitle(customTitleView);
        builder.setView(layout);
        AlertDialog dialog = builder.create();
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

    private void initiateWhatsApp(String phoneNumber) {
        // Create an Intent to open WhatsApp using the 'text' action
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String uri = "whatsapp://send?phone=" + phoneNumber;
        intent.setData(Uri.parse(uri));

        PackageManager packageManager = context.getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "WhatsApp is not installed on this device", Toast.LENGTH_SHORT).show();
        }
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

