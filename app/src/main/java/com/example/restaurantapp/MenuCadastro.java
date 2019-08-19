package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuCadastro extends AppCompatActivity implements View.OnClickListener{

    private Button BTCancela;
    private Button BTConfirma;
    private EditText ETNewUsuario;
    private EditText ETNome;
    private EditText ETnewEmail;
    private EditText ETConfEmail;
    private EditText ETNewSenha;
    private EditText ETConfSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cadastro);

        BTCancela = findViewById(R.id.BTCancela);
        BTCancela.setOnClickListener(this);
        BTConfirma = findViewById(R.id.BTConfirma);
        BTConfirma.setOnClickListener(this);
        ETConfEmail = findViewById(R.id.ETConfEmail);
        ETConfSenha = findViewById(R.id.ETConfSenha);
        ETNewSenha = findViewById(R.id.ETNewSenha);
        ETNewUsuario = findViewById(R.id.ETNewUsuario);
        ETNome = findViewById(R.id.ETNome);
        ETnewEmail = findViewById(R.id.ETNewEmail);
    }  //Listeners dos botões

    @Override
    public void onClick(View view){
        if (view == BTCancela){
            AppCancela();
        }
        if (view == BTConfirma){
            AppConfirma();
        }

    }           //Olha qual botão foi pressionado
    @Override
    public void onBackPressed() {
        AppCancela();
    }       //Desvia o botão "back" para o AppCancela()

    private void AppCancela(){
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.TXCancelar))
                .setCancelable(false)
                .setPositiveButton((getString(R.string.TXSim)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MenuCadastro.super.onBackPressed();
                    }
                })
                .setNegativeButton((getString(R.string.TXNao)), null)
                .show();
    }     //aviso para garantir que o usuário não saia acidentalemnte

    private void AppConfirma(){

        String NewUsuario = ETNewUsuario.getText().toString();
        String Nome = ETNome.getText().toString();
        String NewEmail = ETnewEmail.getText().toString();                 //Recebe os dados inseridos no App
        String ConfEmail = ETConfEmail.getText().toString();
        String NewSenha = ETNewSenha.getText().toString();
        String ConfSenha = ETConfSenha.getText().toString();

        if(TextUtils.isEmpty(NewUsuario)) {
            ETNewUsuario.setError(getString(R.string.ERVazio));
        } else if(TextUtils.isEmpty(Nome)) {
            ETNome.setError(getString(R.string.ERVazio));
        } else if(TextUtils.isEmpty(NewEmail)) {
            ETnewEmail.setError(getString(R.string.ERVazio));
        } else if(TextUtils.isEmpty(ConfEmail)) {                      //Verifica se estão vazios e cria um erro caso sim.
            ETConfEmail.setError(getString(R.string.ERVazio));
        } else if(TextUtils.isEmpty(NewSenha)) {
            ETNewSenha.setError(getString(R.string.ERVazio));
        } else if(TextUtils.isEmpty(ConfSenha)) {
            ETConfSenha.setError(getString(R.string.ERVazio));
        } else {
            if(!NewEmail.equals(ConfEmail)){
                new AlertDialog.Builder(this).setMessage(getString(R.string.EREmailDif)).show();   //Verifica se os dois E-mails são iguais
            }else if (!NewSenha.equals(ConfSenha)){
                new AlertDialog.Builder(this).setMessage(getString(R.string.ERSenhaDif)).show();    //Verifica se as duas senhas são iguais
            }else {
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.TXCadastroSuc))
                        .setCancelable(false)
                        .setPositiveButton((getString(R.string.TXOK)), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .show();
            }                                                                                    // Mostra mensagem de sucesso no cadastro, mas não existe cadastro real ainda.

        }

    }     //Verifica os espaços e valida o usuário, ainda não funciona
}
