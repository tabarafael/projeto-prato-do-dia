package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseUser;

/**Activity para o envio do email para recuperação de senha*/

public class MenuSenha extends AppCompatActivity implements View.OnClickListener{

    private EditText ETEmail;
    private Button BTConfirma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_senha);
        ETEmail = findViewById(R.id.ET_Recupera_Email);
        BTConfirma = findViewById(R.id.BT_Recuperar_Confirma);
        ETEmail.setOnClickListener(this);
        BTConfirma.setOnClickListener(this);
    }

    public void onClick(View view){
        if (view == BTConfirma){
            AppConfirma();
        }
    }  /*Manipula o botão de confirmar*/

    private void AppConfirma(){
        String Email = ETEmail.getText().toString();
        if (Email.equals("")||Email == null){
            Toast.makeText(this,getString(R.string.TXVerificarCampo),Toast.LENGTH_LONG).show();
        }else{
            ParseUser.requestPasswordResetInBackground(Email);
            }
        }  /*Ativa o processo de recuperação de senha*/

    }


