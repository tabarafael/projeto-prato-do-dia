package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class GeradorRelatorioCompletoUsuario extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerador_relatorio_completo_usuario);

        Intent intent = getIntent();
        String usuarioSelecionado = intent.getStringExtra("UsuarioSelecionado");
        AppGetRelatorioUsuario(usuarioSelecionado);
    }

    private void AppGetRelatorioUsuario(String usuarioSelecionado){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pedidos");
        query.whereEqualTo("pedidosUserId",usuarioSelecionado);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    String[] listaNomePedidos = new String[objects.size()];
                    for (int i =0; i < objects.size();i++){
                        listaNomePedidos[i] = objects.get(i).getObjectId();
                    }
                    ArrayAdapterRelatorioCompleto adapter = new ArrayAdapterRelatorioCompleto(GeradorRelatorioCompletoUsuario.this,listaNomePedidos);
                    setListAdapter(adapter);

                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
