package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
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

import java.util.ArrayList;
import java.util.List;

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
        String item = (String) getListAdapter().getItem(position);
        Intent intent = new Intent(PedidoEspera.this, PedidoDescricao.class);
        intent.putExtra("NivelConta", ValorNivelContaUsuario);
        intent.putExtra("PratoSelecionado",item);
        intent.putExtra("ValorSituacao", valorFiltro);
        startActivity(intent);

    }

    private void AppGetListaPratos(String valorFiltro){


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pedidos");
        final ArrayList<Object> listaPedidos = new ArrayList<Object>();
        query.whereEqualTo("pedidosSituacao", valorFiltro);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                    String[] listaNomePedidos = new String[objectsList.size()];
                    for (int i =0; i < objectsList.size();i++){
                        listaNomePedidos[i] = objectsList.get(i).getObjectId();
                    }
                    ArrayAdapterPedidos adapter = new ArrayAdapterPedidos(PedidoEspera.this,listaNomePedidos);
                    setListAdapter(adapter);

                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
