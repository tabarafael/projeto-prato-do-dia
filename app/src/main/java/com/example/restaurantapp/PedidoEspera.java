package com.example.restaurantapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.List;

/**Cria uma lista com todos os pedidos que se encaixam no filtro selecionado anteriormente*/

public class PedidoEspera extends ListActivity {


    private Boolean ValorNivelContaUsuario;
    private TextView header;
    private String valorFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_espera);

        Intent intent = getIntent();
        Boolean valorNivelConta = intent.getBooleanExtra("NivelConta",false);
        valorFiltro = intent.getStringExtra("ValorFiltro");
        ValorNivelContaUsuario = valorNivelConta;
        header = findViewById(R.id.TV_Pedidos_Header);
        header.setText(valorFiltro);

        AppGetListaPratos(valorFiltro);

    }

    @Override
    protected void onListItemClick(ListView listview, View view, int position, long id){
        if (ValorNivelContaUsuario){
            String item = (String) getListAdapter().getItem(position);
            Intent intent = new Intent(PedidoEspera.this, PedidoDescricao.class);
            intent.putExtra("NivelConta", ValorNivelContaUsuario);
            intent.putExtra("PratoSelecionado",item);
            intent.putExtra("ValorSituacao", valorFiltro);
            startActivity(intent);
        }
    }  /*Manipula o item selecionado na lista e abre a próxima acitivity*/

    private void AppGetListaPratos(String valorFiltro){
        final ProgressDialog pd = new ProgressDialog(PedidoEspera.this);
        pd.setMessage(getString(R.string.TXLoading));
        pd.setCancelable(false);
        pd.show();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pedidos");
        query.whereEqualTo("pedidosSituacao", valorFiltro);
        if (!ValorNivelContaUsuario){
            query.whereEqualTo("pedidosUserId", ParseUser.getCurrentUser().getObjectId());
            pd.dismiss();
        }
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                    pd.dismiss();
                    String[] listaNomePedidos = new String[objectsList.size()];
                    for (int i =0; i < objectsList.size();i++){
                        listaNomePedidos[i] = objectsList.get(i).getObjectId();
                    }
                    ArrayAdapterPedidos adapter = new ArrayAdapterPedidos(PedidoEspera.this,listaNomePedidos);
                    setListAdapter(adapter);

                }else {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }  /*Baixa a lista de pratos do servidor e infla lista*/

}
