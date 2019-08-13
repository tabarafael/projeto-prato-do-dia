package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {

    private Button BTCardapioDia;
    private Button BTCardapioSemana;
    private Button BTPedidos;
    private Button BTAltCadastro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        BTCardapioDia = findViewById(R.id.BTCardapioDia);
        BTCardapioSemana = findViewById(R.id.BTCardapioSemana);
        BTPedidos = findViewById(R.id.BTPedidos);
        BTAltCadastro = findViewById(R.id.BTAltCadastro);

        BTCardapioDia.setOnClickListener(this);
        BTCardapioSemana.setOnClickListener(this);
        BTPedidos.setOnClickListener(this);
        BTAltCadastro.setOnClickListener(this);

    }


    @Override
    public void onBackPressed() {
        AppCancela();
    }

    private void AppCancela(){
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.TXSair))
                .setCancelable(false)
                .setPositiveButton((getString(R.string.TXSim)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MenuPrincipal.super.onBackPressed();
                    }
                })
                .setNegativeButton((getString(R.string.TXNao)), null)
                .show();
    }

    @Override
    public void onClick (View view){
        if (view == BTCardapioDia){
            AppCardapioDia();
        }
        if (view == BTCardapioSemana){
            AppCardapioSemana();
        }
        if (view == BTPedidos){
            AppPedidos();
        }
        if (view == BTAltCadastro){
            AppCadastro();
        }
    }

    private void AppCardapioDia(){
        Intent intent = new Intent (this, PratoDoDia.class);
        intent.putExtra(PratoDoDia.PARAMETRO_DIA_SEMANA,8);
        startActivity(intent);
    }
    private void AppCardapioSemana(){
        Intent intent = new Intent (this, PratoDaSemana.class);
        startActivity(intent);
    }
    private void AppPedidos(){
        Toast.makeText(this,"Em construção",Toast.LENGTH_SHORT).show();
    }
    private void AppCadastro(){
        Toast.makeText(this,"Em construção",Toast.LENGTH_SHORT).show();
    }



}
