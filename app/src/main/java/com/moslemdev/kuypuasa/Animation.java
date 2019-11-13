package com.moslemdev.kuypuasa;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.moslemdev.kuypuasa.ui.bottomNav.DashboardFragment;

public class Animation extends AppCompatActivity {
    LottieAnimationView loading;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_spinner);
        getSupportActionBar().hide();



        loading = findViewById(R.id.loading_view);
        loading.setRepeatCount(android.view.animation.Animation.RELATIVE_TO_PARENT);
        loading.playAnimation();

        loading.addAnimatorListener(new Animator.AnimatorListener(){

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent i = new Intent(Animation.this, MainActivity.class);
                startActivity(i);
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

