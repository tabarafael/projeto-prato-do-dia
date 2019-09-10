package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {

    private Button BTCardapioDia;
    private Button BTCardapioSemana;
    private Button BTPedidos;
    private Button BTAltCadastro;
    private Boolean ValorNivelContaUsuario;
    private Integer ValorDiaHoje = 8;   //Código para simbolizar o "dia Atual", os outros dias da semana são de 1-7 de DOM-SAB

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


        Intent intent = getIntent();
        Boolean ValorNivelConta = intent.getBooleanExtra("NivelConta",false);
        ValorNivelContaUsuario = ValorNivelConta;
    }

    @Override
    public void onBackPressed() {
        AppCancela();
    }     //Troca o botão "back" pelo "AppCancela()"

    private void AppCancela(){
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.TXSair))
                .setCancelable(false)
                .setPositiveButton((getString(R.string.TXSim)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ParseUser.logOut();
                        MenuPrincipal.super.onBackPressed();
                    }
                })
                .setNegativeButton((getString(R.string.TXNao)), null)
                .show();
    }                  //Segurança para impedir o usuário de sair do app sem querer

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
    }  //Verifica botão pressionado

    private void AppCardapioDia(){
        Intent intent = new Intent (this, PratoDoDia.class);
        intent.putExtra("NivelConta",ValorNivelContaUsuario);
        intent.putExtra("ValorDia",ValorDiaHoje);
        startActivity(intent);
    }    //Direciona para activity cardápio do dia atual, filtar pratos do dia **em construção

    private void AppCardapioSemana(){
        Intent intent = new Intent (this, PratoDaSemana.class);
        intent.putExtra("NivelConta",ValorNivelContaUsuario);
        startActivity(intent);
    }   //Abre activity com dias da semana

    private void AppPedidos(){
        Intent intent = new Intent (this, MenuPedidosAndamento.class);
        startActivity(intent);
    }         //Abre activity mostrando estado dos pedidos pertintentes ao usuário

    private void AppCadastro(){
        Intent intent = new Intent(this, MenuCadastro.class);
        startActivity(intent);
    }       //Abre menu de cadastro para o usuário alterar seus dados
}