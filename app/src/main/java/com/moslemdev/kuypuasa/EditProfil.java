package com.moslemdev.kuypuasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.moslemdev.kuypuasa.ui.bottomNav.ProfilFragment;

public class EditProfil extends AppCompatActivity {

    private Button buttonSimpan;
    EditText etNama;
    EditText etEmail;
    EditText etGender;
    EditText etUmur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profil);

        etNama = findViewById(R.id.edit_nama);
        etEmail= findViewById(R.id.edit_email);
        etGender= findViewById(R.id.edit_gender);
        etUmur= findViewById(R.id.edit_umur);
        buttonSimpan = findViewById(R.id.button_simpan);

        // menampilkan data user sebelum diedit
        showUserData();

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIsFilled()) {
                    changeUserData();
                    finish();
                }
            }
        });
    }

    private void showUserData() {
        etNama.setText(IsiDataDiri.user.nama);
        etEmail.setText(IsiDataDiri.user.email);
        etGender.setText(IsiDataDiri.user.gender);
        etUmur.setText(String.valueOf(IsiDataDiri.user.umur));
    }

    private boolean checkIsFilled() {
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

        return filled;
    }

    private void changeUserData() {
        IsiDataDiri.user.nama = etNama.getText().toString();
        IsiDataDiri.user.email = etEmail.getText().toString();
        IsiDataDiri.user.gender = etGender.getText().toString();
        IsiDataDiri.user.umur = Integer.parseInt(etUmur.getText().toString());
        saveEditDataUser();
    }

    private void saveEditDataUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("DataUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(IsiDataDiri.user);
        editor.putString("userData", json);
        editor.commit();
    }
}
