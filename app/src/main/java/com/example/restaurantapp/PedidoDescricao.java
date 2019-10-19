package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PedidoDescricao extends AppCompatActivity implements View.OnClickListener {

    private boolean ValorNivelContaUsuario;
    private String valorFiltro;
    private Button BTIniciarPedido;
    private Button BTCancelarPedido;
    private Button BTProntoPedido;
    private final String valorSituacao = "Em espera";
    private final String valorIniciado = "Em Andamento";
    private final String valorCancelado = "Cancelado";
    private final String valorConcluido = "Concluido";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_descricao);

        Intent intent = getIntent();
        Boolean valorNivelConta = intent.getBooleanExtra("NivelConta",false);
        valorFiltro = intent.getStringExtra("PratoSelecionado");
        String valorFIltroSituacao = intent.getStringExtra("ValorSituacao");
        ValorNivelContaUsuario = valorNivelConta;

        BTIniciarPedido = findViewById(R.id.BT_Iniciar_Pedido);
        BTIniciarPedido.setOnClickListener(this);
        BTCancelarPedido = findViewById(R.id.BT_Cancelar_Pedido);
        BTCancelarPedido.setOnClickListener(this);
        BTProntoPedido = findViewById(R.id.BT_Concluido_Pedido);
        BTProntoPedido.setOnClickListener(this);
        AppLimparBotoes(valorFIltroSituacao);
    }

    @Override
    public void onClick(View view) {
        if (view == BTIniciarPedido){
            AppAlterarPedido(valorIniciado);
        } else if (view == BTCancelarPedido){
            AppAlterarPedido(valorCancelado);
        } else if (view == BTProntoPedido){
            AppAlterarPedido(valorConcluido);
        }
    }

    private void AppAlterarPedido(final String novoStatus){
        ParseQuery<ParseObject> query = new ParseQuery<>("Pedidos");
        query.whereEqualTo("objectId", valorFiltro);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
                object.put("pedidosSituacao", novoStatus);
                object.saveInBackground();
                Intent intent = new Intent(PedidoDescricao.this, PedidoEspera.class);
                intent.putExtra("NivelConta",ValorNivelContaUsuario);
                intent.putExtra("ValorFiltro", novoStatus);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                }else{
                    Toast.makeText(PedidoDescricao.this,"Erro de servidor", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void AppLimparBotoes(String valorStatus){
        if(valorStatus.equals(valorIniciado)){
            BTIniciarPedido.setVisibility(View.GONE);
        }else if (valorStatus.equals(valorConcluido)){
            BTCancelarPedido.setVisibility(View.GONE);
            BTIniciarPedido.setVisibility(View.GONE);
            BTProntoPedido.setVisibility(View.GONE);
        }else if (valorStatus.equals(valorCancelado)){
            BTCancelarPedido.setVisibility(View.GONE);
            BTIniciarPedido.setVisibility(View.GONE);
            BTProntoPedido.setVisibility(View.GONE);
        }else if (valorStatus.equals(valorSituacao)){
            BTProntoPedido.setVisibility(View.GONE);
        }
    }
}
