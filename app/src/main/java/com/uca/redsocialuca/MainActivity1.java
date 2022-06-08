package com.uca.redsocialuca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity1 extends AppCompatActivity {

    Button RegistrarBTN,LoginBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);

        LoginBTN = findViewById(R.id.LoginBTN);
        RegistrarBTN = findViewById(R.id.RegistrarBTN);

        LoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity1.this,Iniciar_Sesion.class));
            }
        });
        RegistrarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity1.this,Registrarse.class));
            }
        });
    }
}