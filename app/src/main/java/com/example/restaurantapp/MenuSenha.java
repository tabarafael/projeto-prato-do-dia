package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;

public class MenuSenha extends AppCompatActivity implements View.OnClickListener{

    private EditText ETEmail;
    private EditText ETUsuario;
    private Button BTConfirma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_senha);
        ETEmail = findViewById(R.id.ETRecuperaEmail);
        ETUsuario = findViewById(R.id.ETRecuperaUsuario);
        BTConfirma = findViewById(R.id.BTRecuperarConfirma);
        ETEmail.setOnClickListener(this);
        ETUsuario.setOnClickListener(this);
        BTConfirma.setOnClickListener(this);
    }




    public void onClick(View view){
        if (view == BTConfirma){
            AppConfirma();
        }
    }

    private void AppConfirma(){
        String Email = ETEmail.getText().toString();
        String Usuario = ETUsuario.getText().toString();
        if (Email.equals("")||Email == null || Usuario.equals("")||Usuario==null){
            Toast.makeText(this,"Verifique os campos",Toast.LENGTH_LONG).show();
        }else{
            ParseUser.requestPasswordResetInBackground(Email);
            }
        }

    }


