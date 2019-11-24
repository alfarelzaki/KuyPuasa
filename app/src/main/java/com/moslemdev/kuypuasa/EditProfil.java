package com.moslemdev.kuypuasa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.moslemdev.kuypuasa.ui.bottomNav.ProfilFragment;

public class EditProfil extends AppCompatActivity {

    private Button buttonSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profil);

        buttonSimpan = findViewById(R.id.button_simpan);
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfil.this, ProfilFragment.class);
                startActivity(intent);
            }
        });
    }
}
