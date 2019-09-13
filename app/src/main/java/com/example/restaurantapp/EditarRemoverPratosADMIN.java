package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class EditarRemoverPratosADMIN extends ListActivity {

    private boolean ValorNivelContaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_remover_pratos_admin);

        Intent intent = getIntent();
        Boolean ValorNivelConta = intent.getBooleanExtra("NivelConta",false);
        ValorNivelContaUsuario = ValorNivelConta;
        AppGetListaPratos();
    }

    @Override
    protected void onListItemClick(ListView listview, View view, int position, long id){
        String item = (String) getListAdapter().getItem(position);
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Pratos");
        query.whereEqualTo("objectId", item);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
                    final ParseObject  objetoInterno = object;
                    new  AlertDialog.Builder(EditarRemoverPratosADMIN.this)
                            .setTitle("Exclusão de dados")
                            .setMessage("Esta ação é permanente!")
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try{
                                        objetoInterno.delete();
                                        objetoInterno.saveInBackground();


                                        new AlertDialog.Builder(EditarRemoverPratosADMIN.this)
                                                .setTitle("Excluído com sucesso")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.cancel();
                                                        finish();
                                                        overridePendingTransition(0, 0);
                                                        startActivity(getIntent());
                                                        overridePendingTransition(0, 0);
                                                    }
                                                }).show();
                                    }catch (Exception f){
                                        Toast.makeText(EditarRemoverPratosADMIN.this,f.getMessage(),Toast.LENGTH_LONG).show();
                                    }

                                }
                            }).show();


                }else{
                    Toast.makeText(EditarRemoverPratosADMIN.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void AppGetListaPratos(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pratos");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                    String[] listaNomePedidos = new String[objectsList.size()];
                    for (int i =0; i < objectsList.size();i++){
                        listaNomePedidos[i] = objectsList.get(i).getObjectId();
                    }
                    ArrayAdapterEditorPratos adapter = new ArrayAdapterEditorPratos(EditarRemoverPratosADMIN.this,listaNomePedidos);
                    setListAdapter(adapter);

                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
