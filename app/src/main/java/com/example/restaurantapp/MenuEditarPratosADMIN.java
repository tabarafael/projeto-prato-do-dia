package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuEditarPratosADMIN extends AppCompatActivity implements View.OnClickListener{

    private boolean ValorNivelContaUsuario;
    private Button BTAddPrato;
    private Button BTEditPrato;
    private Button BTRemovePrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_editar_pratos_admin);

        Intent intent = getIntent();
        ValorNivelContaUsuario = intent.getBooleanExtra("NivelConta",false);

        BTAddPrato = findViewById(R.id.BT_Edit_Adicionar);
        BTEditPrato = findViewById(R.id.BT_Edit_Editar);
        BTRemovePrato = findViewById(R.id.BT_Edit_Remover);
        BTAddPrato.setOnClickListener(this);
        BTEditPrato.setOnClickListener(this);
        BTRemovePrato.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view==BTAddPrato){
            AppAbrirAdd();
        }else if (view==BTEditPrato){
            AppAbrirEdit();
        }else if (view==BTRemovePrato){
            AppAbrirRemove();
        }

    }

    private void AppAbrirAdd(){
        Intent intent = new Intent(this, MenuAdicionarPratoADMIN.class);
        intent.putExtra("NivelConta", ValorNivelContaUsuario);
        startActivity(intent);
    }

    private void AppAbrirEdit(){
        Intent intent = new Intent(this, EditarModificarPratosADMIN.class);
        intent.putExtra("NivelConta", ValorNivelContaUsuario);
        startActivity(intent);
    }

    private void AppAbrirRemove(){
        Intent intent = new Intent(this, EditarRemoverPratosADMIN.class);
        intent.putExtra("NivelConta", ValorNivelContaUsuario);
        startActivity(intent);
    }
}
