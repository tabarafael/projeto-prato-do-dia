package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**Menu redireciona para qual tipo de mudança de cadastro deseja efetuar*/

public class MenuCadastroADMIN extends AppCompatActivity implements View.OnClickListener{

    private Button BTModificaContaAdmin;
    private Button BTModificaContaOutros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cadastro_admin);

        BTModificaContaAdmin = findViewById(R.id.BT_Modificar_Conta_Admin);
        BTModificaContaOutros = findViewById(R.id.BT_Modificar_Clientes);

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
        Intent intent = new Intent(this, MenuAtCadastro.class);
        startActivity(intent);
    }                                              //redireciona para a página de cadastro da própria conta

    private void AppModificaContaOutros(){
        Intent intent = new Intent (MenuCadastroADMIN.this, MenuPermissaoADMIN.class);
        startActivity(intent);
    }                                              //redireciona para adicionar ou remover permissão de administrador de outras contas

}
