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

public class MenuPrincipalADMIN extends AppCompatActivity implements View.OnClickListener{

    private Button BTCardapioDiaAdmin;
    private Button BTCardapioSemanaAdmin;
    private Button BTPedidosAdmin;
    private Button BTAltCadastroAdmin;
    private Button BTRelatorioAdmin;
    private Button BTAdicionarPratoAdmin;
    private Boolean ValorNivelContaUsuario;
    private Integer ValorDiaHoje = 8;  //Código para simbolizar o "dia Atual", os outros dias da semana são de 1-7 de DOM-SAB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_principal_admin);
        BTCardapioDiaAdmin = findViewById(R.id.BTCardapioDiaAdmin);
        BTCardapioSemanaAdmin = findViewById(R.id.BTCardapioSemanaAdmin);
        BTPedidosAdmin = findViewById(R.id.BTPedidosAdmin);
        BTAltCadastroAdmin = findViewById(R.id.BTAltCadastroAdmin);
        BTRelatorioAdmin = findViewById(R.id.BTRelatoriosAdmin);
        BTAdicionarPratoAdmin = findViewById(R.id.BTAdicionarPratoAdmin);

        BTCardapioDiaAdmin.setOnClickListener(this);
        BTCardapioSemanaAdmin.setOnClickListener(this);
        BTPedidosAdmin.setOnClickListener(this);
        BTAltCadastroAdmin.setOnClickListener(this);
        BTRelatorioAdmin.setOnClickListener(this);
        BTAdicionarPratoAdmin.setOnClickListener(this);

        Intent intent = getIntent();
        Boolean ValorNivelConta = intent.getBooleanExtra("NivelConta",false);
        ValorNivelContaUsuario = ValorNivelConta;
    }       //set listeners nos botões

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
                        MenuPrincipalADMIN.super.onBackPressed();
                    }
                })
                .setNegativeButton((getString(R.string.TXNao)), null)
                .show();
    }                  //Segurança para impedir o usuário de sair do app sem querer

    public void onClick(View view){
        if (view == BTCardapioDiaAdmin){
            AppCardapioDia();
        }else if (view == BTCardapioSemanaAdmin){
            AppCardapioSemana();
        }else if (view == BTAltCadastroAdmin){
            AppCadastros();
        }else if (view == BTPedidosAdmin){
            AppPedidosAndamento();
        }else if (view == BTRelatorioAdmin){
            AppRelatorio();
        }else if (view == BTAdicionarPratoAdmin){
            AppAdicionarPrato();
        }
    }                  //Verifica botão pressionado

    private void AppRelatorio(){
        Intent intent = new Intent(this, MenuRelatorioADMIN.class);
        startActivity(intent);
    }                   //Leva para activity relatório, exclusiva Admin

    private void AppPedidosAndamento(){
        Intent intent = new Intent(this, MenuPedidosAndamento.class);
        intent.putExtra("NivelConta",ValorNivelContaUsuario);
        startActivity(intent);
    }           //Leva pagina de pedidos em andamento, filtra de acordo com usuário **em andamento**

    private void AppCardapioDia(){
        Intent intent = new Intent (this, PratoDoDia.class);
        intent.putExtra("NivelConta",ValorNivelContaUsuario);
        intent.putExtra("ValorDia",ValorDiaHoje);
        startActivity(intent);
    }                   //Direciona para activity cardápio do dia atual, filtar pratos do dia **em construção

    private void AppCadastros(){
        Intent intent = new Intent(this, MenuCadastroADMIN.class);
        startActivity(intent);
    }                 //Espaço para modificar contas, admin e outras

    private void AppCardapioSemana(){
        Intent intent = new Intent (this, PratoDaSemana.class);
        intent.putExtra("NivelConta",ValorNivelContaUsuario);
        startActivity(intent);
    }               //Abre activity com dias da semana

    private void AppAdicionarPrato(){
        Intent intent = new Intent(this, MenuAdicionarPratoADMIN.class);
        startActivity(intent);
    }            //Activity para adicionar pratos aos menus

}
