package com.suffixit.hrm_suffix.view.Activities;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.databinding.DataBindingUtil;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.suffixit.hrm_suffix.R;
import com.suffixit.hrm_suffix.databinding.ActivitySplashBinding;
import com.suffixit.hrm_suffix.preference.AppPreference;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemBars();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        YoYo.with(Techniques.BounceInUp)
                .duration(3000)
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        Intent intent;
                        try {
                            String loginResponse = new AppPreference(SplashActivity.this).getUserId();
                            intent = !TextUtils.isEmpty(loginResponse) ?
                                    new Intent(SplashActivity.this, MainActivity.class) :
                                    new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } finally {
                            finish();
                        }
                    }
                })
                .playOn(binding.imgLogo);
    }

    private void hideSystemBars() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        }
    }
}