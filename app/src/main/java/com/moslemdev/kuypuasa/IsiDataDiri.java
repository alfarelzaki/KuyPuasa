package com.moslemdev.kuypuasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moslemdev.kuypuasa.ui.bottomNav.DashboardFragment;

public class IsiDataDiri extends AppCompatActivity {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_data_diri);
        getSupportActionBar().hide();

        fab = findViewById(R.id.floating_action_button_isi_data_diri);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IsiDataDiri.this, MainActivity.class);
                startActivity(i);
            }
        });


    }
}
