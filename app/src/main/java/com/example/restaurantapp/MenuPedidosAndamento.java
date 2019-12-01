package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**Abre menu de opções de filtros para verificar os pedidos criados*/

public class MenuPedidosAndamento extends AppCompatActivity implements View.OnClickListener {

    private Button BTEspera;
    private Button BTAndamento;
    private Button BTConcluido;
    private Button BTCancelado;
    private boolean ValorNivelContaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pedidos_andamento);

        BTEspera = findViewById(R.id.BT_Pedidos_Espera);
        BTAndamento = findViewById(R.id.BT_Pedidos_Andamento);
        BTConcluido = findViewById(R.id.BT_Pedidos_Concluido);
        BTCancelado = findViewById(R.id.BT_Pedidos_Cancelado);

        BTEspera.setOnClickListener(this);
        BTAndamento.setOnClickListener(this);
        BTConcluido.setOnClickListener(this);
        BTCancelado.setOnClickListener(this);

        Intent intent = getIntent();
        ValorNivelContaUsuario = intent.getBooleanExtra("NivelConta",false);
    }
    @Override
    public void onClick(View view){
        if (view == BTEspera){
            AppAbrirFiltro("Em espera");
        }else if (view ==BTAndamento){
            AppAbrirFiltro("Em Andamento");
        }else if (view == BTConcluido){
            AppAbrirFiltro("Concluido");
        }else if (view == BTCancelado){
            AppAbrirFiltro("Cancelado");
        }
    }

    private void AppAbrirFiltro(String filtro){
        Intent intent = new Intent(this,PedidoEspera.class);
        intent.putExtra("NivelConta",ValorNivelContaUsuario);
        intent.putExtra("ValorFiltro", filtro);
        startActivity(intent);
    }  //abre próxima activity informando o filtro aqui selecionado
}
