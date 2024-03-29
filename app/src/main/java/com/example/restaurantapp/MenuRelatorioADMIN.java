package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**Redireciona o tipo de relatorio que deseja ser visualizado*/

public class MenuRelatorioADMIN extends AppCompatActivity implements View.OnClickListener{
    private Button BTCliente;
    private Button BTPrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_relatorio_admin);
        BTCliente = findViewById(R.id.BT_Relatorio_Usuario);
        BTPrato = findViewById(R.id.BT_Relatorio_Prato);
        BTCliente.setOnClickListener(this);
        BTPrato.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == BTCliente){
            AppCliente();
        }else if (view == BTPrato){
            AppPrato();
        }
    }

    private void AppCliente(){
        Intent intent = new Intent(this,GeradorRelatorioUsuario.class);
        startActivity(intent);
    }  /*Abre o relatório de clientes*/

    private void AppPrato(){
        Intent intent = new Intent(this,GeradorRelatorioPratos.class);
        startActivity(intent);
    }  /*Abre o relatório de pratos*/
}
