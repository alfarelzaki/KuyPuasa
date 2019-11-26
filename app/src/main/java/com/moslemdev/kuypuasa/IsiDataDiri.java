package com.moslemdev.kuypuasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class IsiDataDiri extends AppCompatActivity {

    private FloatingActionButton fab;
    public User user = new User();
    EditText etNama;
    EditText etEmail;
    EditText etGender;
    EditText etUmur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_data_diri);

        etNama = findViewById(R.id.input_nama);
        etEmail= findViewById(R.id.input_email);
        etGender= findViewById(R.id.input_gender);
        etUmur= findViewById(R.id.input_umur);

        fab = findViewById(R.id.floating_action_button_isi_data_diri);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();

                Intent i = new Intent(IsiDataDiri.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void createUser() {
        user.setNama(etNama.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setGender(etGender.getText().toString());
        user.setUmur(Integer.parseInt(etUmur.getText().toString()));
    }
}
