
package com.suffixit.hrm_suffix.view.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.ActivityLoginBinding;
import com.suffixit.hrm_suffix.databinding.FragmentLeaveApplicationBinding;
import com.suffixit.hrm_suffix.preference.AppPreference;
import com.suffixit.hrm_suffix.utils.KeyboardUtils;
import com.suffixit.hrm_suffix.utils.NetworkUtils;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private AppPreference localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        localStorage = new AppPreference(this);
        db = FirebaseFirestore.getInstance();
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        checkAutoLogin();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (!NetworkUtils.isNetworkConnected(LoginActivity.this)) {
            KeyboardUtils.hideKeyboard(LoginActivity.this);
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
            return;
        }

        binding.loginBtn.setOnClickListener(v -> {

            String username = binding.userName.getText().toString().trim();
            String password = binding.password.getText().toString().trim();

            if (!NetworkUtils.isNetworkConnected(LoginActivity.this)) {
                KeyboardUtils.hideKeyboard(LoginActivity.this);
                Toast.makeText(LoginActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(username)) {
                KeyboardUtils.hideKeyboard(LoginActivity.this);
                binding.userName.requestFocus();
                Snackbar.make(binding.userName, "Please Enter username", Snackbar.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                KeyboardUtils.hideKeyboard(LoginActivity.this);
                binding.password.requestFocus();
                Snackbar.make(binding.password, "Please Enter password", Snackbar.LENGTH_SHORT).show();
                return;
            }

            authenticateUser(username, password);
        });
    }

    private void authenticateUser(final String username, final String enteredPassword) {

        CollectionReference usersCollection = FirebaseFirestore.getInstance().collection("Users");
        Query query = usersCollection.whereEqualTo("username", username);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                boolean userFound = false;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Map<String, Object> userData = document.getData();
                    String storedPassword = (String) userData.get("password");

                    if (storedPassword != null && storedPassword.equals(enteredPassword)) {
                        localStorage.putUserName(username);
                        localStorage.putLoginResponse(true);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        userFound = true;
                        break;
                    }
                }
                if (task.getResult().isEmpty()) {
                    KeyboardUtils.hideKeyboard(LoginActivity.this);
                    binding.userName.requestFocus();
                    Snackbar.make(binding.userName, "User with username '" + username + "' not found. User hasn't been created yet.", Snackbar.LENGTH_SHORT).show();
                } else if (!userFound) {
                    KeyboardUtils.hideKeyboard(LoginActivity.this);
                    binding.password.requestFocus();
                    Toast.makeText(LoginActivity.this, R.string.bad_credential, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Error querying Firestore: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                task.getException().printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        String savedUsername = sharedPreferences.getString(KEY_USERNAME, null);
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, null);

        if (savedUsername != null) {
            binding.userName.setText(savedUsername);
        }
        if (savedPassword != null) {
            binding.password.setText(savedPassword);
        }
    }

  /*  private void checkAutoLogin() {
        String savedUsername = sharedPreferences.getString(KEY_USERNAME, null);
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, null);
        if (savedUsername != null && savedPassword != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }*/

    private void checkAutoLogin() {
        if (localStorage.getLoginResponse()) {
            localStorage.putLoginResponse(false); // Set login response to false
            localStorage.clearUsername(); // Clear saved username
            localStorage.clearPassword(); // Clear saved password
        }
    }


}
