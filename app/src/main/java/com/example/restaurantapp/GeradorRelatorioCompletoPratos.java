package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GeradorRelatorioCompletoPratos extends ListActivity {

    private String pratoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerador_relatorio_completo_pratos);
        Intent intent =getIntent();
        pratoSelecionado = intent.getStringExtra("pratoSelecionado");
        AppGetListaPratos();
    }
    private void AppGetListaPratos(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pedidos");
        query.whereEqualTo("pedidosPratoNome", pratoSelecionado.trim());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                    String[] listaNomePedidos = new String[objectsList.size()];
                    for (int i =0; i < objectsList.size();i++){
                        listaNomePedidos[i] = objectsList.get(i).getObjectId();
                    }
                    ArrayAdapterRelatorioCompleto adapter = new ArrayAdapterRelatorioCompleto(GeradorRelatorioCompletoPratos.this,listaNomePedidos);
                    setListAdapter(adapter);

                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
