package com.moslemdev.kuypuasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class IsiDataDiri extends AppCompatActivity {

    private FloatingActionButton fab;
    public static User user = new User();
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
                // mengecek apakah ada yang kosong atau tidak
                checkIsFilled();
            }
        });
    }

    private void checkIsFilled() {
        boolean filled = true;
        if (TextUtils.isEmpty(etNama.getText())) {
            etNama.setError("Isikan nama");
            filled = false;
        }

        if (TextUtils.isEmpty(etEmail.getText())) {
            etEmail.setError("Isikan email");
            filled = false;
        }

        if (TextUtils.isEmpty(etGender.getText())) {
            etGender.setError("Isikan gender");
            filled = false;
        }

        if (TextUtils.isEmpty(etUmur.getText())) {
            etUmur.setError("Isikan umur");
            filled = false;
        }

        if (filled) createUser();
    }

    public void createUser() {
        user.setNama(etNama.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setGender(etGender.getText().toString());
        user.setUmur(Integer.parseInt(etUmur.getText().toString()));
        saveDataUser();
        gotoDashboard();
    }

    private void saveDataUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("DataUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("userData", json);
        editor.commit();
    }

    private void gotoDashboard() {
        Intent i = new Intent(IsiDataDiri.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
