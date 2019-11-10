package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;

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
    private String usuarioPedido;

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

        GetPedidoUser(valorFiltro);
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
        final ProgressDialog pd = new ProgressDialog(PedidoDescricao.this);
        pd.setMessage(getString(R.string.TXLoading));
        pd.setCancelable(false);
        pd.show();
        ParseQuery<ParseObject> query = new ParseQuery<>("Pedidos");
        query.whereEqualTo("objectId", valorFiltro);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
                    pd.dismiss();
                object.put("pedidosSituacao", novoStatus);
                object.saveInBackground();
                Intent intent = new Intent(PedidoDescricao.this, PedidoEspera.class);
                intent.putExtra("NivelConta",ValorNivelContaUsuario);
                intent.putExtra("ValorFiltro", novoStatus);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if(novoStatus.equals(valorIniciado)){
                    FazerNotificacaoInicio();
                }else if(novoStatus.equals(valorConcluido)){
                    FazerNotificacaoPronto();
                }else if(novoStatus.equals(valorCancelado)){
                    FazerNotificacaoCancelado();
                }
                startActivity(intent);
                }else{
                    pd.dismiss();
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

    private void FazerNotificacaoPronto(){
        Toast.makeText(this, usuarioPedido, Toast.LENGTH_LONG).show();
        final HashMap<String,String> params = new HashMap<>();
        params.put("Channels",usuarioPedido);
        ParseCloud.callFunctionInBackground("notificacaoPronto",params , new FunctionCallback<Object>() {
            @Override
            public void done(Object object, ParseException e) {
                if (e == null){
                    Toast.makeText(PedidoDescricao.this, "Notificação enviada", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PedidoDescricao.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    private void FazerNotificacaoInicio(){
        Toast.makeText(this, usuarioPedido, Toast.LENGTH_LONG).show();

        final HashMap<String,String> params = new HashMap<>();
        params.put("Channels",usuarioPedido);
        ParseCloud.callFunctionInBackground("notificacaoInicio",params , new FunctionCallback<Object>() {
            @Override
            public void done(Object object, ParseException e) {
                if (e == null){
                    Toast.makeText(PedidoDescricao.this, "Notificação enviada", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PedidoDescricao.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    private void FazerNotificacaoCancelado(){
        Toast.makeText(this, usuarioPedido, Toast.LENGTH_LONG).show();

        final HashMap<String,String> params = new HashMap<>();
        params.put("Channels",usuarioPedido);
        ParseCloud.callFunctionInBackground("notificacaoCancelado",params , new FunctionCallback<Object>() {
            @Override
            public void done(Object object, ParseException e) {
                if (e == null){
                    Toast.makeText(PedidoDescricao.this, "Notificação enviada", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PedidoDescricao.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }



    private void GetPedidoUser(String PratoID){
                ParseQuery<ParseObject> query = new ParseQuery<>("Pedidos");
        query.whereEqualTo("objectId", PratoID);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
                    usuarioPedido = object.getString("pedidosUserNome");
                }else{
                    Toast.makeText(PedidoDescricao.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
