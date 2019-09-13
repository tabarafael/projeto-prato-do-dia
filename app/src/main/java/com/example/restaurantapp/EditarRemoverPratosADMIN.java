package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class EditarRemoverPratosADMIN extends AppCompatActivity {

    private boolean ValorNivelContaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_remover_pratos_admin);

        Intent intent = getIntent();
        Boolean ValorNivelConta = intent.getBooleanExtra("NivelConta",false);
        ValorNivelContaUsuario = ValorNivelConta;
    }
}
