package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PratoCheckout extends AppCompatActivity implements View.OnClickListener {

    private Button BTConfirmar;
    private Integer ValorNivelContaUsuario =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_checkout);

        BTConfirmar = findViewById(R.id.BTFinalCheckout);
        BTConfirmar.setOnClickListener(this);

        Intent intent = getIntent();



        ValorNivelContaUsuario = intent.getIntExtra(MenuAdmin.PARAMETRO_NIVEL_CONTA,0);

        Toast.makeText(this, "nível conta"+ValorNivelContaUsuario,Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onClick(View view){
        if (view == BTConfirmar){
            AppConfirmaCheckout();
        }

    }

    private void AppConfirmaCheckout(){


        if (ValorNivelContaUsuario==2){
            Intent intent = new Intent(this, MenuPrincipal.class);
            intent.putExtra(MenuAdmin.PARAMETRO_NIVEL_CONTA,ValorNivelContaUsuario);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                      //Limpa o stack de activities
            startActivity(intent);
        }else if (ValorNivelContaUsuario==1){
            Intent intent = new Intent(this, MenuPrincipalADMIN.class);
            intent.putExtra(MenuAdmin.PARAMETRO_NIVEL_CONTA,ValorNivelContaUsuario);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                      //Limpa o stack de activities
            startActivity(intent);
        }


    }
}
