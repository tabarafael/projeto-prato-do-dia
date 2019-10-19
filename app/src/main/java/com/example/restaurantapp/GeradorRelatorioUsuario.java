package com.example.restaurantapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GeradorRelatorioUsuario extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerador_relatorio_usuario);
        AppGetListaUsuario();

    }
    protected void onListItemClick(ListView listview, View view, int position, long id){
        String item = (String) getListAdapter().getItem(position);
        Intent intent = new Intent(GeradorRelatorioUsuario.this,GeradorRelatorioCompletoUsuario.class);
        intent.putExtra("UsuarioSelecionado",item);
        startActivity(intent);
    }

    private void AppGetListaUsuario(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                    String[] listaUserId = new String[objectsList.size()];
                    for (int i =0; i < objectsList.size();i++){
                        listaUserId[i] = objectsList.get(i).getObjectId();
                    }

                    ArrayAdapterRelatorioUsuario adapter = new ArrayAdapterRelatorioUsuario(GeradorRelatorioUsuario.this,listaUserId);
                    setListAdapter(adapter);
                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
