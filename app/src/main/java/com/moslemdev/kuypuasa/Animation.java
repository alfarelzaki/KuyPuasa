package com.moslemdev.kuypuasa;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.moslemdev.kuypuasa.ui.bottomNav.HomeFragment;

public class Animation extends AppCompatActivity {
    LottieAnimationView loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_spinner);

        loading = findViewById(R.id.loading_view);
        loading.setRepeatCount(android.view.animation.Animation.RELATIVE_TO_SELF);
        loading.setSpeed((float) 1.5);
        loading.playAnimation();

        loading.addAnimatorListener(new Animator.AnimatorListener(){

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                SharedPreferences sharedPreferences
                        = getSharedPreferences("DataUser", MODE_PRIVATE);
                if (!sharedPreferences.contains("userData")) {
                    Intent i = new Intent(Animation.this, LandingPage.class);
                    startActivity(i);
                } else {
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString("userData", "");
                    IsiDataDiri.user = gson.fromJson(json, User.class);
                    HomeFragment.state = sharedPreferences.getInt("stateVerifikasi", 0);
                    Intent i = new Intent(Animation.this, MainActivity.class);
                    startActivity(i);
                }

                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}

