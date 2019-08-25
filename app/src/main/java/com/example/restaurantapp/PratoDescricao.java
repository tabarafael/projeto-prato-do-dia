package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PratoDescricao extends AppCompatActivity implements View.OnClickListener {


    private Button BTCheckout;
    private Boolean ValorNivelContaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_descricao);

        BTCheckout = findViewById(R.id.BTPratoCheckout);
        BTCheckout.setOnClickListener(this);

        Intent intent = getIntent();
        Boolean ValorNivelConta = intent.getBooleanExtra("NivelConta", false);
        ValorNivelContaUsuario = ValorNivelConta;


    }


    @Override
    public void onClick (View view){
        if (view == BTCheckout){
         AppCheckout();
        }
    }    //verifica o botão pressioando

    private void AppCheckout(){
        Intent intent = new Intent(this, PratoCheckout.class);
        intent.putExtra("NivelConta",ValorNivelContaUsuario);
        startActivity(intent);
    }         //Direciona o usuário para o checkout final
}
