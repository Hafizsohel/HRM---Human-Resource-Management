package com.suffixit.hrm_suffix.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.ActivityLoginBinding;


public class loginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn(v);
            }
        });
    }
        public void loginBtn(View view){
            String userEmail= binding.userName.getText().toString().trim();
            String userPassword= binding.password.getText().toString().trim();

            if (TextUtils.isEmpty(userEmail)){
                Toast.makeText(this, "Enter the Email!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(userPassword)){
                Toast.makeText(this, "Enter the Password!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (userPassword.length()<6){
                Toast.makeText(this, "Password too short!, Enter minimum 4 characters!", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                startActivity(new Intent(loginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(loginActivity.this, "Bad Credential", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }
}
