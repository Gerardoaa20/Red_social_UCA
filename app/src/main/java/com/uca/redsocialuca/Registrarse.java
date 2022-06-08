package com.uca.redsocialuca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registrarse extends AppCompatActivity {
    EditText Correo, Nombre, Contraseña, Edad, Apellidos;
    Button Validar;

    //te amo gerardo//
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar !=null;
        actionBar.setTitle("Registro");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Correo = findViewById(R.id.Correo);
        Nombre = findViewById(R.id.Nombre);
        Contraseña = findViewById(R.id.Contraseña);
        Edad = findViewById(R.id.Edad);
        Apellidos = findViewById(R.id.Apellido);
        Validar = findViewById(R.id.Validar);

        firebaseAuth = firebaseAuth.getInstance();
        Validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = Correo.getText().toString();
                String contra = Contraseña.getText().toString();
                
                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    Correo.setError("Correo no válido");
                    Correo.setFocusable(true);
                }else if (contra.length()>6){
                    Contraseña.setError("Contraseña debe ser mayor a 6 caracteres");
                    Contraseña.setFocusable(true);
                }else{
                    Registrar(correo,contra);
                }
            }
        });
    }

    private void Registrar(String correo, String contra) {
        firebaseAuth.createUserWithEmailAndPassword(correo, contra)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();


                            assert user != null;
                            String uid = user.getUid();
                            String correo = Correo.getText().toString();
                            String contra = Contraseña.getText().toString();
                            String nombre = Nombre.getText().toString();
                            String apellido = Apellidos.getText().toString();
                            String edad = Edad.getText().toString();

                            HashMap <Object,String> DatosUsuario = new HashMap<>();

                            DatosUsuario.put("uid",uid);
                            DatosUsuario.put("correo",correo);
                            DatosUsuario.put("contra",contra);
                            DatosUsuario.put("nombre",nombre);
                            DatosUsuario.put("apellido",apellido);
                            DatosUsuario.put("edad",edad);

                            DatosUsuario.put("imagen","");
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference= database.getReference("USUARIOS_DE_APP");
                            reference.child(uid).setValue(DatosUsuario);
                            Toast.makeText(Registrarse.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registrarse.this,Inicio.class));


                        }else{
                            Toast.makeText(Registrarse.this, "Algo salió mal", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registrarse.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}