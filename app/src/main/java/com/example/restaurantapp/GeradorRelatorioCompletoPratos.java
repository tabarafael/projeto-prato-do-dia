package com.example.restaurantapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

public class GeradorRelatorioCompletoPratos extends ListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerador_relatorio_completo_pratos);
        Intent intent =getIntent();
        String pratoSelecionado = intent.getStringExtra("pratoSelecionado");
        AppGetListaPratos(pratoSelecionado);
    }
    private void AppGetListaPratos(String pratoSelecionado){
        final ProgressDialog pd = new ProgressDialog(GeradorRelatorioCompletoPratos.this);
        pd.setMessage(getString(R.string.TXLoading));
        pd.setCancelable(false);
        pd.show();
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
                    pd.dismiss();
                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            }
        });
    }

}
