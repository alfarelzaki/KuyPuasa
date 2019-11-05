package com.moslemdev.kuypuasa;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class Animation extends AppCompatActivity {
    LottieAnimationView loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_spinner);

        loading = findViewById(R.id.loading_view);
        loading.setRepeatCount(android.view.animation.Animation.INFINITE);
        loading.playAnimation();
    }
}

