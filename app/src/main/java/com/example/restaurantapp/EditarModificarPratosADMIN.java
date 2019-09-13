package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class EditarModificarPratosADMIN extends ListActivity {

    private boolean ValorNivelContaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_modificar_pratos_admin);

        Intent intent = getIntent();
        Boolean ValorNivelConta = intent.getBooleanExtra("NivelConta",false);
        ValorNivelContaUsuario = ValorNivelConta;
        AppGetListaPratos();
    }

    @Override
    protected void onListItemClick(ListView listview, View view, int position, long id){
        String item = (String) getListAdapter().getItem(position);
        Intent intent = new Intent(EditarModificarPratosADMIN.this, EditarEditorPratosADMIN.class);
        intent.putExtra("NivelConta", ValorNivelContaUsuario);
        intent.putExtra("PratoSelecionado",item);
        startActivity(intent);

    }

    private void AppGetListaPratos(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pratos");
        final ArrayList<Object> listaPedidos = new ArrayList<Object>();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                    String[] listaNomePedidos = new String[objectsList.size()];
                    for (int i =0; i < objectsList.size();i++){
                        listaNomePedidos[i] = objectsList.get(i).getObjectId();
                    }
                    ArrayAdapterEditorPratos adapter = new ArrayAdapterEditorPratos(EditarModificarPratosADMIN.this,listaNomePedidos);
                    setListAdapter(adapter);

                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
