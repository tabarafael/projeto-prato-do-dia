package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuCadastroADMIN extends AppCompatActivity implements View.OnClickListener{

    private Button BTModificaContaAdmin;
    private Button BTModificaContaOutros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cadastro_admin);

        BTModificaContaAdmin = findViewById(R.id.BTModificarContaAdmin);
        BTModificaContaOutros = findViewById(R.id.BTModificarClientes);

        BTModificaContaOutros.setOnClickListener(this);
        BTModificaContaAdmin.setOnClickListener(this);
    }



    public void onClick(View view){
        if (view == BTModificaContaAdmin) {
            AppModificaContaAdmin();
        }
        if (view == BTModificaContaOutros){
            AppModificaContaOutros();
        }

    }            //verifica botão pressionado
    private void AppModificaContaAdmin(){
        Intent intent = new Intent(this, MenuCadastro.class);
        startActivity(intent);
    }                                              //redireciona para a página de cadastro da própria conta
    private void AppModificaContaOutros(){
            Toast.makeText(this, "Ainda em construção :(", Toast.LENGTH_SHORT).show();
    }                                              //redireciona para configurar contas de outros usuários, em construição ainda

}
