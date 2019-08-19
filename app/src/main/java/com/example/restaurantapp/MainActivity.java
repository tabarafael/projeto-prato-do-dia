package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button BTentrar;
    private Button BTcadastro;
    private Button BTrecuperarsenha;
    private EditText ETusuario;
    private EditText ETsenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BTentrar = findViewById(R.id.BTentrar);
        BTentrar.setOnClickListener(this);
        BTcadastro = findViewById(R.id.BTcadastro);
        BTcadastro.setOnClickListener(this);
        BTrecuperarsenha = findViewById(R.id.BTrecuperarsenha);
        BTrecuperarsenha.setOnClickListener(this);
        ETusuario = findViewById(R.id.ETusuario);
        ETsenha = findViewById(R.id.ETsenha);

    }  //Set de listeners nos botões

    @Override
    public void onClick(View view){
        if (view == BTentrar){
            AppEntrar();
        }
        if (view == BTcadastro){
            AppCadastro();
        }
        if(view == BTrecuperarsenha){
            AppRecuperar();
        }
    }            //Verifica o botão selecionado

    private void AppEntrar(){
        String usuario = ETusuario.getText().toString();
        String senha = ETsenha.getText().toString();

        if(TextUtils.isEmpty(usuario)) {
            ETusuario.setError(getString(R.string.ERUserVazio));
        }else if (TextUtils.isEmpty(senha)){
            ETsenha.setError(getString(R.string.ERSenhaVazio));
        }else{
            finish();
            Intent intent = new Intent(this, MenuAdmin.class);
            startActivity(intent);
        }
    }            //Pressionando o botão entrar, verifica os espaços, mas não chega a verificar os valores

    private void AppCadastro(){
        Intent intent = new Intent(this, MenuCadastro.class);
        startActivity(intent);
    }          //Redireciona para criar um cadastro, **Em construção

    private void AppRecuperar(){
        Intent intent = new Intent(this, MenuSenha.class);
        startActivity(intent);
    }          //Redireciona para recuperar senha  **Em construção
}
